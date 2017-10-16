/*
 * Copyright (C) 2017 Sebastian Woeste
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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.task;


import de.swoeste.infinitum.fw.core.bl.file.search.executor.Executor;
import javafx.concurrent.Task;


/**
 * @author swoeste
 *
 */
public class SearchContentTask extends Task<Void> {

    Executor x;

    /** {@inheritDoc} */
    @Override
    protected Void call() throws Exception {
        // TODO Auto-generated method stub

        // this.getMessage()
        // this.getProgress()
        // this.getState()
        // this.getTitle()
        // this.getTotalWork()
        // this.getValue()
        // this.getWorkDone()
        return null;
    }

    // EXAMPL
    /*
     * import javafx.concurrent.Task;
     *
     * Task task = new Task<Void>() {
     *
     * @Override public Void call() { static final int max = 1000000; for (int i=1; i<=max; i++) { if (isCancelled()) {
     * break; } updateProgress(i, max); } return null; } }; ProgressBar bar = new ProgressBar();
     * bar.progressProperty().bind(task.progressProperty()); new Thread(task).start();
     */

}
