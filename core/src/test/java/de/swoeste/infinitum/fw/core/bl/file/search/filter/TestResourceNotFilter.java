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
package de.swoeste.infinitum.fw.core.bl.file.search.filter;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * @author swoeste
 */
@Test
@SuppressWarnings("nls")
public class TestResourceNotFilter {

    public void testAcceptNotFalse() {
        final ResourceFilter mock = createResourceFilter(false);
        final ResourceNotFilter filter = new ResourceNotFilter(mock);
        assertTrue(filter.accept(null));
    }

    public void testAcceptNotTrue() {
        final ResourceFilter mock = createResourceFilter(true);
        final ResourceNotFilter filter = new ResourceNotFilter(mock);
        assertFalse(filter.accept(null));
    }

    private ResourceFilter createResourceFilter(final boolean accecpt) {
        final ResourceFilter mock = createNiceMock(ResourceFilter.class);
        expect(mock.accept(anyObject())).andReturn(accecpt).anyTimes();
        replay(mock);
        return mock;
    }

}
