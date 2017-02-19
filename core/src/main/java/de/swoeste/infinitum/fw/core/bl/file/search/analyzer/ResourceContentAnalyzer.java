/*
 * Copyright (C) 2016 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.analyzer;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.swoeste.infinitum.fw.core.bl.file.search.model.Resource;
import de.swoeste.infinitum.fw.core.bl.file.search.model.SearchResult;

/**
 * @author swoeste
 */
public class ResourceContentAnalyzer implements ResourceAnalyzer {

    private final Pattern searchPattern;

    /**
     * Constructor for a new ResourceContentAnalyzer.
     *
     * @param search
     */
    public ResourceContentAnalyzer(final String search) {
        this.searchPattern = Pattern.compile(search);
    }

    /** {@inheritDoc} */
    @Override
    public List<SearchResult> analyze(final Resource resource) {
        try {
            final List<SearchResult> result = new ArrayList<>();
            final String contentToAnalyze = resource.getContentAsString();

            final Matcher matcher = this.searchPattern.matcher(contentToAnalyze);
            while (matcher.find()) {
                final int start = matcher.start();
                final int end = matcher.end();
                if (start != end) {
                    final String match = matcher.group();
                    result.add(new SearchResult(resource, match, start, end));
                }
            }

            return result;
        } catch (IOException ex) {
            final String message = MessageFormat.format("Unable to analyze: {0}", resource); //$NON-NLS-1$
            throw new IllegalArgumentException(message, ex);
        }
    }

}
