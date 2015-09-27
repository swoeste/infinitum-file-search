/*
 * Copyright (C) 2015 Sebastian Woeste
 * 
 * Licensed to Sebastian Woeste under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership. I license this file to You under
 * the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License
 * at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swoeste.infinitum.fw.core.bl.file.search.x1;

import java.nio.file.Path;

/**
 * @author swoeste
 */
public class FileSystemSearchConfiguration {

    private final Path    path;
    private final boolean searchArchives;
    private final int     depth;

    /**
     * Constructor for a new FileSystemSearchConfiguration.
     *
     * @param path
     * @param searchArchives
     */
    public FileSystemSearchConfiguration(final Path path, final boolean searchArchives) {
        this(path, searchArchives, Integer.MAX_VALUE);
    }

    /**
     * Constructor for a new FileSystemSearchConfiguration.
     *
     * @param path
     * @param searchArchives
     * @param depth
     */
    public FileSystemSearchConfiguration(final Path path, final boolean searchArchives, final int depth) {
        this.path = path;
        this.searchArchives = searchArchives;
        this.depth = depth;
    }

    /**
     * @return the path
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * @return the searchArchives
     */
    public boolean isSearchArchives() {
        return this.searchArchives;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return this.depth;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "FileSystemSearchConfiguration [path=" + this.path + ", searchArchives=" + this.searchArchives + ", depth=" + this.depth + "]";
    }

}
