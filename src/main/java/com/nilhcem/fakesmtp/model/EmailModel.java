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
package com.nilhcem.fakesmtp.model;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * A model representing a received email.
 * <p>
 * This object will be created and sent to observers by the {@code MailSaver}
 * object.<br>
 * It contains useful data such as the content of the email and its path in the
 * file system.
 * </p>
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class EmailModel {

	/** The received date. */
	private Date receivedDate;

	/** The from. */
	private String from;

	/** The to. */
	private String to;

	/** The subject. */
	private String subject;

	/** The email str. */
	private String emailStr;

	/** The file path. */
	private String filePath;

	/**
	 * Instantiates a new email model.
	 */
	public EmailModel() {
		super();
	}

	/**
	 * Gets the email str.
	 *
	 * @return the email str
	 */
	public String getEmailStr() {
		return emailStr;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Gets the received date.
	 *
	 * @return the received date
	 */
	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Saves the mail into an output stream like a file.
	 * 
	 * The stream is not closed after the execution of the method. You need to
	 * close it yourself.
	 *
	 * @param outputStream
	 *            the output stream
	 * @param charset
	 *            the charset
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void save(OutputStream outputStream, Charset charset) throws IOException {
		// Copy String to file

		IOUtils.write(this.emailStr, outputStream, charset);
	}

	/**
	 * Sets the email str.
	 *
	 * @param emailStr
	 *            the new email str
	 */
	public void setEmailStr(String emailStr) {
		this.emailStr = emailStr;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath
	 *            the new file path
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;

	}

	/**
	 * Sets the from.
	 *
	 * @param from
	 *            the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Sets the received date.
	 *
	 * @param receivedDate
	 *            the new received date
	 */
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject
	 *            the new subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Sets the to.
	 *
	 * @param to
	 *            the new to
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmailModel [receivedDate=" + receivedDate + ", from=" + from + ", to=" + to + ", subject=" + subject
				+ ", emailStr=" + emailStr + ", filePath=" + filePath + "]";
	}
}
