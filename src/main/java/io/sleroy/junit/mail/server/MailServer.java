/*
 *
 */
package io.sleroy.junit.mail.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.nilhcem.fakesmtp.core.ServerConfiguration;
import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.InvalidHostException;
import com.nilhcem.fakesmtp.core.exception.InvalidPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
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
	private boolean started;

	public MailServer(ServerConfiguration serverConfiguration) {
		super();
		this.serverConfiguration = serverConfiguration;

	}

	public void start() {

		// Model

		smtpAuthHandler = new SMTPAuthHandler(serverConfiguration.getAuthentication().getUserName(),
				serverConfiguration.getAuthentication().getPassword());

		smtpAuthHandlerFactory = new SMTPAuthHandlerFactory(smtpAuthHandler);

		mailSaver = new MailSaver(mailServerModel, serverConfiguration.getStorageCharset());
		if (serverConfiguration.memoryModeEnabled()) {

		} else {
//			mailSaver = new DiskMailSaver(mailServerModel, serverConfiguration);
		}

		mailListener = new MailListener(mailSaver);

		mailServerModel = new MailServerModel(); // Cycle

		smtpServerHandler = new SMTPServerHandler(mailServerModel, mailListener, mailSaver, smtpAuthHandlerFactory);

	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * Happens when a user clicks on the start button.
	 * <p>
	 * This method will notify the {@code SMTPServerHandler} to start the
	 * server.
	 * </p>
	 *
	 * @throws InvalidPortException
	 *             when the port is invalid.
	 * @throws BindPortException
	 *             when the port cannot be bound.
	 * @throws OutOfRangePortException
	 *             when the port is out of range.
	 * @throws InvalidHostException
	 *             when the address cannot be resolved.
	 * @throws RuntimeException
	 *             when an unknown exception happened.
	 */
	public void toggleButton()
			throws BindPortException, OutOfRangePortException, InvalidPortException, InvalidHostException {
		if (started) {
			// Do nothing. We can't stop the server. User has to quit the app
			// (issue with SubethaSMTP)
		} else {
			String hostStr = serverConfiguration.getBindAddress();
			try {
				int port = Integer.parseInt(serverConfiguration.getPort());
				InetAddress host = null;
				if (hostStr != null && !hostStr.isEmpty()) {
					host = InetAddress.getByName(hostStr);
				}

				smtpServerHandler.startServer(port, host);
			} catch (NumberFormatException e) {
				throw new InvalidPortException(e);
			} catch (UnknownHostException e) {
				throw new InvalidHostException(e, hostStr);
			}
		}
		started = !started;
	}
}
