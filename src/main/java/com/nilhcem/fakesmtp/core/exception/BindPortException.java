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
package com.nilhcem.fakesmtp.core.exception;

/**
 * Thrown if the SMTP port cannot be bound while trying to start the server.
 * <p>
 * A port cannot be bound...
 * </p>
 * <ul>
 *   <li>If it is already use by another application;</li>
 *   <li>If the user is not allowed to open it.<br>
 *   For example on Unix-like machines, we need to be root to open a port {@literal <} 1024.</li>
 * </ul>
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class BindPortException extends AbstractPortException {

	private static final long serialVersionUID = -4019988153141714187L;

	public BindPortException(Exception e, int port) {
		super(e, port);
	}
}
