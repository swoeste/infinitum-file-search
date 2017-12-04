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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @author swoeste
 */
public abstract class AbstractResource implements Resource {

    // TODO make me configurable
    private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

    private static final String CR_LF = "\r\n";        //$NON-NLS-1$
    private static final String LF    = "\n";                              //$NON-NLS-1$

    private final Path          filePath;
    private final Charset       encoding;

    /**
     * Constructor for a new AbstractResource.
     *
     * @param filePath
     *            the path of this resource
     */
    public AbstractResource(final Path filePath) {
        this.filePath = filePath;
        this.encoding = Charset.forName(UTF_8);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return this.filePath.toFile().getName();
    }

    /**
     * The path of this resource
     *
     * @return the filePath
     */
    public Path getFilePath() {
        return this.filePath;
    }

    /** {@inheritDoc} */
    @Override
    public String getPathAsString() {
        return this.filePath.toString();
    }

    /**
     * The encoding which should be used for reading this files content.
     *
     * @return the encoding
     */
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
        return MessageFormat.format("{0} [filePath={1}]", this.getClass().getSimpleName(), this.filePath); //$NON-NLS-1$
    }

}
