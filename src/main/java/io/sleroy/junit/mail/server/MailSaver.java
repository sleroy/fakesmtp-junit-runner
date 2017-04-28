package io.sleroy.junit.mail.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nilhcem.fakesmtp.core.ServerConfiguration;
import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.MailServerModel;

// TODO: Auto-generated Javadoc
/**
 * Saves emails and notifies components so they can refresh their views with new
 * data.
 *
 * @author Nilhcem
 * @since 1.0
 */
public abstract class MailSaver extends Observable {

	/** The mail server model. */
	protected MailServerModel mailServerModel;

	protected Charset storageCharSet;

	/**
	 * Instantiates a new mail saver.
	 *
	 * @param mailServerModel the mail server model
	 * @param _storageCharSet the storage char set
	 */
	public MailSaver(MailServerModel mailServerModel, Charset _storageCharSet) {
		this.mailServerModel = mailServerModel;
		this.storageCharSet = _storageCharSet;

	}

	/** The Constant LOGGER. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(MailSaver.class);

	/** The Constant LINE_SEPARATOR. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	/** The Constant SUBJECT_PATTERN. */
	// This can be a static variable since it is Thread Safe
	private static final Pattern SUBJECT_PATTERN = Pattern.compile("^Subject: (.*)$");

	/** The date format. */
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

	/**
	 * Saves incoming email in file system and notifies observers.
	 *
	 * @param from
	 *            the user who send the email.
	 * @param to
	 *            the recipient of the email.
	 * @param data
	 *            an InputStream object containing the email.
	 * @see com.nilhcem.fakesmtp.gui.MainPanel#addObservers to see which
	 *      observers will be notified
	 */
	public void saveEmailAndNotify(String from, String to, InputStream data) {
		List<String> relayDomains = mailServerModel.getRelayDomains();

		if (relayDomains != null) {
			boolean matches = false;
			for (String domain : relayDomains) {
				if (to.endsWith(domain)) {
					matches = true;
					break;
				}
			}

			if (!matches) {
				LOGGER.debug("Destination {} doesn't match relay domains", to);
				return;
			}
		}

		// We move everything that we can move outside the synchronized block to
		// limit the impact
		EmailModel model = new EmailModel();
		model.setFrom(from);
		model.setTo(to);
		String mailContent = convertStreamToString(data);
		model.setSubject(getSubjectFromStr(mailContent));
		model.setEmailStr(mailContent);

		synchronized (getLock()) {
			String filePath = saveEmailToFile(mailContent);

			model.setReceivedDate(new Date());
			model.setFilePath(filePath);

			setChanged();
			notifyObservers(model);
		}
	}

	/**
	 * Deletes all received emails from file system.
	 */
	public abstract void deleteEmails() ;

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
	 * Saves the content of the email passed in parameters in a file.
	 *
	 * @param mailContent
	 *            the content of the email to be saved.
	 * @return the path of the created file.
	 */
	protected abstract String saveEmailToFile(String mailContent);
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
			LOGGER.error("", e);
		}
		return "";
	}
}
