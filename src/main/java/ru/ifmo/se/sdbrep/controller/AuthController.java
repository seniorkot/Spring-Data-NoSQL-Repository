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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ifmo.se.sdbrep.model.Profile;
import ru.ifmo.se.sdbrep.service.LogService;
import ru.ifmo.se.sdbrep.service.ProfileService;

/**
 * This class is RESTController for requests associated
 * with authentication (login / register / logout).
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@RestController
public class AuthController {

    @Autowired
    private ProfileService mProfileService;

    /**
     * This endpoint authorizes user in the system.
     *
     * @param username Username
     * @param password Password
     * @return 200 - OK, 400 - User doesn't exist, 401 - Invalid password
     */
    @RequestMapping(path = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<Void> login(@RequestParam String username,
                                      @RequestParam String password) {
        Profile profile = mProfileService.getByUsernameAndPassword(username, password);
        if (profile != null) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(profile.getId(),
                            profile.getPassword(), profile.getAuthorities()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (mProfileService.getByUsername(username) != null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * This endpoint registers new user and authorizes him in the system
     *
     * @param username Username
     * @param password Password
     * @return 200 - OK, 409 - Conflict situation (user exists), 500 - Internal error
     */
    @RequestMapping(path = "/api/signUp", method = RequestMethod.POST)
    public ResponseEntity<Void> signUp(@RequestParam String username,
                                       @RequestParam String password) {
        Profile profile = mProfileService.create(username, password);
        if (profile != null){
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(profile.getId(),
                            profile.getPassword(), profile.getAuthorities()));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else if (mProfileService.getByUsername(username) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This endpoint allows user to logout from the system.
     *
     * @return 200 - OK
     */
    @RequestMapping(path = "/api/logout", method = RequestMethod.POST)
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
