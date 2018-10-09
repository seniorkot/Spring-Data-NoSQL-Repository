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
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ifmo.se.sdbrep.config.SecurityConfig;
import ru.ifmo.se.sdbrep.model.Profile;
import ru.ifmo.se.sdbrep.repository.ProfileRepository;
import ru.ifmo.se.sdbrep.service.ProfileService;

import java.util.Optional;

/**
 * This class is used as profile app service
 * that implements {@link ProfileService} and
 * {@link UserDetailsService} methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
public class ProfileServiceImpl implements ProfileService, UserDetailsService {

    @Autowired
    private ProfileRepository mProfileRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<UserDetails> profile = mProfileRepository.findByUsername(username);
        return profile.orElse(null);
    }

    @Override
    public Profile getCurrent() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof Profile) {
            return (Profile) userDetails;
        }
        return null;

    }

    @Override
    public Profile getById(@NonNull String id) {
        Optional<Profile> profile = mProfileRepository.findById(id);
        return profile.orElse(null);
    }

    @Override
    public Profile getByUsername(@NonNull String username) {
        UserDetails profile = loadUserByUsername(username);
        if (profile instanceof Profile) {
            return (Profile) profile;
        }
        return null;
    }

    @Override
    public Profile getByUsernameAndPassword(@NonNull String username, @NonNull String password) {
        Optional<Profile> profile = mProfileRepository.findByUsernameAndPassword(username, password);
        return profile.orElse(null);
    }

    @Override
    public Profile create(@NonNull String username, @NonNull String password) {
        if (loadUserByUsername(username) == null) {
            Profile profile = new Profile(username, password, new String[]{SecurityConfig.Roles.ROLE_USER});
            return mProfileRepository.insert(profile);
        }
        return null;
    }

    @Override
    public Profile update(@NonNull Profile profile) {
        Profile currentProfile = getCurrent();
        if (currentProfile != null) {
            if (profile.getUsername() != null) {
                if (loadUserByUsername(profile.getUsername()) == null) {
                    currentProfile.setUsername(profile.getUsername());
                }
                else {
                    return null;
                }
            }
            if (profile.getPassword() != null) {
                currentProfile.setPassword(profile.getPassword());
            }
            if (profile.getFirstName() != null) {
                currentProfile.setFirstName(profile.getFirstName());
            }
            if (profile.getLastName() != null) {
                currentProfile.setLastName(profile.getLastName());
            }
            if (profile.getBio() != null) {
                currentProfile.setBio(profile.getBio());
            }
            return mProfileRepository.save(currentProfile);
        }
        return null;
    }
}
