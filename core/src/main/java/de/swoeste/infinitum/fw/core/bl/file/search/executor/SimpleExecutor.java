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
package de.swoeste.infinitum.fw.core.bl.file.search.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author swoeste
 */
public class SimpleExecutor implements Executor {

    private static final SimpleExecutor INSTANCE = new SimpleExecutor();

    private final ExecutorService       executorService;

    /**
     * Constructor for a new RunnableExecutor.
     */
    private SimpleExecutor() {
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public <T> Future<T> submit(final Callable<T> callable) {
        return this.executorService.submit(callable);
    }

    @Override
    public Future<?> submit(final Runnable runnable) {
        return this.executorService.submit(runnable);
    }

    public void shutdown() throws InterruptedException {
        // TODO check working behavior
        this.executorService.shutdown();
        if (!this.executorService.awaitTermination(60, TimeUnit.SECONDS)) { // optional
            // *
            // Logger.log("Executor did not terminate in the specified time.");
            // //optional *
            final List<Runnable> droppedTasks = this.executorService.shutdownNow(); // optional
            // **
            // Logger.log("Executor was abruptly shut down. " +
            // droppedTasks.size() + " tasks will not be executed."); //optional
            // **
        }
    }

    public static SimpleExecutor getInstance() {
        return INSTANCE;
    }

}
