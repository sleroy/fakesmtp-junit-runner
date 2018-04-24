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

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

import com.github.sleroy.fakesmtp.core.ServerConfiguration;
import com.github.sleroy.fakesmtp.core.exception.BindPortException;
import com.github.sleroy.fakesmtp.core.exception.InvalidHostException;
import com.github.sleroy.fakesmtp.core.exception.InvalidPortException;

public class MailServerTest {

	@Test(expected = InvalidPortException.class)
	public void testStart_WrongPort() throws Exception {
		// GIVEN A CONFIGURATION WITH WRONG PORT
		ServerConfiguration serverConfiguration = new ServerConfiguration();
		serverConfiguration.port("24P");
		// WHEN I TRIGGER THE SERVER
		try (MailServer mailServer = new MailServer(serverConfiguration);) {
			mailServer.start();
		} finally {
			//
		}
		// THE SERVER IS MISERABILY CRASHING (NumberFormatException)

	}

	@Test(expected = InvalidHostException.class)
	public void testStart_UnknownHost() throws Exception {
		// GIVEN A CONFIGURATION WITH UNKNOWN HOST
		ServerConfiguration serverConfiguration = new ServerConfiguration().port("2525").bind("DFDFDFDFD@#%&");

		// WHEN I TRIGGER THE SERVER
		try (MailServer mailServer = new MailServer(serverConfiguration);) {
			mailServer.start();
		} finally {
			//
		}
		// THE SERVER IS MISERABILY CRASHING (NumberFormatException)

	}

	@Test(expected = BindPortException.class)
	public void testStart_BindException() throws Exception {
		// GIVEN A PORT AND A Running MailServer
        ServerConfiguration serverConfiguration = new ServerConfiguration().port(2828);
        try (MailServer mailServer = new MailServer(serverConfiguration)){
            mailServer.start();
            // WHEN I TRIGGER THE SERVER
            startMailServer(serverConfiguration);
            // THE SERVER IS MISERABILY CRASHING (PORT EXCEPTION ON 2828)
        } finally {
            //
        }
	}

   	private void startMailServer(ServerConfiguration serverConfiguration) throws Exception {
        try (MailServer mailServer = new MailServer(serverConfiguration);) {
            mailServer.start();
        } finally {
            //
        }
    	}

    /**
	 * Test start running server.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test()
	public void testStart_RunningServer() throws Exception {
		// GIVEN A BASIC CONFIGURATION
		ServerConfiguration serverConfiguration = new ServerConfiguration();
		// AND APPROPRIATE PORT
		serverConfiguration.port("2525");
		// WHEN I TRIGGER THE SERVER

		boolean hasLaunched = false;
		try (MailServer mailServer = new MailServer(serverConfiguration);) {
			mailServer.start();
			// THE SERVER IS RUNNING
			hasLaunched = mailServer.isRunning();

		} finally {
			//
			Assert.assertTrue(hasLaunched);

		}

	}

}
