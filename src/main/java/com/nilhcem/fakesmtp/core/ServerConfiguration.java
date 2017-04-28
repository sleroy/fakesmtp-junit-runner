
package com.nilhcem.fakesmtp.core;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Handles command line arguments.
 *
 * @author Nilhcem
 * @since 1.3
 */
public class ServerConfiguration {

	private String port = "25";
	private String bindAddress;
	private String outputDirectory;
	private String emlViewer;
	private boolean backgroundStart;
	private boolean startServerAtLaunch;
	private boolean memoryModeEnabled;
	private String emailsSuffix;
	private String storageCharsetName;
	private String savePath;
	private Authentication authentication = new Authentication();

	/**
	 * Handles command line arguments.
	 */
	ServerConfiguration() {
	}

	/**
	 * @return the bind address, as specified by the user, or a {@code null}
	 *         string if unspecified.
	 */
	public String getBindAddress() {
		return bindAddress;
	}

	/**
	 * Gets the emails suffix.
	 *
	 * @return the emails suffix
	 */
	public String getEmailsSuffix() {

		return emailsSuffix;
	}

	/**
	 * @return the name of executable used for viewing eml files, as specified
	 *         by the user, or a {@code null} string if unspecified.
	 */
	public String getEmlViewer() {
		return emlViewer;
	}

	/**
	 * @return the file name of the program.
	 */
	private String getJarName() {
		return new java.io.File(ServerConfiguration.class.getProtectionDomain().getCodeSource().getLocation().getPath())
				.getName();
	}

	/**
	 * @return the output directory, as specified by the user, or a {@code null}
	 *         string if unspecified.
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @return the port, as specified by the user, or a {@code null} string if
	 *         unspecified.
	 */
	public String getPort() {
		return port;
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
	public String getStorageCharSet() {
		return storageCharsetName;
	}

	/**
	 * @return whether or not the SMTP server should disable the persistence in
	 *         order to avoid the overhead that it adds. This is particularly
	 *         useful when we launch performance tests that massively send
	 *         emails.
	 */
	public boolean memoryModeEnabled() {
		return memoryModeEnabled;
	}

	/**
	 * Sets the background start.
	 *
	 * @param backgroundStart
	 *            the new background start
	 */
	public void setBackgroundStart(boolean backgroundStart) {
		this.backgroundStart = backgroundStart;
	}

	/**
	 * Sets the bind address.
	 *
	 * @param bindAddress
	 *            the new bind address
	 */
	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	/**
	 * Sets the emails suffix.
	 *
	 * @param emailsSuffix
	 *            the new emails suffix
	 */
	public void setEmailsSuffix(String emailsSuffix) {
		this.emailsSuffix = emailsSuffix;
	}

	public void setEmlViewer(String emlViewer) {
		this.emlViewer = emlViewer;
	}

	/**
	 * Sets the memory mode enabled.
	 *
	 * @param memoryModeEnabled
	 *            the new memory mode enabled
	 */
	public void setMemoryModeEnabled(boolean memoryModeEnabled) {
		this.memoryModeEnabled = memoryModeEnabled;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	/**
	 * Sets the port.
	 *
	 * @param port
	 *            the new port
	 */
	public void setPort(String port) {
		this.port = port;
	}

	public void setStartServerAtLaunch(boolean startServerAtLaunch) {
		this.startServerAtLaunch = startServerAtLaunch;
	}

	/**
	 * Sets the storage charset name.
	 *
	 * @param storageCharset
	 *            the new storage charset name
	 */
	public void setStorageCharsetName(String storageCharset) {
		this.storageCharsetName = storageCharset;
	}

	/**
	 * @return whether or not the SMTP server must be running without a GUI,
	 *         only if started at launch (if {@code shouldStartServerAtLaunch()}
	 *         returns true}).
	 * @see #shouldStartServerAtLaunch
	 */
	public boolean shouldStartInBackground() {
		return startServerAtLaunch && backgroundStart;
	}

	/**
	 * @return whether or not the SMTP server must be started automatically at
	 *         launch.
	 */
	public boolean shouldStartServerAtLaunch() {
		return startServerAtLaunch;
	}

	/**
	 * Gets the save path to store the mails;
	 *
	 * @return the save path
	 */
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Authentication getAuthentication() {

		return authentication;
	}

}
