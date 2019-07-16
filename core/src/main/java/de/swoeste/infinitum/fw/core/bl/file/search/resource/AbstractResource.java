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
package de.swoeste.infinitum.fw.core.bl.file.search.resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @author swoeste
 */
public abstract class AbstractResource implements Resource {

    // TODO java doc
    // FIXME logging!

    private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

    private static final String CR_LF = "\r\n";       //$NON-NLS-1$
    private static final String LF    = "\n";                   //$NON-NLS-1$

    private final Resource      parent;

    private final ResourceType  resourceType;
    private final String        resourcePath;
    private final String        resourceName;

    private final Charset       encoding;

    /**
     * Constructor for a new AbstractResource.
     *
     * @param path
     *            the path of this resource
     */
    public AbstractResource(final Resource parent, final ResourceType type, final String path, final String name) {
        this(parent, type, path, name, Charset.forName(UTF_8));
    }

    public AbstractResource(final Resource parent, final ResourceType type, final String path, final String name, final Charset encoding) {
        this.parent = parent;
        this.resourceType = type;
        this.resourcePath = path;
        this.resourceName = name;
        this.encoding = encoding;
    }

    /** {@inheritDoc} */
    @Override
    public ResourceType getType() {
        return this.resourceType;
    }

    /** {@inheritDoc} */
    @Override
    public Resource getParent() {
        return this.parent;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return this.resourceName;
    }

    /** {@inheritDoc} */
    @Override
    public String getPath() {
        return this.resourcePath;
    }

    /** {@inheritDoc} */
    @Override
    public String getFullQualifiedPath() {
        if (this.parent == null) {
            return this.resourcePath;
        }
        // TODO delimiter
        return this.parent.getFullQualifiedPath() + "!" + this.resourcePath;
    }

    /** {@inheritDoc} */
    @Override
    public Charset getEncoding() {
        return this.encoding;
    }

    /** {@inheritDoc} */
    @Override
    public final String getContentAsString() throws IOException {
        final String content = getContentAsStringInternal();
        return StringUtils.replace(content, CR_LF, LF);
    }

    protected abstract String getContentAsStringInternal() throws IOException;

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format("{0} [filePath={1}]", this.getClass().getSimpleName(), this.resourcePath); //$NON-NLS-1$
    }

    // TODO check hashCode / equals are correct?

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.parent == null) ? 0 : this.parent.hashCode());
        result = (prime * result) + ((this.resourcePath == null) ? 0 : this.resourcePath.hashCode());
        return result;
    }

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
        AbstractResource other = (AbstractResource) obj;
        if (this.parent == null) {
            if (other.parent != null) {
                return false;
            }
        } else if (!this.parent.equals(other.parent)) {
            return false;
        }
        if (this.resourcePath == null) {
            if (other.resourcePath != null) {
                return false;
            }
        } else if (!this.resourcePath.equals(other.resourcePath)) {
            return false;
        }
        return true;
    }

}
