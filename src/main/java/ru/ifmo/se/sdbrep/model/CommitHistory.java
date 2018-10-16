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

package ru.ifmo.se.sdbrep.model;

import org.neo4j.ogm.annotation.*;

/**
 * This entity is used as Commit relationship
 * entity.
 */
@RelationshipEntity(type = "COMMIT_HISTORY")
public class CommitHistory {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Commit previousCommit;

    @EndNode
    private Commit nextCommit;

    public CommitHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commit getPreviousCommit() {
        return previousCommit;
    }

    public void setPreviousCommit(Commit previousCommit) {
        this.previousCommit = previousCommit;
    }

    public Commit getNextCommit() {
        return nextCommit;
    }

    public void setNextCommit(Commit nextCommit) {
        this.nextCommit = nextCommit;
    }
}
