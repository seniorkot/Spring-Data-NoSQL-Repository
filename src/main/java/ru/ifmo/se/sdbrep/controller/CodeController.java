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

package ru.ifmo.se.sdbrep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.sdbrep.model.Branch;
import ru.ifmo.se.sdbrep.model.Commit;
import ru.ifmo.se.sdbrep.model.InputFile;
import ru.ifmo.se.sdbrep.model.Tree;
import ru.ifmo.se.sdbrep.service.CodeService;

import java.util.List;

/**
 * This class is a REST Controller for requests associated
 * with code actions (commit, view code, create branch, etc.).
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/project")
public class CodeController {

    @Autowired
    private CodeService mCodeService;

    /**
     * This endpoint returns code root tree from last commit in current
     * user's project on default branch.
     *
     * @param projectName Project name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/{projectName}/code", method = RequestMethod.GET)
    public ResponseEntity<Tree> getDefaultProjectCode(@PathVariable String projectName) {
        Tree codeRoot = mCodeService.getTree(projectName, Branch.DEFAULT_BRANCH);
        if (codeRoot != null) {
            return new ResponseEntity<>(codeRoot, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint returns code root tree from last commit in current
     * user's project on default branch.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/{projectName}/code/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String projectName,
                                               @PathVariable String branchName) {
        Tree codeRoot = mCodeService.getTree(projectName, branchName);
        if (codeRoot != null) {
            return new ResponseEntity<>(codeRoot, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint returns root tree from last commit in current
     * user's project on concrete branch.
     *
     * @param username Username
     * @param projectName Project name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/profile/{username}/{projectName}/code", method = RequestMethod.GET)
    public ResponseEntity<Tree> getDefaultProjectCode(@PathVariable String username,
                                                      @PathVariable String projectName) {
        Tree codeRoot = mCodeService.getTree(username, projectName, Branch.DEFAULT_BRANCH);
        if (codeRoot != null) {
            return new ResponseEntity<>(codeRoot, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint returns root tree from last commit on concrete
     * project branch.
     *
     * @param username Username
     * @param projectName Project name
     * @param branchName Branch name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/profile/{username}/{projectName}/code/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String username,
                                               @PathVariable String projectName,
                                               @PathVariable String branchName) {
        Tree codeRoot = mCodeService.getTree(username, projectName, branchName);
        if (codeRoot != null) {
            return new ResponseEntity<>(codeRoot, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint creates new commit in current user's project.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @param files Files to modify
     * @param message Commit message
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/{projectName}/commit/{branchName}", method = RequestMethod.POST)
    public ResponseEntity<Void> commit(@PathVariable String projectName,
                                       @PathVariable String branchName,
                                       @RequestBody List<InputFile> files,
                                       @RequestParam String message) {
        if (mCodeService.commit(projectName, branchName, files, message) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint creates new commit in the project.
     *
     * @param username Username
     * @param projectName Project name
     * @param branchName Branch name
     * @param files Files to modify
     * @param message Commit message
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/profile/{username}/{projectName}/commit/{branchName}", method = RequestMethod.POST)
    public ResponseEntity<Void> commit(@PathVariable String username,
                                       @PathVariable String projectName,
                                       @PathVariable String branchName,
                                       @RequestBody List<InputFile> files,
                                       @RequestParam String message) {
        if (mCodeService.commit(username, projectName, branchName, files, message) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint gets the last commit in current user's project
     * on concrete branch.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/{projectName}/commit/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Commit> getCommit(@PathVariable String projectName,
                                            @PathVariable String branchName) {
        Commit commit;
        if ((commit = mCodeService.getCommit(projectName, branchName)) != null) {
            return new ResponseEntity<>(commit, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint gets the last commit in certain user's project
     * on concrete branch.
     *
     * @param username Username
     * @param projectName Project name
     * @param branchName Branch name
     * @return 200 - OK, 400 - Bad request
     */
    @RequestMapping(path = "/profile/{username}/{projectName}/commit/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Commit> getCommit(@PathVariable String username,
                                            @PathVariable String projectName,
                                            @PathVariable String branchName) {
        Commit commit;
        if ((commit = mCodeService.getCommit(username, projectName, branchName)) != null) {
            return new ResponseEntity<>(commit, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
