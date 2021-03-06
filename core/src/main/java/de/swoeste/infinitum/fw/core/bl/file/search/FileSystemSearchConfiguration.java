/*-
 * Copyright (C) 2017 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;

import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.ResourceFilter;

/**
 * @author swoeste
 */
public class FileSystemSearchConfiguration {

    // TODO shared base class with filecontentsearchconfiguration ?

    // TODO java doc
    // FIXME logging!

    private final Path                 path;
    private final List<ResourceFilter> filters;
    private final boolean              searchArchives;
    private final int                  depth;
    private final Executor             executor;
    private final int                  instances;

    // TODO reduce constructors?

    /**
     * Constructor for a new FileSystemSearchConfiguration.
     *
     * @param path
     * @param filters
     * @param searchArchives
     */
    public FileSystemSearchConfiguration(final Path path, final List<ResourceFilter> filters, final boolean searchArchives, final Executor executor, final int instances) {
        this(path, filters, searchArchives, Integer.MAX_VALUE, executor, instances);
    }

    /**
     * Constructor for a new FileSystemSearchConfiguration.
     *
     * @param path
     * @param filters
     * @param searchArchives
     * @param depth
     */
    public FileSystemSearchConfiguration(final Path path, final List<ResourceFilter> filters, final boolean searchArchives, final int depth, final Executor executor,
            final int instances) {
        this.path = path;
        this.filters = filters;
        this.searchArchives = searchArchives;
        this.depth = depth;
        this.executor = executor;
        this.instances = instances;
    }

    /**
     * Constructor for a new FileSystemSearchConfiguration.
     *
     * @param path
     * @param filters
     * @param searchArchives
     * @param includeSubDirectories
     */
    public FileSystemSearchConfiguration(final Path path, final List<ResourceFilter> filters, final boolean searchArchives, final boolean includeSubDirectories,
            final Executor executor, final int instances) {
        this.path = path;
        this.filters = filters;
        this.searchArchives = searchArchives;
        this.depth = includeSubDirectories ? Integer.MAX_VALUE : 1;
        this.executor = executor;
        this.instances = instances;
    }

    /**
     * @return the path
     */
    public Path getPath() {
        return this.path;
    }

    /**
     * @return the filters
     */
    public List<ResourceFilter> getFilters() {
        return this.filters;
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

    /**
     * @return the executor
     */
    public Executor getExecutor() {
        return this.executor;
    }

    /**
     * @return the instances
     */
    public int getInstances() {
        return this.instances;
    }

    // TODO recreate

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("FileSystemSearchConfiguration [path={0}, filters={1}, searchArchives={2}, depth={3}]", //$NON-NLS-1$
                this.path, this.filters, this.searchArchives, this.depth);
    }

}
