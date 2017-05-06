/*
 *
 */
package com.github.sleroy.junit.mail.server.events;

import com.github.sleroy.fakesmtp.model.EmailModel;

/**
 * The Class {@link RejectedMailEvent} defines an event when an email model has
 * been rejected.
 */
public class RejectedMailEvent {

    private final EmailModel model;

    /**
     * Instantiates a new rejected mail event.
     *
     * @param model
     *            the model
     */
    public RejectedMailEvent(final EmailModel model) {
	this.model = model;

    }

    public EmailModel getModel() {
	return model;
    }

}
