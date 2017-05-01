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

	@Override
	public String toString() {
		return "EmailModel [receivedDate=" + receivedDate + ", from=" + from + ", to=" + to + ", subject=" + subject
				+ ", emailStr=" + emailStr + ", filePath=" + filePath + "]";
	}

	private Date receivedDate;
	private String from;
	private String to;
	private String subject;
	private String emailStr;
	private String filePath;

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailStr() {
		return emailStr;
	}

	public void setEmailStr(String emailStr) {
		this.emailStr = emailStr;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;

	}

	/**
	 * Saves the mail into an output stream like a file.
	 * 
	 * The stream is not closed after the execution of the method. You need to
	 * close it yourself.
	 *
	 * @param outputStream the output stream
	 * @param charset the charset
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void save(OutputStream outputStream, Charset charset) throws IOException {
		// Copy String to file

		IOUtils.write(this.emailStr, outputStream, charset);
	}
}
