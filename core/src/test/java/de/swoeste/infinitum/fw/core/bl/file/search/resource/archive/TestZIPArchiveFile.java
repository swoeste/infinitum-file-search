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
package de.swoeste.infinitum.fw.core.bl.file.search.resource.archive;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.TestUtils;
import de.swoeste.infinitum.fw.core.bl.file.search.resource.Resource;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestZIPArchiveFile {

    public void testGetParentWithExistingFile() {
        final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
        final Resource parent = existingFile.getParent();
        Assert.assertNotNull(parent);
        Assert.assertEquals(parent, TestUtils.getExistingZIPArchiveFileParent());
    }

    public void testGetParentWithNonExistingFile() {
        final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
        final Resource parent = nonExistingFile.getParent();
        Assert.assertNotNull(parent);
        Assert.assertEquals(parent, TestUtils.getExistingZIPArchiveFileParent());
    }

    public void testGetNameWithExistingFile() {
        final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
        final String expectedName = TestUtils.getExistingSimpleFileName();
        Assert.assertEquals(existingFile.getName(), expectedName);
    }

    public void testGetNameWithNonExistingFile() {
        final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
        final String expectedName = TestUtils.getNonExistingZIPArchiveFileName();
        Assert.assertEquals(nonExistingFile.getName(), expectedName);
    }

    public void testGetPathWithExistingFile() {
        final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
        final String expectedPath = TestUtils.getExistingZIPArchiveFilePathAsString();
        Assert.assertEquals(existingFile.getPath(), expectedPath);
    }

    public void testGetPathWithNonExistingFile() {
        final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
        final String expectedPath = TestUtils.getNonExistingZIPArchiveFilePathAsString();
        Assert.assertEquals(nonExistingFile.getPath(), expectedPath);
    }

    public void testGetContentAsByteArrayWithExistingFile() {
        try {
            final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
            final byte[] contentAsByteArray = existingFile.getContentAsByteArray();
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 3);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

    public void testGetContentAsByteArrayWithNoneExistingFile() {
        try {
            final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
            final byte[] contentAsByteArray = nonExistingFile.getContentAsByteArray();
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

    public void testGetContentAsByteArrayWithLengthWithExistingFile() {
        try {
            final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
            final byte[] contentAsByteArray = existingFile.getContentAsByteArray(2);
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 2);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

    public void testGetContentAsByteArrayWithLengthWithNoneExistingFile() {
        try {
            final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
            final byte[] contentAsByteArray = nonExistingFile.getContentAsByteArray(2);
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

    public void testGetContentAsStringInternalWithExistingFile() {
        try {
            final ArchiveFileEntry existingFile = TestUtils.getExistingZIPArchiveFile();
            final String contentAsString = existingFile.getContentAsString();
            Assert.assertNotNull(contentAsString);
            Assert.assertTrue(contentAsString.length() > 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

    public void testGetContentAsStringInternalWithNonExistingFile() {
        try {
            final ArchiveFileEntry nonExistingFile = TestUtils.getNonExistingZIPArchiveFile();
            Assert.assertNull(nonExistingFile.getContentAsString());
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex); //$NON-NLS-1$
        }
    }

}
