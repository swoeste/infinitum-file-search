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
package de.swoeste.infinitum.fw.core.bl.file.search.x2;

import java.util.List;
import java.util.Queue;

import de.swoeste.infinitum.fw.core.bl.file.search.Executor;
import de.swoeste.infinitum.fw.core.bl.file.search.filter.Filter;
import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;

/**
 * @author swoeste
 */
public class FileContentSearchConfiguration {

    private final Queue<Resource> files;
    private final List<Filter> filters;

    private final String       fileContent;

    private final Executor     executor;
    private final int          instances;

    /**
     * Constructor for a new FileContentSearchConfiguration.
     *
     * @param files
     * @param filters
     * @param fileContent
     * @param executor
     * @param instances
     */
    public FileContentSearchConfiguration(final Queue<Resource> files, final List<Filter> filters, final String fileContent, final Executor executor, final int instances) {
        this.files = files;
        this.filters = filters;
        this.fileContent = fileContent;
        this.executor = executor;
        this.instances = instances;
    }

    /**
     * @return the files
     */
    public Queue<Resource> getFiles() {
        return this.files;
    }

    /**
     * @return the filters
     */
    public List<Filter> getFilters() {
        return this.filters;
    }

    /**
     * @return the fileContent
     */
    public String getFileContent() {
        return this.fileContent;
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

}
