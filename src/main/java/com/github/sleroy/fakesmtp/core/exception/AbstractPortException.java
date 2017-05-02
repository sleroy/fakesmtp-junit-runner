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
package com.github.sleroy.fakesmtp.core.exception;

/**
 * Abstract class to simplify creation of exceptions due to a SMTP port error.
 *
 * @author Nilhcem
 * @since 1.0
 */
abstract class AbstractPortException extends Exception {
	private static final long serialVersionUID = 9011196541962512429L;
	private final int port;

	/**
	 * Copies the stack trace of the exception passed in parameter, and sets the port which caused the exception.
	 *
	 * @param e the exception we need to copy the stack trace from.
	 * @param port the selected port which was the cause of the exception.
	 */
	public AbstractPortException(Exception e, int port) {
		setStackTrace(e.getStackTrace());
		this.port = port;
	}

	/**
	 * Returns the port entered by the user.
	 * <p>
	 * Useful to know why the SMTP server could not start.
	 * </p>
	 *
	 * @return the port which caused the exception.
	 */
	public int getPort() {
		return port;
	}
}
