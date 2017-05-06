/*
 *
 */
package com.github.sleroy.fakesmtp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sleroy.junit.mail.server.events.DeleteAllMailEvent;
import com.github.sleroy.junit.mail.server.events.NewMailEvent;
import com.github.sleroy.junit.mail.server.events.RejectedMailEvent;

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

    /** The email models. */
    private final List<EmailModel> emailModels = new ArrayList<>(100);

    /** The rejected mails. */
    private final List<EmailModel> rejectedMails = new ArrayList<>(100);

    /**
     * Instantiates a new mail server model.
     */
    public MailServerModel() {
	super();
    }

    /**
     * Delete all mails.
     */
    public void deleteAllMails() {
	LOGGER.info("Received event to delete all the mails");
	emailModels.clear();
	rejectedMails.clear();
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

    /**
     * Rejects a mail.
     *
     * @param mail
     *            the mail
     */
    public void rejectedMail(final EmailModel mail) {
	LOGGER.info("A mail has been rejected : {}", mail);
	rejectedMails.add(mail);

    }

    /**
     * Save the mail.
     *
     * @param email
     *            the email
     */
    public void saveMail(final EmailModel email) {
	LOGGER.info("Has received mail : {}", email);
	emailModels.add(email);
    }

    @Override
    public String toString() {
	return "MailServerModel [emailModels=" + emailModels + ", rejectedMails=" + rejectedMails + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(final Observable o, final Object arg) {
	if (arg instanceof NewMailEvent) {
	    saveMail(((NewMailEvent) arg).getModel());
	} else if (arg instanceof DeleteAllMailEvent) {
	    deleteAllMails();
	} else if (arg instanceof RejectedMailEvent) {
	    rejectedMail(((RejectedMailEvent) arg).getModel());
	}

    }

}
