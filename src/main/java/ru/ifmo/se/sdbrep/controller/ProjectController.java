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
import ru.ifmo.se.sdbrep.model.Project;
import ru.ifmo.se.sdbrep.service.ProjectService;

import java.util.List;

/**
 * This class is a REST Controller for requests associated
 * with getting / creating / updating / deleting user
 * profile(s).
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService mProjectService;

    /**
     * This endpoint returns all current user's profile projects.
     *
     * @return 200 - OK
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects() {
        return new ResponseEntity<>(mProjectService.getAllCurrent(), HttpStatus.OK);
    }

    /**
     * This endpoint returns all concrete user's profile projects.
     *
     * @param username Profile name
     * @return 200 - OK
     */
    @RequestMapping(path = "/profile/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable String username) {
        return new ResponseEntity<>(mProjectService.getAllByProfileUsername(username), HttpStatus.OK);
    }

    /**
     * This endpoint returns current user's profile project
     * by project name.
     *
     * @param projectName Project name
     * @return 200 - OK, 404 - Project not found
     */
    @RequestMapping(path = "/{projectName}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProject(@PathVariable String projectName) {
        Project project = mProjectService.getCurrentByName(projectName);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * This endpoint returns concrete user's profile project
     * by project name.
     *
     * @param username Profile name
     * @param projectName Project name
     * @return 200 - OK, 404 - Project not found
     */
    @RequestMapping(path = "/profile/{username}/{projectName}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProject(@PathVariable String username,
                                              @PathVariable String projectName) {
        Project project = mProjectService.getByProfileUsernameAndName(username, projectName);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * This endpoint creates new project in current
     * user's profile.
     *
     * @param projectName New project name
     * @return 201 - new project created, 400 - bad
     */
    @RequestMapping(path = "/{projectName}", method = RequestMethod.POST)
    public ResponseEntity<Void> createProject(@PathVariable String projectName) {
        if (mProjectService.create(projectName) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint updates current user's profile project.
     *
     * @param projectName Project name
     * @param project Project data to update
     * @return 200 - OK, 400 - Project not found or bad project name
     */
    @RequestMapping(path = "/{projectName}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateProject(@PathVariable String projectName,
                                              @RequestBody Project project) {
        if (mProjectService.update(projectName, project) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint deletes current user's profile project
     * by project name.
     *
     * @param projectName Project name
     * @return 200 - OK, 404 - Project not found
     */
    @RequestMapping(path = "/{projectName}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProject(@PathVariable String projectName) {
        Project project = mProjectService.getCurrentByName(projectName);
        if (project != null) {
            mProjectService.delete(project);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * This endpoint adds new collaborator to the project.
     *
     * @param projectName Project name
     * @param collaborator Collaborator
     * @return 200 - OK, 400 - Project or profile not found or collaborator already exists in the project
     */
    @RequestMapping(path = "/{projectName}/collaborator/{collaborator}", method = RequestMethod.POST)
    public ResponseEntity<Void> addCollaborator(@PathVariable String projectName,
                                                @PathVariable String collaborator) {
        if (mProjectService.addCollaborator(projectName, collaborator) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint deletes collaborator from the project.
     *
     * @param projectName Project name
     * @param collaborator Collaborator
     * @return 200 - OK, 400 - Project collaborator not found
     */
    @RequestMapping(path = "/{projectName}/collaborator/{collaborator}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeCollaborator(@PathVariable String projectName,
                                                   @PathVariable String collaborator) {
        if (mProjectService.removeCollaborator(projectName, collaborator) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
