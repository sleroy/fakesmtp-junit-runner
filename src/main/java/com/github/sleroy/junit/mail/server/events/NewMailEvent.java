/*
 *
 */
package com.github.sleroy.junit.mail.server.events;

import com.github.sleroy.fakesmtp.model.EmailModel;

/**
 * The Class NewMailEvent defines an event when an email model has been
 * received.
 */
public class NewMailEvent {

    private final EmailModel model;

    /**
     * Instantiates a new new mail event.
     *
     * @param model
     *            the model
     */
    public NewMailEvent(final EmailModel model) {
	this.model = model;

    }

    public EmailModel getModel() {
	return model;
    }

}
