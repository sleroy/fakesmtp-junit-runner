/*
 *
 */
package com.github.sleroy.junit.mail.server.test;

/**
 * The Class FakeSmtpRuleException is an exception thrown when the JUnit Rule
 * has encountered problems.
 */
public class FakeSmtpRuleException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -8829764756943838590L;

    /**
     * Instantiates a new fake smtp rule exception.
     *
     * @param message
     *            the message
     */
    public FakeSmtpRuleException(final String message) {
	super(message);

    }

    /**
     * Instantiates a new fake smtp rule exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     */
    public FakeSmtpRuleException(final String message, final Throwable cause) {
	super(message, cause);

    }

    /**
     * Instantiates a new fake smtp rule exception.
     *
     * @param message
     *            the message
     * @param cause
     *            the cause
     * @param enableSuppression
     *            the enable suppression
     * @param writableStackTrace
     *            the writable stack trace
     */
    public FakeSmtpRuleException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);

    }

    /**
     * Instantiates a new fake smtp rule exception.
     *
     * @param cause
     *            the cause
     */
    public FakeSmtpRuleException(final Throwable cause) {
	super("The SMTP Server encountered a problem", cause);

    }

}
