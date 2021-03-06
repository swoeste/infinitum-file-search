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
package de.swoeste.infinitum.fw.core.bl.file.search.filter;

import java.util.regex.Pattern;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * This filter is capable of filtering the path of a resource with a regular expression.
 *
 * @author swoeste
 */
public class ResourcePathFilter implements ResourceFilter {

    // TODO java doc
    // FIXME logging!

    private final Pattern pattern;

    /**
     * Constructor for a new ResourceNameFilter.
     *
     * @param regEx
     *            a regular expression to filter the path
     */
    public ResourcePathFilter(final String regEx) {
        this.pattern = Pattern.compile(regEx);
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(final Resource resource) {
        final String filePath = resource.getPath();
        if (filePath != null) {
        return this.pattern.matcher(filePath).matches();
        }  else {
            // TODO logging
            return false;
        }
    }

}
