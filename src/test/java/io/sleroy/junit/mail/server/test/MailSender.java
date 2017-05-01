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
package io.sleroy.junit.mail.server.test;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;

public class MailSender {

	private String host;
	private int port;

	public MailSender(String localhost, int port) {
		super();
		this.host = localhost;
		this.port = port;
	}

	/**
	 * Send mail.
	 *
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 * @throws MessagingException
	 *             the messaging exception
	 */
	public void sendMail(String from, String to) throws MessagingException {
		sendMail(from, to, UUID.randomUUID().toString(), UUID.randomUUID().toString());
	}

	/**
	 * Send mail.
	 *
	 * @param from
	 *            Sender's email ID needs to be mentioned
	 * @param to
	 *            Recipient's email ID needs to be mentioned.
	 * @param subject
	 *            the subject
	 * @throws MessagingException
	 */
	public void sendMail(String from, String to, String subject, String body) throws MessagingException {

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", Integer.toString(port));

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		Transport transport = null;
		try {
			transport = session.getTransport();

			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setText(body);

			// Send message
			transport.send(message);
			System.out.println("Sent message successfully....");
		} finally {
			if (transport != null) {
				transport.close();
			}
		}

	}

}
