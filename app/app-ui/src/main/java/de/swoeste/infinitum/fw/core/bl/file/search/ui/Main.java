/*-
 * Copyright (C) 2019 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.ui;

/**
 * This wrapper main class is required to bundle JavaFX into an UBER JAR and
 * execute it. Due to the internal behavior of JavaFX it is not possible to
 * bundle it and execute it with a main class that is extending "Application".
 *
 * @author swoeste
 */
public class Main {

    public static void main(final String[] args) {
        FileSearchApplication.main(args);
    }

}
