package io.sleroy.junit.mail.server;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import java.util.Observer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.ServerConfiguration;
import com.nilhcem.fakesmtp.model.MailServerModel;

import io.sleroy.junit.mail.server.events.DeleteAllMailEvent;

/**
 * Saves emails and notifies components so they can refresh their views with new
 * data.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class DiskSaverObserver extends MailServerModel {

	private ServerConfiguration serverConfiguration;

	/**
	 * Instantiates a new disk mail saver.
	 *
	 * @param mailServerModel
	 *            the mail server model
	 * @param storageCharSet
	 *            the storage char set
	 */
	public DiskSaverObserver(MailServerModel mailServerModel, ServerConfiguration serverConfiguration) {
		this.serverConfiguration = serverConfiguration;

	}


	/**
	 * Deletes all received emails from file system.
	 */
	public void deleteEmails() {
		Map<Integer, String> mails = mailServerModel.getListMailsMap();
		for (String value : mails.values()) {
			File file = new File(value);
			if (file.exists()) {
				try {
					if (!file.delete()) {
						LOGGER.error("Impossible to delete file {}", value);
					}
				} catch (SecurityException e) {
					LOGGER.error("", e);
				}
			}
		}
		notifyObservers(new DeleteAllMailEvent());
	}

	/**
	 * Saves the content of the email passed in parameters in a file.
	 *
	 * @param mailContent
	 *            the content of the email to be saved.
	 * @return the path of the created file.
	 */
	protected String saveEmailToFile(String mailContent) {
		String filePath = String.format("%s%s%s", serverConfiguration.getSavePath(), File.separator,
				dateFormat.format(new Date()));

		// Create file
		int i = 0;
		File file = null;
		while (file == null || file.exists()) {
			String iStr;
			if (i++ > 0) {
				iStr = Integer.toString(i);
			} else {
				iStr = "";
			}
			file = new File(filePath + iStr + serverConfiguration.getEmailsSuffix());
		}

		// Copy String to file
		try {
			FileUtils.writeStringToFile(file, mailContent, storageCharSet);
		} catch (IOException e) {
			// If we can't save file, we display the error in the SMTP logs
			Logger smtpLogger = LoggerFactory.getLogger(org.subethamail.smtp.server.Session.class);
			smtpLogger.error("Error: Can't save email: {}", e.getMessage());
		}
		return file.getAbsolutePath();

	}


}
