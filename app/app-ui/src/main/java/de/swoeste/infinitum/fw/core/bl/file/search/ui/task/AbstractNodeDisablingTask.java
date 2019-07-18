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
package de.swoeste.infinitum.fw.core.bl.file.search.ui.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.concurrent.Task;
import javafx.scene.Node;

/**
 * This abstract task disables all provided nodes on task start an re-enables
 * them as soon as the task has finished (or an error occurred).
 *
 * @author swoeste
 */
public abstract class AbstractNodeDisablingTask<V> extends Task<V> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNodeDisablingTask.class);

    private final List<Node>    nodes;

    /**
     * Constructor for a new AbstractNodeDisablingTask.
     *
     * @param nodes
     *            the nodes to disable during task execution
     */
    public AbstractNodeDisablingTask(final List<Node> nodes) {
        this.nodes = nodes;
    }

    private final void changeNodesDisabledStateTo(final boolean state) {
        for (Node node : this.nodes) {
            node.setDisable(state);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected final V call() throws Exception {
        try {
            changeNodesDisabledStateTo(true);
            return callInternal();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            changeNodesDisabledStateTo(false);
        }
    }

    protected abstract V callInternal() throws Exception; // NOSONAR this is by
                                                          // API design of Task

}
