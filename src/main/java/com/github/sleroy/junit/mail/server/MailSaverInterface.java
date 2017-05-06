/*
 *
 */
package com.github.sleroy.junit.mail.server;

import java.io.InputStream;

import com.github.sleroy.fakesmtp.model.MailServerModel;

@FunctionalInterface
public interface MailSaverInterface {

    /**
     * Saves incoming email in file system and notifies observers.
     *
     * @param from
     *            the user who send the email.
     * @param to
     *            the recipient of the email.
     * @param data
     *            an InputStream object containing the email.
     * @see MailServerModel to see which observers will be notified
     */
    void saveEmailAndNotify(String from, String to, InputStream data);

}