/*
 * MIT License
 *
 * Copyright (c) 2018 seniorkot
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.ifmo.se.sdbrep.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ifmo.se.sdbrep.model.*;
import ru.ifmo.se.sdbrep.repository.*;
import ru.ifmo.se.sdbrep.service.CodeService;
import ru.ifmo.se.sdbrep.service.ProfileService;
import ru.ifmo.se.sdbrep.service.ProjectService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used as commit app service
 * that implements {@link CodeService} methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private TreeRepository mTreeRepository;

    @Autowired
    private BlobRepository mBlobRepository;

    @Autowired
    private CommitRepository mCommitRepository;

    @Autowired
    private BranchRepository mBranchRepository;

    @Autowired
    private ProjectRepository mProjectRepository;

    @Autowired
    private ProjectService mProjectService;

    @Autowired
    private ProfileService mProfileService;

    @Override
    public Tree getTree(String id) {
        return mTreeRepository.findById(id).orElse(null);
    }

    @Override
    public Tree getTree(String projectName, String branchName) {
        Branch branch = getBranch(projectName, branchName);
        if (branch != null) {
            Commit lastCommit = branch.getLastCommit();
            if (lastCommit != null) {
                return getTree(lastCommit.getCodeRoot());
            }
        }
        return null;
    }

    @Override
    public Tree getTree(String profileName, String projectName, String branchName) {
        Branch branch = getBranch(profileName, projectName, branchName);
        if (branch != null) {
            Commit lastCommit = branch.getLastCommit();
            if (lastCommit != null) {
                return getTree(lastCommit.getCodeRoot());
            }
        }
        return null;
    }

    @Override
    public Blob getBlob(String id) {
        return mBlobRepository.findById(id).orElse(null);
    }

    @Override
    public Commit getCommit(Long id) {
        return mCommitRepository.findById(id).orElse(null);
    }

    @Override
    public Commit getCommit(String projectName, String branchName) {
        Branch branch = getBranch(projectName, branchName);
        if (branch != null) {
            return branch.getLastCommit();
        }
        return null;
    }

    @Override
    public Commit getCommit(String profileName, String projectName, String branchName) {
        Branch branch = getBranch(profileName, projectName, branchName);
        if (branch != null) {
            return branch.getLastCommit();
        }
        return null;
    }

    @Override
    public Branch getBranch(Long id) {
        return mBranchRepository.findById(id).orElse(null);
    }

    @Override
    public Branch getBranch(String projectName, String branchName) {
        Project project = mProjectService.getCurrentByName(projectName);
        if (project != null) {
            List<Long> branchIds = project.getBranches();
            Iterable<Branch> branches = mBranchRepository.findAllById(branchIds);
            for (Branch branch : branches) {
                if (branch.getName().equals(branchName)) {
                    return branch;
                }
            }
        }
        return null;
    }

    @Override
    public Branch getBranch(String profileName, String projectName, String branchName) {
        Project project = mProjectService.getByProfileUsernameAndName(profileName, projectName);
        if (project != null) {
            List<Long> branchIds = project.getBranches();
            Iterable<Branch> branches = mBranchRepository.findAllById(branchIds);
            for (Branch branch : branches) {
                if (branch.getName().equals(branchName)) {
                    return branch;
                }
            }
        }
        return null;
    }

    @Override
    public Commit commit(String projectName, String branchName, List<InputFile> files, String message) {
        Branch branch = getBranch(projectName, branchName);
        if (branch == null) {
            if (branchName.equals(Branch.DEFAULT_BRANCH)){
                branch = new Branch();
                mBranchRepository.save(branch);
                Project project = mProjectService.getCurrentByName(projectName);
                project.getBranches().add(branch.getId());
                mProjectRepository.save(project);
            }
            else {
                Branch master = getBranch(projectName, Branch.DEFAULT_BRANCH);
                if (master != null) {
                    branch = new Branch();
                    branch.setName(branchName);
                    branch.setLastCommit(master.getLastCommit());
                    branch = mBranchRepository.save(branch);
                    Project project = mProjectService.getCurrentByName(projectName);
                    project.getBranches().add(branch.getId());
                    mProjectRepository.save(project);
                }
                else {
                    return null;
                }
            }
        }
        return createCommit(branch, files, message);
    }

    @Override
    public Commit commit(String profileName, String projectName, String branchName, List<InputFile> files, String message) {
        Branch branch = getBranch(profileName, projectName, branchName);
        if (branch == null) {
            if (branchName.equals(Branch.DEFAULT_BRANCH)){
                branch = mBranchRepository.save(new Branch());
                Project project = mProjectService.getByProfileUsernameAndName(profileName, projectName);
                project.getBranches().add(branch.getId());
                mProjectRepository.save(project);

            }
            else {
                Branch master = getBranch(profileName, projectName, Branch.DEFAULT_BRANCH);
                if (master != null) {
                    branch = new Branch();
                    branch.setName(branchName);
                    branch.setLastCommit(master.getLastCommit());
                    branch = mBranchRepository.save(branch);
                    Project project = mProjectService.getByProfileUsernameAndName(profileName, projectName);
                    project.getBranches().add(branch.getId());
                    mProjectRepository.save(project);
                }
                else {
                    return null;
                }
            }
        }
        return createCommit(branch, files, message);
    }

    private Commit createCommit(Branch branch, List<InputFile> files, String message) {
        if (files.size() != 0) {
            final Tree newTree = new Tree();
            Commit commit = new Commit();
            commit.setMessage(message);
            commit.setAuthor(mProfileService.getCurrent().getUsername());
            commit.setPreviousCommit(branch.getLastCommit());
            newTree.setDirName("/");

            // For each input file create and add trees with blobs
            for (InputFile file : files) {
                if (file.getPath() != null && !file.getPath().endsWith("/")) {
                    Tree tmp = newTree;
                    Tree tmp2;
                    String[] dirs = file.getPath().split("/");
                    for (int i = 1; i < dirs.length - 1; i++) {
                        tmp2 = findTree(tmp, dirs[i]);
                        if (tmp2 == null) {
                            tmp2 = new Tree();
                            tmp2.setDirName(dirs[i]);
                            tmp.getTrees().add(mTreeRepository.insert(tmp2));
                        }
                        tmp = tmp2;
                    }
                    Blob blob = new Blob();
                    blob.setFileName(dirs[dirs.length - 1]);
                    blob.setCode(file.getContent());
                    tmp.getBlobs().add(mBlobRepository.insert(blob));
                    mTreeRepository.save(tmp);
                }
            }

            if (branch.getLastCommit() != null) {
                final Tree oldTree = getTree(branch.getLastCommit().getCodeRoot());

                // Add other blobs to the structure
                addFiles(oldTree, newTree);

                // Remove unnecessary files & dirs
                List<Tree> addedTrees = new ArrayList<>();
                for (InputFile file : files) {
                    if (file.getPreviousPath() != null && !file.getPreviousPath().equals(file.getPath())
                            && !file.getPreviousPath().endsWith("/")) {
                        String[] dirs = file.getPreviousPath().split("/");
                        Tree tmp = newTree;
                        for (int i = 1; i < dirs.length - 1; i++) {
                            Tree tmp2 = findTree(tmp, dirs[i]);
                            if (tmp2 != null) {
                                if (addedTrees.remove(tmp2)) {
                                    mTreeRepository.delete(tmp2);
                                }
                                Tree tmp3 = new Tree();
                                tmp3.setDirName(tmp2.getDirName());
                                tmp3.setBlobs(tmp2.getBlobs());
                                tmp3.setTrees(tmp2.getTrees());
                                tmp3 = mTreeRepository.insert(tmp3);
                                addedTrees.add(tmp3);
                                tmp.getTrees().remove(tmp2);
                                tmp.getTrees().add(tmp3);
                                tmp = tmp3;
                            }
                            else  {
                                break;
                            }
                        }
                        tmp.getBlobs().remove(findBlob(tmp, dirs[dirs.length - 1]));
                        mTreeRepository.save(tmp);
                    }
                }
            }

            commit.setCodeRoot(mTreeRepository.save(newTree).getId());
            branch.setLastCommit(mCommitRepository.save(commit));
            mBranchRepository.save(branch);
            return commit;
        }
        return null;
    }

    private Tree findTree(Tree root, String treeName) {
        for (Tree tree : root.getTrees()) {
            if (tree.getDirName().equals(treeName)) {
                return tree;
            }
        }
        return null;
    }

    private Blob findBlob(Tree root, String blobName) {
        for (Blob blob : root.getBlobs()) {
            if (blob.getFileName().equals(blobName)) {
                return blob;
            }
        }
        return null;
    }

    private void addFiles(Tree oldTree, Tree newTree) {
        for (Blob blob : oldTree.getBlobs()) {
            if (findBlob(newTree, blob.getFileName()) == null) {
                newTree.getBlobs().add(blob);
            }
        }
        for (Tree tree : oldTree.getTrees()) {
            Tree subTree = findTree(newTree, tree.getDirName());
            if (subTree == null) {
                newTree.getTrees().add(tree);
            }
            else {
                addFiles(tree, subTree);
            }
        }
        mTreeRepository.save(newTree);
    }
}
