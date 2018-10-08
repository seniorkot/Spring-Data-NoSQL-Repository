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

import ru.ifmo.se.sdbrep.model.Log;

import java.util.List;
import java.util.UUID;

/**
 * This interface contains methods that service must implement
 * to work with {@link Log} entities in the application.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
public interface LogService {

    /**
     * Gets and returns certain log defined with an ID.
     *
     * @param id Log id
     * @return {@link Log} entity
     */
    Log getById(UUID id);

    /**
     * Gets and returns all logs in the system.
     *
     * @return List of {@link Log} entities.
     */
    List<Log> getAll();

    /**
     * Gets and returns all logs connected to certain
     * profile and project.
     *
     * @param profileId Profile id
     * @param projectId Project id
     * @return List of {@link Log} entities.
     */
    List<Log> getAll(String profileId, String projectId);

    /**
     * Gets and returns all logs connected to certain
     * profile.
     *
     * @param profileId Profile id
     * @return List of {@link Log} entities.
     */
    List<Log> getAllByProfileId(String profileId);

    /**
     * Gets and returns all logs connected to certain
     * project.
     *
     * @param projectId Project id
     * @return List of {@link Log} entities.
     */
    List<Log> getAllByProjectId(String projectId);

    /**
     * Creates new log about user actions not connected
     * to any project (e.g. Registration, changing profile
     * info).
     *
     * @param message Log message
     * @param profileId Profile id
     * @return Created {@link Log} entity
     */
    Log createLog(String message, String profileId);

    /**
     * Creates new log about user actions connected
     * to a project (e.g. Creating, commit, etc.).
     *
     * @param message Log message
     * @param profileId Profile id
     * @param projectId Project id
     * @return Created {@link Log} entity
     */
    Log createLog(String message, String profileId, String projectId);
}
