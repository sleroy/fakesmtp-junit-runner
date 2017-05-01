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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sleroy.junit.mail.server.events.DeleteAllMailEvent;
import io.sleroy.junit.mail.server.events.NewMailEvent;
import io.sleroy.junit.mail.server.events.RejectedMailEvent;

// TODO: Auto-generated Javadoc
/**
 * UI presentation model of the application.
 * <p>
 * The essence of a Presentation Model is of a fully self-contained class that
 * represents all the data and behavior of the UI window, but without any of the
 * controls used to render that UI on the screen.
 * </p>
 *
 * @author Nilhcem
 * @see <a href="link">http://martinfowler.com/eaaDev/PresentationModel.html</a>
 * @since 1.0
 */
public class MailServerModel implements Observer {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MailServerModel.class);

	/** The nb message received. */
	private int nbMessageReceived = 0;

	/** The email models. */
	private List<EmailModel> emailModels = new ArrayList<>(100);

	/** The rejected mails. */
	private List<EmailModel> rejectedMails = new ArrayList<>(100);

	/**
	 * Instantiates a new mail server model.
	 */
	public MailServerModel() {
		super();
	}

	/**
	 * Gets the nb message received.
	 *
	 * @return the nb message received
	 */
	public int getNbMessageReceived() {
		return nbMessageReceived;
	}

	/**
	 * Sets the nb message received.
	 *
	 * @param nbMessageReceived
	 *            the new nb message received
	 */
	public void setNbMessageReceived(int nbMessageReceived) {
		this.nbMessageReceived = nbMessageReceived;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof NewMailEvent) {
			saveMail(((NewMailEvent) arg).getModel());
		} else if (arg instanceof DeleteAllMailEvent) {
			deletAllMails();
		} else if (arg instanceof RejectedMailEvent) {
			rejectedMail(((RejectedMailEvent) arg).getModel());
		}

	}

	private void rejectedMail(EmailModel model) {
		LOGGER.info("A mail has been rejected : {}", model);
		this.rejectedMails.add(model);

	}

	/**
	 * Delet all mails.
	 */
	protected void deletAllMails() {
		LOGGER.info("Received event to delete all the mails");
		this.emailModels.clear();
		this.rejectedMails.clear();
	}

	/**
	 * Save mail.
	 *
	 * @param arg
	 *            the arg
	 */
	protected void saveMail(EmailModel arg) {
		LOGGER.info("Has received mail : {}", arg);
		this.emailModels.add(arg);
	}

	@Override
	public String toString() {
		return "MailServerModel [nbMessageReceived=" + nbMessageReceived + ", emailModels=" + emailModels
				+ ", rejectedMails=" + rejectedMails + "]";
	}

	/**
	 * Gets the email models.
	 *
	 * @return the email models
	 */
	public List<EmailModel> getEmailModels() {
		return emailModels;
	}

	/**
	 * Gets the rejected mails.
	 *
	 * @return the rejected mails
	 */
	public List<EmailModel> getRejectedMails() {

		return rejectedMails;
	}

}
