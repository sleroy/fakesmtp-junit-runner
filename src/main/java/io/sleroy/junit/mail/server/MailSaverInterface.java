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

import com.nilhcem.fakesmtp.model.MailServerModel;

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
	 * @see MailServerModel to see which
	 *      observers will be notified
	 */
	void saveEmailAndNotify(String from, String to, InputStream data);

}