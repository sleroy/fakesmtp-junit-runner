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

package com.github.sleroy.fakesmtp.core;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Handles command line arguments.
 *
 * @author Nilhcem
 * @since 1.3
 */
public class ServerConfiguration {

    /**
     * Creates the server configuration.
     *
     * @return the server configuration
     */
    public static ServerConfiguration create() {
	return new ServerConfiguration();
    }

    /** The port. */
    private String port = "25";

    /** The bind address. */
    private String bindAddress;

    /** The storage charset name. */
    private String storageCharsetName = Charset.defaultCharset().name();

    /** The authentication. */
    private Authentication authentication = new Authentication();

    private List<String> relayDomains = Collections.emptyList();

    /**
     * Handles command line arguments.
     */
    public ServerConfiguration() {
	super();
    }

    /**
     * Defines the authentication.
     *
     * @param authentication
     *            the authentication
     * @return the server configuration
     */
    public ServerConfiguration auth(final Authentication authentication) {
	this.authentication = authentication;
	return this;
    }

    /**
     * Sets the bind address.
     *
     * @param bindAddress
     *            the new bind address
     * @return the server configuration
     */
    public ServerConfiguration bind(final String bindAddress) {
	this.bindAddress = bindAddress;
	return this;
    }

    /**
     * Sets the storage charset name.
     *
     * @param storageCharset
     *            the new storage charset name
     * @return the server configuration
     */
    public ServerConfiguration charset(final String storageCharset) {
	storageCharsetName = storageCharset;
	return this;
    }

    /**
     * Gets the authentication.
     *
     * @return the authentication
     */
    public Authentication getAuthentication() {

	return authentication;
    }

    /**
     * Gets the bind address.
     *
     * @return the bind address, as specified by the user, or a {@code null}
     *         string if unspecified.
     */
    public String getBindAddress() {
	return bindAddress;
    }

    /**
     * Gets the port.
     *
     * @return the port, as specified by the user, or a {@code null} string if
     *         unspecified.
     */
    public String getPort() {
	return port;
    }

    /**
     * Gets the relay domains.
     *
     * @return the relay domains
     */
    public List<String> getRelayDomains() {

	return relayDomains;
    }

    /**
     * Gets the storage charset to store the mails.
     *
     * @return the storage charset
     */
    public Charset getStorageCharset() {

	return Charset.forName(storageCharsetName);
    }

    /**
     * Gets the charset used to store the mails.
     *
     * @return the storage charset
     */
    public String getStorageCharSetName() {
	return storageCharsetName;
    }

    /**
     * Defines the user name for the authentication.
     *
     * @param password
     *            the password
     * @return the server configuration
     */
    public ServerConfiguration password(final String password) {
	authentication.setPassword(password);
	return this;
    }

    /**
     * Defines the server's port.
     *
     * @param port
     *            the port
     * @return the server configuration
     */
    public ServerConfiguration port(final int port) {
	this.port = Integer.toString(port);
	return this;
    }

    /**
     * Sets the port.
     *
     * @param port
     *            the new port
     * @return the server configuration
     */
    public ServerConfiguration port(final String port) {
	this.port = port;
	return this;
    }

    /**
     * Defines the relay domains. for this SMTP Server
     *
     * @param domains
     *            a list of relay domains
     * @return the server configuration
     */
    public ServerConfiguration relayDomains(final String... domains) {
	relayDomains = Arrays.asList(domains);
	return this;

    }

    /**
     * Sets the relay domains.
     *
     * @param relayDomains
     *            the relay domains
     * @return the server configuration
     */
    public ServerConfiguration relayDomainsList(final List<String> relayDomains) {
	this.relayDomains = relayDomains;
	return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "ServerConfiguration [port=" + port + ", bindAddress=" + bindAddress + ", storageCharsetName="
	        + storageCharsetName + ", authentication=" + authentication + "]";
    }

    /**
     * Defines the user name for the authentication.
     *
     * @param userName
     *            the user name
     * @return the server configuration
     */
    public ServerConfiguration userName(final String userName) {
	authentication.setUserName(userName);
	return this;
    }
}
