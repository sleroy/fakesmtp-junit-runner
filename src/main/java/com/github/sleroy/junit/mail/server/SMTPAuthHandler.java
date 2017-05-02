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
package com.github.sleroy.junit.mail.server;

import org.subethamail.smtp.AuthenticationHandler;

/**
 * Simulates an authentication handler to allow capturing emails that are set up
 * with login authentication.
 *
 * @author jasonpenny
 * @since 1.2
 */
final class SMTPAuthHandler implements AuthenticationHandler {
	public static final String USER_IDENTITY = "User";

	private int pass = 0;

	private String userName;

	private String password;

	// private static final String PROMPT_USERNAME = "334 VXNlcm5hbWU6"; //
	// VXNlcm5hbWU6 is base64 for "Username:"
	// private static final String PROMPT_PASSWORD = "334 UGFzc3dvcmQ6"; //
	// UGFzc3dvcmQ6 is base64 for "Password:"

	public SMTPAuthHandler(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Simulates an authentication process.
	 * <p>
	 * <ul>
	 * <li>first prompts for username;</li>
	 * <li>then, prompts for password;</li>
	 * <li>finally, returns {@code null} to finish the authentication
	 * process;</li>
	 * </ul>
	 * </p>
	 *
	 * @return <code>null</code> if the authentication process is finished,
	 *         otherwise a string to hand back to the client.
	 * @param clientInput
	 *            The client's input, eg "AUTH PLAIN dGVzdAB0ZXN0ADEyMzQ="
	 */
	@Override
	public String auth(String clientInput) {
		String prompt;

		if (++pass == 1) {
			prompt = userName;
		} else if (pass == 2) {
			prompt = password;
		} else {
			pass = 0;
			prompt = null;
		}
		return prompt;
	}

	/**
	 * If the authentication process was successful, this returns the identity
	 * of the user. The type defining the identity can vary depending on the
	 * authentication mechanism used, but typically this returns a String
	 * username. If authentication was not successful, the return value is
	 * undefined.
	 */
	@Override
	public Object getIdentity() {
		return SMTPAuthHandler.USER_IDENTITY;
	}
}
