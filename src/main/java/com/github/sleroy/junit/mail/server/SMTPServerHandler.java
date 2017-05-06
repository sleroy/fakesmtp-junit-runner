/*
 *
 */
package com.github.sleroy.junit.mail.server;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import com.github.sleroy.fakesmtp.core.exception.BindPortException;
import com.github.sleroy.fakesmtp.core.exception.OutOfRangePortException;

/**
 * Starts and stops the SMTP server.
 *
 * @author Nilhcem
 * @since 1.0
 */
public class SMTPServerHandler {

    private static final Logger	LOGGER = LoggerFactory.getLogger(SMTPServerHandler.class);
    private final SMTPServer	smtpServer;

    /**
     * Instantiates a new SMTP server handler.
     *
     * @param mailListener
     *            the mail listener
     * @param smtpAuthHandlerFactory
     *            the smtp auth handler factory
     */
    public SMTPServerHandler(final SimpleMessageListener mailListener,
            final AuthenticationHandlerFactory smtpAuthHandlerFactory) {

	smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(mailListener), smtpAuthHandlerFactory);
    }

    /**
     * Checks whether the server is running
     *
     * @return true, whether the server is running
     */
    public boolean isRunning() {
	return smtpServer.isRunning();
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
    public void startServer(final int port, final InetAddress bindAddress)
            throws BindPortException, OutOfRangePortException {
	LOGGER.debug("Starting server on port {}", port);
	try {
	    smtpServer.setBindAddress(bindAddress);
	    smtpServer.setPort(port);
	    smtpServer.start();
	} catch (final RuntimeException exception) {
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
}
