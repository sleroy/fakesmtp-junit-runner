/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.sleroy.junit.mail.server.test;

import java.util.List;

import org.junit.rules.ExternalResource;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.fakesmtp.model.EmailModel;
import com.github.sleroy.junit.mail.server.MailServer;

/**
 * The Class FakeSmtpRule defines a JUnit Rule to start/stop a SMTP Server. It
 * can be useful to write integration tests with JavaMail libraries. The
 * configuration of the server has to provided in argument of the constructor.
 */
public class FakeSmtpRule extends ExternalResource {

    /** The server configuration. */
    private final ServerConfiguration serverConfiguration;

    /** The mail server. */
    private MailServer mailServer;

    /**
     * Instantiates a new fake smtp rule.
     *
     * @param serverConfiguration
     *            the server configuration
     */
    public FakeSmtpRule(final ServerConfiguration serverConfiguration) {
	super();
	this.serverConfiguration = serverConfiguration;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.junit.rules.ExternalResource#after()
     */
    @Override
    protected void after() {
	super.after();
	try {
	    if (mailServer != null) {
		mailServer.close();
	    }
	    mailServer = null;
	} catch (final Exception e) {
	    throw new FakeSmtpRuleException(e);
	}
    }

    /*
     * (non-Javadoc)
     *
     * @see org.junit.rules.ExternalResource#before()
     */
    @Override
    protected void before() throws Throwable {
	mailServer = new MailServer(serverConfiguration);
	mailServer.start();
    }

    /**
     * Gets the server configuration.
     *
     * @return the server configuration
     */
    public ServerConfiguration getServerConfiguration() {
	return serverConfiguration;
    }

    /**
     * Checks if the server is running.
     *
     * @return true, if is running
     */
    public boolean isRunning() {

	return mailServer != null && mailServer.isRunning();
    }

    /**
     * Returns the list of mails from the server.
     *
     * @return the list of mails from the server
     */
    public List<EmailModel> mailBox() {

	return mailServer.getMails();
    }

    /**
     * Returns the list of rejected mails.
     *
     * @return the list of rejected mails
     */
    public List<EmailModel> rejectedMails() {
	return mailServer.getRejectedMails();
    }

    /**
     * Returns the list of domains to relay.
     *
     * @return the list of domains to relay.
     */
    public List<String> relayDomains() {

	return mailServer.getRelayDomains();
    }
    /**
     * Forces Shutdown. May be used in cases where you want to test for robustness
     */
    public void forceShutdown() {
    if (mailServer == null) {
        throw new FakeSmtpRuleException("already closed");
    }
    try {
        mailServer.close();
        mailServer = null;
    }catch (Exception e) {
        throw new FakeSmtpRuleException(e);
    }
    }
}
