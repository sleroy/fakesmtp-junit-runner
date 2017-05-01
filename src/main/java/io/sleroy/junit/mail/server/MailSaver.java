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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.ServerConfiguration;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.MailServerModel;

import io.sleroy.junit.mail.server.events.NewMailEvent;
import io.sleroy.junit.mail.server.events.RejectedMailEvent;

/**
 * Saves emails and notifies components so they can refresh their views with new
 * data.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MailSaver extends Observable implements MailSaverInterface {

	/** The Constant LOGGER. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(MailSaver.class);

	/** The Constant LINE_SEPARATOR. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/** The Constant SUBJECT_PATTERN. */
	// This can be a static variable since it is Thread Safe
	private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");

	protected Charset storageCharSet;

	/** The date format. */
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	private ServerConfiguration serverConfiguration;

	/**
	 * Instantiates a new mail saver.
	 *
	 * @param mailServerModel
	 *            the mail server model
	 */
	public MailSaver(ServerConfiguration serverConfiguration) {

		this.serverConfiguration = serverConfiguration;
		this.storageCharSet = serverConfiguration.getStorageCharset();
		Validate.notNull(serverConfiguration);
		Validate.notNull(storageCharSet);

	}

	/**
	 * Converts an {@code InputStream} into a {@code String} object.
	 * <p>
	 * The method will not copy the first 4 lines of the input stream.<br>
	 * These 4 lines are SubEtha SMTP additional information.
	 * </p>
	 *
	 * @param is
	 *            the InputStream to be converted.
	 * @return the converted string object, containing data from the InputStream
	 *         passed in parameters.
	 */
	private String convertStreamToString(InputStream is) {
		final long lineNbToStartCopy = 4; // Do not copy the first 4 lines
											// (received part)
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, storageCharSet));
		StringBuilder sb = new StringBuilder();

		String line;
		long lineNb = 0;
		try {
			while ((line = reader.readLine()) != null) {
				if (++lineNb > lineNbToStartCopy) {
					sb.append(line).append(LINE_SEPARATOR);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Could not convert the stream.", e);
		}
		return sb.toString();
	}

	/**
	 * Returns a lock object.
	 * <p>
	 * This lock will be used to make the application thread-safe, and avoid
	 * receiving and deleting emails in the same time.
	 * </p>
	 *
	 * @return a lock object <i>(which is actually the current instance of the
	 *         {@code MailSaver} object)</i>.
	 */
	public Object getLock() {
		return this;
	}

	/**
	 * Gets the subject from the email data passed in parameters.
	 *
	 * @param data
	 *            a string representing the email content.
	 * @return the subject of the email, or an empty subject if not found.
	 */
	private String getSubjectFromStr(String data) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(data));

			String line;
			while ((line = reader.readLine()) != null) {
				Matcher matcher = SUBJECT_PATTERN.matcher(line);
				if (matcher.matches()) {
					return matcher.group(1);
				}
			}
		} catch (IOException e) {
			LOGGER.error("Cannot obtain the subject", e);
		}
		return "";
	}

	/**
	 * Checks if is matching relay domains.
	 *
	 * @param to
	 *            the to
	 * @param relayDomains
	 *            the relay domains
	 * @return true, if is matching relay domains
	 */
	private boolean isMatchingRelayDomains(String to, List<String> relayDomains) {
		if (relayDomains != null) {
			LOGGER.debug("Relay domains are defined : ", relayDomains);
			boolean matches = false;
			for (String domain : relayDomains) {
				if (to.endsWith(domain)) {
					LOGGER.debug("The domain is matching : ", domain);
					matches = true;
					break;
				}
			}

			if (!matches) {
				LOGGER.debug("Destination {} doesn't match relay domains", to);
				return false;
			}
		} else {
			LOGGER.debug("No relay domain has been defined, no filtering");
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.sleroy.junit.mail.server.MailSaverInterface#saveEmailAndNotify(java.
	 * lang.String, java.lang.String, java.io.InputStream)
	 */
	@Override
	public void saveEmailAndNotify(String from, String to, InputStream data) {
		List<String> relayDomains = serverConfiguration.getRelayDomains();

		// We move everything that we can move outside the synchronized block to
		// limit the impact
		EmailModel model = new EmailModel();
		model.setFrom(from);
		model.setTo(to);
		String mailContent = convertStreamToString(data);
		model.setSubject(getSubjectFromStr(mailContent));
		model.setEmailStr(mailContent);
		model.setReceivedDate(new Date());

		// Controls the relay domain
		if (!isMatchingRelayDomains(to, relayDomains)) {
			setChanged();
			notifyObservers(new RejectedMailEvent(model));
			return;
		}

		synchronized (getLock()) {

			setChanged();

			notifyObservers(new NewMailEvent(model));
		}
	}
}
