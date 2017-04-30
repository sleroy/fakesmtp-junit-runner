package io.sleroy.junit.mail.server.events;

import com.nilhcem.fakesmtp.model.EmailModel;

/**
 * The Class NewMailEvent defines an event when an email model has been
 * received.
 */
public class NewMailEvent {

	private EmailModel model;

	/**
	 * Instantiates a new new mail event.
	 *
	 * @param model
	 *            the model
	 */
	public NewMailEvent(EmailModel model) {
		this.model = model;

	}

	public EmailModel getModel() {
		return model;
	}

}
