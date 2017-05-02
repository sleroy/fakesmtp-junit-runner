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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.sleroy.junit.mail.server.SMTPAuthHandler;

@RunWith(MockitoJUnitRunner.class)
public class SMTPAuthHandlerTest {
	private String password = "password";
	private String userName = "username";

	private SMTPAuthHandler SMTPAuthHandler = new SMTPAuthHandler(userName, password);

	@Test
	public void testAuth() throws Exception {
		Assert.assertEquals(userName, SMTPAuthHandler.auth(""));
		Assert.assertEquals(password, SMTPAuthHandler.auth(""));
		Assert.assertNull(SMTPAuthHandler.auth(""));


	}

	@Test
	public void testGetIdentity() throws Exception {
		Object identity = SMTPAuthHandler.getIdentity();

		Assert.assertEquals(SMTPAuthHandler.USER_IDENTITY, identity);
	}

}
