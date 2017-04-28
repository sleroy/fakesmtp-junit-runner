/*
 *
 */
package io.sleroy.junit.mail.server;

import com.nilhcem.fakesmtp.core.ServerConfiguration;
import com.nilhcem.fakesmtp.model.MailServerModel;

/**
 * The Class MailServer initializes the MailServer.
 */
public class MailServer implements AutoCloseable {
	private SMTPAuthHandler smtpAuthHandler;
	private ServerConfiguration serverConfiguration;
	private MailSaver mailSaver;
	private MailListener mailListener;
	private SMTPAuthHandlerFactory smtpAuthHandlerFactory;
	private SMTPServerHandler smtpServerHandler;
	private MailServerModel mailServerModel;

	public MailServer(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;

	}

	public void start() {

		// Model

		smtpAuthHandler = new SMTPAuthHandler(serverConfiguration.getAuthentication().getUserName(),
				serverConfiguration.getAuthentication().getPassword());

		smtpAuthHandlerFactory = new SMTPAuthHandlerFactory(smtpAuthHandler);

		if (serverConfiguration.memoryModeEnabled()) {
			mailSaver = new MemoryMailSaver(mailServerModel, serverConfiguration.getStorageCharset());
		} else {
			mailSaver = new DiskMailSaver(mailServerModel, serverConfiguration.getStorageCharset());
		}

		mailListener = new MailListener(mailSaver);

		mailServerModel = new MailServerModel(serverConfiguration, smtpServerHandler); // Cycle

		smtpServerHandler = new SMTPServerHandler(mailServerModel, mailListener, mailSaver, smtpAuthHandlerFactory);

	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

}
