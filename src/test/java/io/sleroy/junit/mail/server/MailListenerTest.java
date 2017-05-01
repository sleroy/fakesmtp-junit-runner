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

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MailListenerTest {

	@Test
	public void testAccept() throws Exception {
		MailListener mailListener = new MailListener(Mockito.mock(MailSaverInterface.class));
		// WHEN I provide dumb values to the mail listener
		boolean accept = mailListener.accept("gni", "gna");
		// THEN IT ACCEPTS ANYTHING
		Assert.assertTrue(accept);
	}

	@Test
	public void testDeliver() throws Exception {
		MailSaverInterface mailSaver = Mockito.mock(MailSaverInterface.class);
		MailListener mailListener = new MailListener(mailSaver);
		// WHEN deliver a new mail

		mailListener.deliver("me@mail.org", "recipient", IOUtils.toInputStream("test", "UTF-8"));
		// THEN it invokes the mail saver
		Mockito.verify(mailSaver, Mockito.times(1)).saveEmailAndNotify(Mockito.eq("me@mail.org"),
				Mockito.eq("recipient"), Mockito.any(InputStream.class));
	}

}
