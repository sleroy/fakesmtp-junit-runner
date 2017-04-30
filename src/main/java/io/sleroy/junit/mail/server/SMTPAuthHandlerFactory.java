package io.sleroy.junit.mail.server;

import java.util.ArrayList;
import java.util.List;
import org.subethamail.smtp.AuthenticationHandler;
import org.subethamail.smtp.AuthenticationHandlerFactory;

// TODO: Auto-generated Javadoc
/**
 * The factory interface for creating authentication handlers.
 *
 * @author jasonpenny
 * @since 1.2
 */
final class SMTPAuthHandlerFactory implements AuthenticationHandlerFactory {

	/** The Constant LOGIN_MECHANISM. */
	public static final String LOGIN_MECHANISM = "LOGIN";

	/** The smtp auth handler. */
	private AuthenticationHandler smtpAuthHandler;

	/**
	 * Instantiates a new SMTP auth handler factory.
	 *
	 * @param smtpAuthHandler
	 *            the smtp auth handler
	 */
	public SMTPAuthHandlerFactory(AuthenticationHandler smtpAuthHandler) {
		super();
		this.smtpAuthHandler = smtpAuthHandler;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.subethamail.smtp.AuthenticationHandlerFactory#create()
	 */
	@Override
	public AuthenticationHandler create() {
		return smtpAuthHandler;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.subethamail.smtp.AuthenticationHandlerFactory#
	 * getAuthenticationMechanisms()
	 */
	@Override
	public List<String> getAuthenticationMechanisms() {
		List<String> result = new ArrayList<String>();
		result.add(SMTPAuthHandlerFactory.LOGIN_MECHANISM);
		return result;
	}
}
