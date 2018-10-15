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

package ru.ifmo.se.sdbrep.service;

import ru.ifmo.se.sdbrep.model.*;

import java.util.List;

/**
 * This interface contains methods that service must implement
 * to work with {@link Branch}, {@link Commit}, {@link Blob}
 * and {@link Tree} entities in the application.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
public interface CodeService {

    /**
     * Gets and returns tree by ID.
     *
     * @param id Tree id
     * @return {@link Tree} entity
     */
    Tree getTree(String id);

    /**
     * Gets and returns root tree from current
     * user project's last commit on a certain
     * branch.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @return {@link Tree entity}
     */
    Tree getTree(String projectName, 
                 String branchName);

    /**
     * Gets and returns root tree from certain
     * user project's last commit on a certain
     * branch.
     *
     * @param profileName Username
     * @param projectName Project name
     * @param branchName Branch name
     * @return {@link Tree entity}
     */
    Tree getTree(String profileName, 
                 String projectName, 
                 String branchName);

    /**
     * Gets and returns blob by ID.
     *
     * @param id Blob id
     * @return {@link Blob} entity
     */
    Blob getBlob(String id);

    /**
     * Gets and returns commit by ID.
     *
     * @param id Commit id
     * @return {@link Commit} entity
     */
    Commit getCommit(Long id);

    /**
     * Gets and returns branch by ID.
     *
     * @param id Branch id
     * @return {@link Branch} entity
     */
    Branch getBranch(Long id);

    /**
     * Gets and returns current user
     * project's branch by its name and
     * by project name.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @return {@link Branch} entity
     */
    Branch getBranch(String projectName, 
                     String branchName);

    /**
     * Gets and returns certain user
     * project's branch by its name and
     * by project name.
     *
     * @param profileName Username
     * @param projectName Project name
     * @param branchName Branch name
     * @return {@link Branch} entity
     */
    Branch getBranch(String profileName, 
                     String projectName, 
                     String branchName);

    /**
     * Creates new commit in current user's
     * project on certain branch.
     *
     * @param projectName Project name
     * @param branchName Branch name
     * @param files Modified files
     * @param message Commit message
     * @return Crated {@link Commit}
     */
    Commit commit(String projectName,
                  String branchName,
                  List<InputFile> files,
                  String message);

    /**
     * Creates new commit in certain user's
     * project on certain branch.
     *
     * @param profileName Username
     * @param projectName Project name
     * @param branchName Branch name
     * @param files Modified files
     * @param message Commit message
     * @return Crated {@link Commit}
     */
    Commit commit(String profileName,
                  String projectName,
                  String branchName,
                  List<InputFile> files,
                  String message);
}
