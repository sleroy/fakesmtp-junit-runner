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
package io.sleroy.junit.mail.server;

import java.util.ArrayList;
import java.util.List;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

// TODO: Auto-generated Javadoc
/**
 * The factory interface for creating authentication handlers.
 *
 * @author jasonpenny
 * @since 1.2
 */
final class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {

	/** The Constant LOGIN_MECHANISM. */
	public static final String LOGIN_MECHANISM = "LOGIN";

	/** The smtp auth handler. */
	private AuthenticationHandler smtpAuthHandler;

	/**
	 * Instantiates a new SMTP auth handler factory.
	 *
	 * @param smtpAuthHandler
	 *            the smtp auth handler
	 */
	public SMTPAuthHandlerFactory(AuthenticationHandler smtpAuthHandler) {
		super();
		this.smtpAuthHandler = smtpAuthHandler;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.subethamail.smtp.AuthenticationHandlerFactory#create()
	 */
	@Override
	public AuthenticationHandler create() {
		return smtpAuthHandler;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.subethamail.smtp.AuthenticationHandlerFactory#
	 * getAuthenticationMechanisms()
	 */
	@Override
	public List<String> getAuthenticationMechanisms() {
		List<String> result = new ArrayList<String>();
		result.add(SMTPAuthHandlerFactory.LOGIN_MECHANISM);
		return result;
	}
}
