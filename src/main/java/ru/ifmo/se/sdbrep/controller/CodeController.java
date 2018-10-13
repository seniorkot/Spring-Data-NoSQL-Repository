package ru.ifmo.se.sdbrep.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.sdbrep.model.Tree;

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

    @RequestMapping(path = "/{projectName}/code/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String projectName,
                                               @PathVariable String branchName) {
        return null;
    }

    @RequestMapping(path = "/profile/{username}/{projectName}/code/{branchName}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String username,
                                               @PathVariable String projectName,
                                               @PathVariable String branchName) {
        return null;
    }

    @RequestMapping(path = "/{projectName}/code/{branchName}/commit", method = RequestMethod.POST)
    public ResponseEntity<Void> commit(@PathVariable String projectName,
                                       @PathVariable String branchName) {
        return null;
    }

    @RequestMapping(path = "/profile/{username}/{projectName}/code/{branchName}/commit", method = RequestMethod.POST)
    public ResponseEntity<Void> commit(@PathVariable String username,
                                       @PathVariable String projectName,
                                       @PathVariable String branchName) {
        return null;
    }

    @RequestMapping(path = "/{projectName}/code/commit/{commitId}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String projectName,
                                               @PathVariable Long commitId) {
        return null;
    }

    @RequestMapping(path = "/profile/{username}/{projectName}/code/commit/{commitId}", method = RequestMethod.GET)
    public ResponseEntity<Tree> getProjectCode(@PathVariable String username,
                                               @PathVariable String projectName,
                                               @PathVariable Long commitId) {
        return null;
    }
}
