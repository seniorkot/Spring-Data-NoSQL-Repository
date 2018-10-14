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

import org.springframework.lang.NonNull;
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

    Tree getTree(@NonNull String id);
    Tree getTree(@NonNull String projectName, @NonNull String branchName);
    Tree getTree(@NonNull String profileName, @NonNull String projectName, @NonNull String branchName);
    Blob getBlob(@NonNull String id);
    Commit getCommit(@NonNull Long id);
    Branch getBranch(@NonNull Long id);
    Branch getBranch(@NonNull String projectName, @NonNull String branchName);
    Branch getBranch(@NonNull String profileName, @NonNull String projectName, @NonNull String branchName);
    Commit commit(@NonNull String projectName,
                  @NonNull String branchName,
                  @NonNull List<InputFile> files,
                  @NonNull String message);
    Commit commit(@NonNull String profileName,
                  @NonNull String projectName,
                  @NonNull String branchName,
                  @NonNull List<InputFile> files,
                  @NonNull String message);
}
