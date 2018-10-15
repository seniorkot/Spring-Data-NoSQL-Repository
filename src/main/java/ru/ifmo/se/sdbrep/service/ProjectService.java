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

import ru.ifmo.se.sdbrep.model.Project;

import java.util.List;

/**
 * This interface contains methods that service must implement
 * to work with {@link Project} entities in the application.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
public interface ProjectService {

    /**
     * Gets and returns certain project specified
     * by ID.
     *
     * @param id Project ID
     * @return {@link Project} entity
     */
    Project getById(String id);

    /**
     * Gets and returns current user's project
     * by project name.
     *
     * @param name Project name
     * @return {@link Project} entity
     */
    Project getCurrentByName(String name);

    /**
     * Gets and returns certain user's project
     * by project name.
     *
     * @param username Username
     * @param name Project name
     * @return {@link Project} entity
     */
    Project getByProfileUsernameAndName(String username,
                                        String name);

    /**
     * Gets and returns all current user's projects.
     *
     * @return List of {@link Project} entities
     */
    List<Project> getAllCurrent();

    /**
     * Gets and returns all certain user's projects.
     *
     * @param username Username
     * @return List of {@link Project} entities
     */
    List<Project> getAllByProfileUsername(String username);

    /**
     * Gets and returns all projects by collaborator
     * name (not owner).
     *
     * @param collaborator Collaborator name
     * @return List of {@link Project} entities
     */
    List<Project> getAllByCollaborator(String collaborator);

    /**
     * Creates new project in current user's profile.
     *
     * @param name Project name
     * @return Created {@link Project}
     */
    Project create(String name);

    /**
     * Updates current user's project information.
     *
     * @param projectName Project name
     * @param project Project's new info
     * @return Updated {@link Project}
     */
    Project update(String projectName,
                   Project project);

    /**
     * Deletes current user's project.
     *
     * @param project Project
     */
    void delete(Project project);

    /**
     * Adds new collaborator to current user's
     * project.
     *
     * @param projectName Project name
     * @param collaborator New collaborator's name
     * @return Updated {@link Project}
     */
    Project addCollaborator(String projectName,
                            String collaborator);

    /**
     * Removes collaborator from current user's
     * project.
     *
     * @param projectName Project name
     * @param collaborator Collaborator's name
     * @return Updated {@link Project}
     */
    Project removeCollaborator(String projectName,
                               String collaborator);
}
