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
package de.swoeste.infinitum.fw.core.bl.file.search.util;


/**
 * @author swoeste
 */
public class ByteArrayUtils {

    // TODO java doc
    // FIXME logging!

    private ByteArrayUtils() {
        // hidden
    }

    /**
     * Does this byte array begin with match array content?
     *
     * @param source Byte array to examine
     * @param match Byte array to locate in <code>source</code>
     * @return true If the starting bytes are equal
     */
    public static boolean startsWith( final byte[] source, final byte[] match ) {
        if ( match.length > source.length ) {
            return false;
        }

        for ( int i = 0; i < match.length; i++ ) {
            if ( source[i] != match[i] ) {
                return false;
            }
        }

        return true;
    }

}
