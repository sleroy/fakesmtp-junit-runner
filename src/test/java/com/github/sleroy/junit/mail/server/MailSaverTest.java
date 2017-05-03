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
package com.github.sleroy.junit.mail.server;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.NullInputStream;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.fakesmtp.model.EmailModel;
import com.github.sleroy.fakesmtp.model.MailServerModel;
import com.github.sleroy.junit.mail.server.MailSaver;

@RunWith(MockitoJUnitRunner.class)
public class MailSaverTest {

	private static final String TO = "to@data.org";

	private static final String FROM = "test@data.org";

	@SuppressWarnings("deprecation")

	// TEST WITH CORRECT CONFIG AND INVALID INPUT
	@Test
	public void testSaveEmailAndNotify_correct_config_invalid_input() throws Exception {
		// GIVEN I have a server accurately configured (relay domains)

		ServerConfiguration serverConfiguration = new ServerConfiguration();

		MailSaver mailSaver = new MailSaver(serverConfiguration);

		serverConfiguration.relayDomains("data.org");

		MailServerModel mailServerModel = new MailServerModel();
		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, new NullInputStream(10));

		// THEN
		Assert.assertEquals(1, mailServerModel.getEmailModels().size());
		EmailModel emailModel = mailServerModel.getEmailModels().get(0);
		assertNotNull(emailModel);
		assertEquals("", emailModel.getSubject());

		//
	}

	// TEST WITH CORRECT CONFIG
	@Test
	public void testSaveEmailAndNotify_correct_config() throws Exception {
		// GIVEN I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		ServerConfiguration serverConfiguration = new ServerConfiguration().relayDomains("data.org");

		MailSaver mailSaver = new MailSaver(serverConfiguration);

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, IOUtils.toInputStream("test", "UTF-8"));

		// THEN
		Assert.assertEquals(1, mailServerModel.getEmailModels().size());
		EmailModel emailModel = mailServerModel.getEmailModels().get(0);
		assertNotNull(emailModel);

		//
	}

	// TEST WITH MISSING RELAY DOMAINS
	@Test
	public void testSaveEmailAndNotify_missing_relay() throws Exception {
		// GIVEN I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		ServerConfiguration serverConfiguration = new ServerConfiguration();

		MailSaver mailSaver = new MailSaver(serverConfiguration);

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, IOUtils.toInputStream("test", "UTF-8"));

		// THEN THE MAILS ARE REJECTED
		Assert.assertEquals(0, mailServerModel.getEmailModels().size());

		//
	}

	// TEST WITH NO RELAY DOMAINS (NO FILTER)
	@Test
	public void testSaveEmailAndNotify_no_relay() throws Exception {
		// GIVEN I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		ServerConfiguration serverConfiguration = new ServerConfiguration();

		serverConfiguration.relayDomainsList((List) null);
		MailSaver mailSaver = new MailSaver(serverConfiguration);

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, IOUtils.toInputStream("test", "UTF-8"));

		// THEN THE MAILS ARE REJECTED
		Assert.assertEquals(1, mailServerModel.getEmailModels().size());
		EmailModel emailModel = mailServerModel.getEmailModels().get(0);
		assertNotNull(emailModel);

		//
	}

}
