/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.sleroy.junit.mail.server.events;

import com.github.sleroy.fakesmtp.model.EmailModel;

/**
 * The Class {@link RejectedMailEvent} defines an event when an email model has
 * been rejected.
 */
public class RejectedMailEvent {

    private final EmailModel model;

    /**
     * Instantiates a new rejected mail event.
     *
     * @param model
     *            the model
     */
    public RejectedMailEvent(final EmailModel model) {
	this.model = model;

    }

    public EmailModel getModel() {
	return model;
    }

}
