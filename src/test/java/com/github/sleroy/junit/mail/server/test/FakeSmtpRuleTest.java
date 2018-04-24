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

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.junit.mail.server.test.FakeSmtpRule;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

public class FakeSmtpRuleTest {

	@Rule
	public FakeSmtpRule smtpServer = new FakeSmtpRule(ServerConfiguration.create().port(2525).charset("UTF-8"));

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Tests that the server is launching
	 */
	@Test
	public void testFakeSmtpRule() {
		Assert.assertTrue(smtpServer.isRunning());
	}

	@Test
	public void forceShutdown() {
		smtpServer.forceShutdown();
		Assert.assertFalse(smtpServer.isRunning());
	}
	@Test
	public void forceShutdown_twice_throwsFakeSmtpRuleException() {
		smtpServer.forceShutdown();
		Assert.assertFalse(smtpServer.isRunning());
		thrown.expect(FakeSmtpRuleException.class);
		smtpServer.forceShutdown();
	}
}
