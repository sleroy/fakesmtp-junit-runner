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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.fakesmtp.core.exception.BindPortException;
import com.github.sleroy.fakesmtp.core.exception.InvalidHostException;
import com.github.sleroy.fakesmtp.core.exception.InvalidPortException;
import com.github.sleroy.fakesmtp.core.exception.OutOfRangePortException;
import com.github.sleroy.fakesmtp.model.EmailModel;
import com.github.sleroy.fakesmtp.model.MailServerModel;

// TODO: Auto-generated Javadoc
/**
 * The Class MailServer initializes the MailServer.
 */
public class MailServer implements AutoCloseable {

	/** The smtp auth handler. */
	private SMTPAuthHandler smtpAuthHandler;

	/** The server configuration. */
	private ServerConfiguration serverConfiguration;

	/** The mail saver. */
	private MailSaver mailSaver;

	/** The mail listener. */
	private MailListener mailListener;

	/** The smtp auth handler factory. */
	private SMTPAuthHandlerFactory smtpAuthHandlerFactory;

	/** The smtp server handler. */
	private SMTPServerHandler smtpServerHandler;

	/** The mail server model. */
	private MailServerModel mailServerModel;

	/** The started. */
	private boolean started = false;

	/**
	 * Instantiates a new mail server.
	 *
	 * @param serverConfiguration
	 *            the server configuration
	 */
	public MailServer(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;

	}

	/**
	 * Start.
	 *
	 * @throws BindPortException
	 *             the bind port exception
	 * @throws OutOfRangePortException
	 *             the out of range port exception
	 * @throws InvalidPortException
	 *             the invalid port exception
	 * @throws InvalidHostException
	 *             the invalid host exception
	 */
	public void start() throws BindPortException, OutOfRangePortException, InvalidPortException, InvalidHostException {

		if (started) {
			LOGGER.warn("The server has been already started");
			// Do nothing. We can't stop the server. User has to quit the app
			// (issue with SubethaSMTP)
		} else {
			// Model
			LOGGER.info("Configuration of the server. {}", serverConfiguration);

			mailServerModel = new MailServerModel(); // Cycle

			smtpAuthHandler = new SMTPAuthHandler(serverConfiguration.getAuthentication().getUserName(),
					serverConfiguration.getAuthentication().getPassword());

			smtpAuthHandlerFactory = new SMTPAuthHandlerFactory(smtpAuthHandler);

			mailSaver = new MailSaver(serverConfiguration);

			// Adding MailServerModel to obtain the notifications
			mailSaver.addObserver(mailServerModel);

			mailListener = new MailListener(mailSaver);

			smtpServerHandler = new SMTPServerHandler(mailServerModel, mailListener, mailSaver, smtpAuthHandlerFactory);

			String hostStr = serverConfiguration.getBindAddress();
			started = false;
			try {
				int port = Integer.parseInt(serverConfiguration.getPort());
				InetAddress host = null;
				if (hostStr != null && !hostStr.isEmpty()) {
					host = InetAddress.getByName(hostStr);
				}

				smtpServerHandler.startServer(port, host);
				started = true;
			} catch (NumberFormatException e) {
				throw new InvalidPortException(e);
			} catch (UnknownHostException e) {
				throw new InvalidHostException(e, hostStr);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {
		LOGGER.info("Stopping the server");
		if (smtpServerHandler != null) {
			smtpServerHandler.stopServer();
		}

	}

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailServer.class);

	/**
	 * Checks if the mail server is running.
	 *
	 * @return true, if is running
	 */
	public boolean isRunning() {

		return started;
	}

	/**
	 * Gets the mails of the server.
	 *
	 * @return the mails
	 */
	public List<EmailModel> getMails() {

		return this.mailServerModel.getEmailModels();
	}

	/**
	 * Gets the relay domains.
	 *
	 * @return the relay domains
	 */
	public List<String> getRelayDomains() {
		return this.serverConfiguration.getRelayDomains();
	}

	/**
	 * Gets the rejected mails from the server.
	 *
	 * @return the rejected mails
	 */
	public List<EmailModel> getRejectedMails() {

		return this.mailServerModel.getRejectedMails();
	}

}
