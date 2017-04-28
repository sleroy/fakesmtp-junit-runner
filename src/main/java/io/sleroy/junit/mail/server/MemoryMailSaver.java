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

/**
 * Saves emails and notifies components so they can refresh their views with new
 * data.
 *
 * @author Nilhcem
 * @since 1.0
 */
public final class MemoryMailSaver extends MailSaver {

	/**
	 * Instantiates a new memory mail saver.
	 *
	 * @param mailServerModel the mail server model
	 * @param storageCharSet the storage char set
	 */
	public MemoryMailSaver(MailServerModel mailServerModel, Charset storageCharSet) {
		super(mailServerModel, storageCharSet);

	}

	/**
	 * Deletes all received emails from file system.
	 */
	public void deleteEmails() {
		return;
	}

	/**
	 * Saves the content of the email passed in parameters in a file.
	 *
	 * @param mailContent
	 *            the content of the email to be saved.
	 * @return the path of the created file.
	 */
	protected String saveEmailToFile(String mailContent) {
		return null;
	}

}
