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
package de.swoeste.infinitum.fw.core.bl.file.search.resource.file;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.swoeste.infinitum.fw.core.bl.file.search.TestUtils;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestSimpleFile {

    public void testGetParentWithExistingFile() {
        final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
        Assert.assertNull(existingFile.getParent());
    }

    public void testGetParentWithNonExistingFile() {
        final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
        Assert.assertNull(nonExistingFile.getParent());
    }

    public void testGetNameWithExistingFile() {
        final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
        final String expectedName = TestUtils.getExistingSimpleFilePath().toFile().getName();
        Assert.assertEquals(existingFile.getName(), expectedName);
    }

    public void testGetNameWithNonExistingFile() {
        final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
        final String expectedName = TestUtils.getNonExistingSimpleFilePath().toFile().getName();
        Assert.assertEquals(nonExistingFile.getName(), expectedName);
    }

    public void testGetPathWithExistingFile() {
        final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
        final String expectedPath = TestUtils.getExistingSimpleFilePath().toAbsolutePath().toString();
        Assert.assertEquals(existingFile.getPath(), expectedPath);
    }

    public void testGetPathWithNonExistingFile() {
        final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
        final String expectedPath = TestUtils.getNonExistingSimpleFilePath().toAbsolutePath().toString();
        Assert.assertEquals(nonExistingFile.getPath(), expectedPath);
    }

    public void testGetContentAsByteArrayWithExistingFile() {
        try {
            final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
            final byte[] contentAsByteArray = existingFile.getContentAsByteArray();
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 3);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

    public void testGetContentAsByteArrayWithNoneExistingFile() {
        try {
            final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
            final byte[] contentAsByteArray = nonExistingFile.getContentAsByteArray();
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

    public void testGetContentAsByteArrayWithLengthWithExistingFile() {
        try {
            final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
            final byte[] contentAsByteArray = existingFile.getContentAsByteArray(2);
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 2);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

    public void testGetContentAsByteArrayWithLengthWithNoneExistingFile() {
        try {
            final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
            final byte[] contentAsByteArray = nonExistingFile.getContentAsByteArray(2);
            Assert.assertNotNull(contentAsByteArray);
            Assert.assertEquals(contentAsByteArray.length, 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

    public void testGetContentAsStringInternalWithExistingFile() {
        try {
            final SimpleFile existingFile = TestUtils.getExistingSimpleFile();
            final String contentAsString = existingFile.getContentAsString();
            Assert.assertNotNull(contentAsString);
            Assert.assertTrue(contentAsString.length() > 0);
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

    public void testGetContentAsStringInternalWithNonExistingFile() {
        try {
            final SimpleFile nonExistingFile = TestUtils.getNonExistingSimpleFile();
            Assert.assertNull(nonExistingFile.getContentAsString());
        } catch (Exception ex) {
            Assert.fail("This test does not expect an excpetion: ", ex);
        }
    }

}
