/*
 * Copyright (C) 2015 Sebastian Woeste
 *
 * Licensed to Sebastian Woeste under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. I license this file to You under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package de.swoeste.infinitum.fw.core.bl.file.search.model;


import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.MessageFormat;


/**
 * @author swoeste
 */
public abstract class AbstractResource implements Resource {

    private final Path    filePath;
    private final Charset encoding;

    /**
     * Constructor for a new AbstractResource.
     *
     * @param filePath
     */
    public AbstractResource( final Path filePath ) {
        this.filePath = filePath;
        this.encoding = Charset.forName( "UTF-8" ); //$NON-NLS-1$ // TODO make it
                                                    // configurable
    }

    /** {@inheritDoc} */
    @Override
    public String getFileName() {
        return this.filePath.toFile().getName();
    }

    /**
     * @return the filePath
     */
    public Path getFilePath() {
        return this.filePath;
    }

    /** {@inheritDoc} */
    @Override
    public String getFilePathAsString() {
        return this.filePath.toString();
    }

    /**
     * @return the encoding
     */
    public Charset getEncoding() {
        return this.encoding;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return MessageFormat.format( "{0} [filePath={1}]", this.getClass().getSimpleName(), this.filePath ); //$NON-NLS-1$
    }

}
