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

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ifmo.se.sdbrep.model.Profile;
import ru.ifmo.se.sdbrep.model.Project;

/**
 * This interface contains methods that service must implement
 * to work with {@link Profile} entities in the application.<br>
 * Extends from {@link UserDetailsService} for Spring Security
 * work.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
public interface ProfileService {

    /**
     * Gets and returns current user's profile.
     *
     * @return {@link Profile} entity
     */
    Profile getCurrent();

    /**
     * Gets and returns profile by id.
     *
     * @param id Profile id
     * @return {@link Profile} entity
     */
    Profile getById(String id);

    /**
     * Gets and returns profile by username.
     *
     * @param username Profile username
     * @return {@link Profile} entity
     */
    Profile getByUsername(String username);

    /**
     * Gets and returns profile by username
     * and password.
     *
     * @param username Profile username
     * @param password Profile password
     * @return {@link Profile} entity
     */
    Profile getByUsernameAndPassword(String username, 
                                     String password);

    /**
     * Creates new Profile in the system.
     *
     * @param username Profile username
     * @param password Profile password
     * @return Created {@link Profile}
     */
    Profile create(String username,
                   String password);

    /**
     * Updates current user's profile.
     *
     * @param profile Profile with new data
     * @return Updated {@link Profile}
     */
    Profile update(Profile profile);

    /**
     * Adds new project to current user's
     * profile.
     *
     * @param project Project to add
     * @return Updated {@link Profile}
     */
    Profile addProject(Project project);

    /**
     * Removes project from current user's
     * profile.
     *
     * @param project Project to add
     * @return Updated {@link Profile}
     */
    Profile removeProject(Project project);
}
