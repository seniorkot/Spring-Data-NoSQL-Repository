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
import ru.ifmo.se.sdbrep.model.Blob;
import ru.ifmo.se.sdbrep.model.Branch;
import ru.ifmo.se.sdbrep.model.Commit;
import ru.ifmo.se.sdbrep.model.Tree;
import ru.ifmo.se.sdbrep.repository.BlobRepository;
import ru.ifmo.se.sdbrep.repository.BranchRepository;
import ru.ifmo.se.sdbrep.repository.CommitRepository;
import ru.ifmo.se.sdbrep.repository.TreeRepository;
import ru.ifmo.se.sdbrep.service.CodeService;
import ru.ifmo.se.sdbrep.service.ProjectService;

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
    private ProjectService mProjectService;

    @Override
    public Tree getTree(String id) {
        return mTreeRepository.findById(id).orElse(null);
    }

    @Override
    public Tree getTree(String projectName, String branchName) {
        Branch branch = getBranch(projectName, branchName);
        String treeId = branch.getLastCommit().getCodeRoot();
        return getTree(treeId);
    }

    @Override
    public Tree getTree(String profileName, String projectName, String branchName) {
        Branch branch = getBranch(profileName, projectName, branchName);
        String treeId = branch.getLastCommit().getCodeRoot();
        return getTree(treeId);
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
    public Branch getBranch(Long id) {
        return mBranchRepository.findById(id).orElse(null);
    }

    @Override
    public Branch getBranch(String projectName, String branchName) {
        List<Long> branchIds = mProjectService.getCurrentByName(projectName).getBranches();
        return mBranchRepository.findByIdInAndName(branchIds, branchName);
    }

    @Override
    public Branch getBranch(String profileName, String projectName, String branchName) {
        List<Long> branchIds = mProjectService.getByProfileUsernameAndName(profileName, projectName).getBranches();
        return mBranchRepository.findByIdInAndName(branchIds, branchName);
    }
}
