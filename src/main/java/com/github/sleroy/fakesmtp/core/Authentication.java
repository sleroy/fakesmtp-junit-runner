/*
 *
 */
package com.github.sleroy.fakesmtp.core;

/**
 * The Class Authentication defines the configuration for the SMTPAuthHandler.
 */
public class Authentication {

    /** The user name. */
    private String userName;

    /** The password. */
    private String password;

    /**
     * Instantiates a new authentication.
     */
    public Authentication() {
	super();
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {

	return password;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {

	return userName;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            the new password
     */
    public void setPassword(final String password) {
	this.password = password;
    }

    /**
     * Sets the user name.
     *
     * @param userName
     *            the new user name
     */
    public void setUserName(final String userName) {
	this.userName = userName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Authentication [userName=" + userName + ", password=" + password + "]";
    }

}
