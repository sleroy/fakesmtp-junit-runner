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
package io.sleroy.junit.mail.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.subethamail.smtp.AuthenticationHandler;

import io.sleroy.junit.mail.server.SMTPAuthHandlerFactory;

@RunWith(MockitoJUnitRunner.class)
public class SMTPAuthHandlerFactoryTest {
	@Mock
	private AuthenticationHandler smtpAuthHandler;
	@InjectMocks
	private SMTPAuthHandlerFactory sMTPAuthHandlerFactory;

	@Test
	public void testCreate() throws Exception {
		AuthenticationHandler newAuthHandler = sMTPAuthHandlerFactory.create();
		Assert.assertEquals(smtpAuthHandler, newAuthHandler);

	}

	@Test
	public void testGetAuthenticationMechanisms() throws Exception {
		// WHEN I RECLAIM THE AUTH MECHANISM
		List<String> newAuthHandler = sMTPAuthHandlerFactory.getAuthenticationMechanisms();
		// ONLY LOGIN IS SUPPORTED
		Assert.assertEquals(1, newAuthHandler.size());
		Assert.assertTrue(newAuthHandler.contains("LOGIN"));
	}

}
