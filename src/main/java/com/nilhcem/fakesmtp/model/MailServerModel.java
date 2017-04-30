package com.nilhcem.fakesmtp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.sleroy.junit.mail.server.events.DeleteAllMailEvent;
import io.sleroy.junit.mail.server.events.NewMailEvent;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(MailServerModel.class);

	/** The nb message received. */
	private int nbMessageReceived = 0;

	/** The list mails map. */
	private final Map<Integer, String> listMailsMap = new HashMap<>();

	/** The relay domains. */
	private List<String> relayDomains = Collections.emptyList();

	private List<EmailModel> emailModels = new ArrayList<>(100);

	/**
	 * Instantiates a new mail server model.
	 */
	public MailServerModel() {
		super();
	}

	/**
	 * Gets the list mails map.
	 *
	 * @return the list mails map
	 */
	public Map<Integer, String> getListMailsMap() {
		return listMailsMap;
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
	 * Gets the relay domains.
	 *
	 * @return the relay domains
	 */
	public List<String> getRelayDomains() {
		return relayDomains;
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

	/**
	 * Sets the relay domains.
	 *
	 * @param relayDomains
	 *            the new relay domains
	 */
	public void setRelayDomains(List<String> relayDomains) {
		this.relayDomains = relayDomains;
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof NewMailEvent) {
			saveMail(((NewMailEvent) arg).getModel());
		} else if (arg instanceof DeleteAllMailEvent) {
			deletAllMails();
		}

	}

	protected void deletAllMails() {
		LOGGER.info("Received event to delete all the mails");
		this.emailModels.clear();
	}

	protected void saveMail(EmailModel arg) {
		LOGGER.info("Has received mail : {}", arg);
		this.emailModels.add(arg);
	}

	/**
	 * Gets the email models.
	 *
	 * @return the email models
	 */
	@Override
	public String toString() {
		return "MailServerModel [nbMessageReceived=" + nbMessageReceived + ", listMailsMap=" + listMailsMap
				+ ", relayDomains=" + relayDomains + ", emailModels=" + emailModels + "]";
	}

	/**
	 * Gets the email models.
	 *
	 * @return the email models
	 */
	public List<EmailModel> getEmailModels() {
		return emailModels;
	}

}
