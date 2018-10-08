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
import ru.ifmo.se.sdbrep.model.Log;
import ru.ifmo.se.sdbrep.repository.LogRepository;
import ru.ifmo.se.sdbrep.service.LogService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class is used as log app service
 * that implements {@link LogService} methods.
 *
 * @author seniorkot
 * @version 1.0
 * @since 1.0
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository mLogRepository;

    @Override
    public Log getById(UUID id) {
        Optional<Log> log = mLogRepository.findById(id);
        return log.orElse(null);
    }

    @Override
    public List<Log> getAll() {
        return mLogRepository.findAll();
    }

    @Override
    public List<Log> getAll(String profileId, String projectId) {
        return mLogRepository.findAllByProfileIdAndProjectId(profileId, projectId);
    }

    @Override
    public List<Log> getAllByProfileId(String profileId) {
        return mLogRepository.findAllByProfileId(profileId);
    }

    @Override
    public List<Log> getAllByProjectId(String projectId) {
        return mLogRepository.findAllByProjectId(projectId);
    }

    @Override
    public Log createLog(String message, String profileId) {
        return createLog(message, profileId, null);
    }

    @Override
    public Log createLog(String message, String profileId, String projectId) {
        Log log = new Log();
        log.setAction(message);
        log.setProfileId(profileId);
        log.setProjectId(projectId);
        return mLogRepository.insert(log);
    }
}
