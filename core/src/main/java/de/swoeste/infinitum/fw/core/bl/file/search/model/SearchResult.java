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
package de.swoeste.infinitum.fw.core.bl.file.search.model;

import java.text.MessageFormat;

/**
 * @author swoeste
 */
public class SearchResult {

    private final Resource resource;
    private final String   content;
    private final long     startIndex;
    private final long     endIndex;

    /**
     * Constructor for a new SearchResult.
     *
     * @param resource
     *            the resource this result belongs to
     * @param content
     *            the content of the resource this result belongs to
     * @param startIndex
     *            the start position within the content where the search found a match
     * @param endIndex
     *            the end position within the content where the search found a match
     */
    public SearchResult(final Resource resource, final String content, final long startIndex, final long endIndex) {
        this.resource = resource;
        this.content = content;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * The resource this result belongs to.
     *
     * @return the resource
     */
    public Resource getResource() {
        return this.resource;
    }

    /**
     * The content of the resource this result belongs to.
     *
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * The start position within the content where the search found a match.
     *
     * @return the startIndex
     */
    public long getStartIndex() {
        return this.startIndex;
    }

    /**
     * The end position within the content where the search found a match.
     *
     * @return the endIndex
     */
    public long getEndIndex() {
        return this.endIndex;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.content == null) ? 0 : this.content.hashCode());
        result = (prime * result) + (int) (this.endIndex ^ (this.endIndex >>> 32));
        result = (prime * result) + ((this.resource == null) ? 0 : this.resource.hashCode());
        result = (prime * result) + (int) (this.startIndex ^ (this.startIndex >>> 32));
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchResult other = (SearchResult) obj;
        if (this.content == null) {
            if (other.content != null) {
                return false;
            }
        } else if (!this.content.equals(other.content)) {
            return false;
        }
        if (this.endIndex != other.endIndex) {
            return false;
        }
        if (this.resource == null) {
            if (other.resource != null) {
                return false;
            }
        } else if (!this.resource.equals(other.resource)) {
            return false;
        }
        if (this.startIndex != other.startIndex) {
            return false;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("SearchResult [resource={0}, content={1}, startIndex={2}, endIndex={3}]",  //$NON-NLS-1$
                this.resource, this.content, this.startIndex, this.endIndex);
    }

}
