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

import java.io.IOException;
import java.io.InputStream;
import org.subethamail.smtp.helper.SimpleMessageListener;

/**
 * Listens to incoming emails and redirects them to the {@code MailSaver} object.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MailListener implements SimpleMessageListener {
	private final MailSaverInterface saver;

	/**	 
	 * Creates the listener.
	 *
	 * @param saver a {@code MailServer} object used to save emails and notify components.
	 */
	public MailListener(MailSaverInterface saver) {
		this.saver = saver;
	}

	/**
	 * Accepts all kind of email <i>(always return true)</i>.
	 * <p>
	 * Called once for every RCPT TO during a SMTP exchange.<br>
     * Each accepted recipient will result in a separate deliver() call later.
     * </p>
     *
	 * @param from the user who send the email.
	 * @param recipient the recipient of the email.
	 * @return always return {@code true}
	 */
	public boolean accept(String from, String recipient) {
		return true;
	}

    /**
     * Receives emails and forwards them to the {@link MailSaver} object.
     */
	@Override
	public void deliver(String from, String recipient, InputStream data) throws IOException {
		saver.saveEmailAndNotify(from, recipient, data);
	}
}
