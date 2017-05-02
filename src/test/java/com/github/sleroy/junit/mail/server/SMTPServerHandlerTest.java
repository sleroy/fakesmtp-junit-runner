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

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;

import com.github.sleroy.fakesmtp.core.exception.OutOfRangePortException;
import com.github.sleroy.fakesmtp.model.MailServerModel;
import com.github.sleroy.junit.mail.server.MailSaverInterface;
import com.github.sleroy.junit.mail.server.SMTPServerHandler;

@RunWith(MockitoJUnitRunner.class)
public class SMTPServerHandlerTest {
	@Mock
	private SimpleMessageListener _mailListener;

	@Mock
	private MailSaverInterface _mailSaver;

	@Mock
	private AuthenticationHandlerFactory smtpAuthHandlerFactory;

	@Mock
	private MailServerModel mailServerModel;

	@InjectMocks
	private SMTPServerHandler sMTPServerHandler;

	/**
	 * Test start server.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testStartServer() throws Exception {
		sMTPServerHandler.startServer(0, InetAddress.getByName("localhost"));
		Assert.assertTrue(sMTPServerHandler.isRunning());
		sMTPServerHandler.stopServer();
		Assert.assertFalse(sMTPServerHandler.isRunning());

	}

	@Test(expected = OutOfRangePortException.class)
	public void _PortOutOfRange() throws Exception {
		sMTPServerHandler.startServer(Integer.MIN_VALUE, InetAddress.getByName("localhost"));

	}

}
