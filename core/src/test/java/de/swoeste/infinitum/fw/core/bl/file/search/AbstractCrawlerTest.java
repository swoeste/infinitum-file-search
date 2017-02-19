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
package de.swoeste.infinitum.fw.core.bl.file.search;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

/**
 * @author swoeste
 */
public class AbstractCrawlerTest {

    private static final String TEST_FOLDER_MARKER = "root/test.marker"; //$NON-NLS-1$

    protected Path getTestFolder() {
        try {
            final URL resource = this.getClass().getClassLoader().getResource(TEST_FOLDER_MARKER);
            final Path markerFile = Paths.get(resource.toURI());
            return markerFile.getParent();
        } catch (URISyntaxException e) {
            Assert.fail();
            return null;
        }
    }

}
