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

/**
 * @author swoeste
 */
public interface Resource {

    /**
     * The parent of the resource
     *
     * @return the parent or null, if the resource has no parent
     */
    Resource getParent();

    /**
     * The type of the resource
     *
     * @return the type
     */
    ResourceType getType();

    /**
     * The name of the resource
     *
     * @return the name
     */
    String getName();

    /**
     * The path of the resource (as string) including the name
     *
     * @return the path as string
     */
    String getPath();

    // TODO getAbsolutePath() // getFullPath() ???
    String getFullQualifiedPath();

    /**
     * The content of the resource (as string)
     *
     * @return the content as string
     * @throws IOException
     */
    String getContentAsString() throws IOException;

    /**
     * The encoding which should be used for reading this files content.
     *
     * @return the encoding
     */
    Charset getEncoding();

    // TODO check that all user close the stream!!!???

    byte[] getContentAsByteArray() throws IOException;

    byte[] getContentAsByteArray(int length) throws IOException;

}
