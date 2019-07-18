/*-
 * Copyright (C) 2018 Sebastian Woeste
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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;

import de.swoeste.infinitum.fw.core.bl.file.search.resource.ResourceType;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.archive.ArchiveEntry;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.archive.ZIPArchiveEntry;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.file.SimpleFile;

/**
 * @author swoeste
 */
@SuppressWarnings("nls")
public class TestUtils {

    public static final String ENCODING           = "UTF-8";
    public static final String TEST_FOLDER_MARKER = "root/test.marker";
    public static final String TEST_CONTENT_FILE  = "test.content";

    public static SimpleFile getExistingSimpleFile() {
        final Path testFile = getExistingSimpleFilePath();
        Assert.assertTrue(testFile.toFile().exists());
        return new SimpleFile(ResourceType.UNKNOWN, testFile);
    }

    public static String getExistingSimpleFileName() {
        return "1.txt"; //$NON-NLS-1$
    }

    public static Path getExistingSimpleFilePath() {
        final Path testFolder = TestUtils.getTestFolder();
        return testFolder.resolve(getExistingSimpleFilePathAsString());
    }

    public static String getExistingSimpleFilePathAsString() {
        return "5/" + getExistingSimpleFileName(); //$NON-NLS-1$
    }

    public static ArchiveEntry getExistingZIPArchiveFile() {
        final SimpleFile parent = getExistingZIPArchiveFileParent();
        return new ZIPArchiveEntry(parent, ResourceType.UNKNOWN, getExistingZIPArchiveEntryPathAsString(), getExistingZIPArchiveEntryName());
    }

    public static SimpleFile getExistingZIPArchiveFileParent() {
        final Path testFolder = TestUtils.getTestFolder();
        final Path testFile = testFolder.resolve(getExistingZIPArchiveFileParentPathAsString());
        Assert.assertTrue(testFile.toFile().exists());
        return new SimpleFile(ResourceType.ZIP, testFile);
    }

    public static String getExistingZIPArchiveFileParentPathAsString() {
        return "5.zip";
    }

    public static String getExistingZIPArchiveEntryPathAsString() {
        return "5/" + getExistingZIPArchiveEntryName();
    }

    public static String getExistingZIPArchiveEntryName() {
        return "1.txt"; //$NON-NLS-1$
    }

    public static String getNonExistingSimpleFilePathAsString() {
        return "5/" + getNonExistingSimpleFileName(); //$NON-NLS-1$
    }

    public static SimpleFile getNonExistingSimpleFile() {
        final Path testFile = getNonExistingSimpleFilePath();
        Assert.assertFalse(testFile.toFile().exists());
        return new SimpleFile(ResourceType.UNKNOWN, testFile);
    }

    public static String getNonExistingSimpleFileName() {
        return "doesnotexist.txt"; //$NON-NLS-1$
    }

    public static Path getNonExistingSimpleFilePath() {
        final Path testFolder = TestUtils.getTestFolder();
        return testFolder.resolve(getNonExistingSimpleFilePathAsString());
    }

    public static ArchiveEntry getNonExistingZIPArchiveFile() {
        final SimpleFile simpleFile = getExistingZIPArchiveFileParent();
        return new ZIPArchiveEntry(simpleFile, ResourceType.UNKNOWN, getNonExistingZIPArchiveEntryPathAsString(), getNonExistingZIPArchiveEntryName());
    }

    public static String getNonExistingZIPArchiveEntryName() {
        return "doesnotexist.txt"; //$NON-NLS-1$
    }

    public static SimpleFile getNonExistingZIPArchiveFileParent() {
        final Path testFolder = TestUtils.getTestFolder();
        final Path testFile = testFolder.resolve(getNonExistingZIPArchiveFileParentPathAsString());
        Assert.assertFalse(testFile.toFile().exists());
        return new SimpleFile(ResourceType.ZIP, testFile);
    }

    public static String getNonExistingZIPArchiveFileParentPathAsString() {
        return "doesnotexist.zip";
    }

    public static String getNonExistingZIPArchiveEntryPathAsString() {
        return "5/" + getNonExistingZIPArchiveEntryName(); //$NON-NLS-1$
    }

    public static Set<String> getTestContent() {
        try {
            final File file = new File(getTestFolder().toAbsolutePath().toFile(), TEST_CONTENT_FILE);
            final List<String> lines = FileUtils.readLines(file, ENCODING);

            final Set<String> result = new HashSet<>();

            for (final String string : lines) {
                result.add(StringUtils.trim(string));
            }

            return result;
        } catch (final IOException e) {
            Assert.fail();
            return null;
        }
    }

    public static Path getTestFolder() {
        try {
            final URL resource = TestUtils.class.getClassLoader().getResource(TEST_FOLDER_MARKER);
            final Path markerFile = Paths.get(resource.toURI());
            return markerFile.getParent();
        } catch (final URISyntaxException e) {
            Assert.fail();
            return null;
        }
    }

}
