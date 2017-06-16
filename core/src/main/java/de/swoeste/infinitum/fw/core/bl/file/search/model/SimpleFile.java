/*
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
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author swoeste
 */
public class SimpleFile extends AbstractResource {

    /**
     * Constructor for a new SimpleFile.
     *
     * @param filePath
     */
    public SimpleFile(final Path filePath) {
        super(filePath);
    }

    /** {@inheritDoc} */
    @Override
    public String getContentAsString() throws IOException {
        final byte[] bytes = Files.readAllBytes(getFilePath());
        return new String(bytes, getEncoding());
    }

}
