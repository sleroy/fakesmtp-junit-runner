/*
 *
 */
package com.github.sleroy.fakesmtp.core.exception;

import java.net.UnknownHostException;

/**
 * Thrown if the host name is invalid while trying to start the server.
 * <p>
 * This is a wrapper for UnknownHostException
 * </p>
 *
 * @author Vest
 * @since 2.1
 */
public class InvalidHostException extends Exception {

    private static final long serialVersionUID = -8263018939961075449L;
    private final String      host;

    public InvalidHostException(final UnknownHostException e, final String host) {
	setStackTrace(e.getStackTrace());
	this.host = host;
    }

    public String getHost() {
	return host;
    }
}
