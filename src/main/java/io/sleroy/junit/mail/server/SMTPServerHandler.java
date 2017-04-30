package io.sleroy.junit.mail.server;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import com.nilhcem.fakesmtp.core.exception.BindPortException;
import com.nilhcem.fakesmtp.core.exception.OutOfRangePortException;
import com.nilhcem.fakesmtp.model.MailServerModel;

/**
 * Starts and stops the SMTP server.
 *
 * @author Nilhcem
 * @since 1.0
 */
public class SMTPServerHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SMTPServerHandler.class);
	private final MailSaver mailSaver;
	private final SimpleMessageListener mailListener;
	private final SMTPServer smtpServer;
	private AuthenticationHandlerFactory smtpAuthHandlerFactory;

	/**
	 * Instantiates a new SMTP server handler.
	 *
	 * @param mailServerModel
	 *            the mail server model
	 * @param _mailListener
	 *            the mail listener
	 * @param _mailSaver
	 *            the mail saver
	 * @param _smtpAuthHandlerFactory
	 *            the smtp auth handler factory
	 */
	public SMTPServerHandler(MailServerModel mailServerModel, SimpleMessageListener _mailListener, MailSaver _mailSaver,
			AuthenticationHandlerFactory _smtpAuthHandlerFactory) {

		mailListener = _mailListener;
		mailSaver = _mailSaver;
		smtpAuthHandlerFactory = _smtpAuthHandlerFactory;
		smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(mailListener), smtpAuthHandlerFactory);
	}

	/**
	 * Starts the server on the port and address specified in parameters.
	 *
	 * @param port
	 *            the SMTP port to be opened.
	 * @param bindAddress
	 *            the address to bind to. null means bind to all.
	 * @throws BindPortException
	 *             when the port can't be opened.
	 * @throws OutOfRangePortException
	 *             when port is out of range.
	 * @throws IllegalArgumentException
	 *             when port is out of range.
	 */
	public void startServer(int port, InetAddress bindAddress) throws BindPortException, OutOfRangePortException {
		LOGGER.debug("Starting server on port {}", port);
		try {
			smtpServer.setBindAddress(bindAddress);
			smtpServer.setPort(port);
			smtpServer.start();
		} catch (RuntimeException exception) {
			if (exception.getMessage().contains("BindException")) { // Can't
																	// open port
				LOGGER.error("{}. Port {}", exception.getMessage(), port);
				throw new BindPortException(exception, port);
			} else if (exception.getMessage().contains("out of range")) { // Port
																			// out
																			// of
																			// range
				LOGGER.error("Port {} out of range.", port);
				throw new OutOfRangePortException(exception, port);
			} else { // Unknown error
				LOGGER.error("", exception);
				throw exception;
			}
		}
	}

	/**
	 * Stops the server.
	 * <p>
	 * If the server is not started, does nothing special.
	 * </p>
	 */
	public void stopServer() {
		if (smtpServer.isRunning()) {
			LOGGER.debug("Stopping server");
			smtpServer.stop();
		}
	}

	/**
	 * Checks whether the server is running
	 *
	 * @return true, whether the server is running
	 */
	public boolean isRunning() {
		return smtpServer.isRunning();
	}
}