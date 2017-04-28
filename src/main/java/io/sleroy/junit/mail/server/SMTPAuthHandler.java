package io.sleroy.junit.mail.server;

import org.subethamail.smtp.AuthenticationHandler;

/**
 * Simulates an authentication handler to allow capturing emails that are set up
 * with login authentication.
 *
 * @author jasonpenny
 * @since 1.2
 */
final class SMTPAuthHandler implements AuthenticationHandler {
	private static final String USER_IDENTITY = "User";

	private int pass = 0;

	private String userName;

	private String password;

	// private static final String PROMPT_USERNAME = "334 VXNlcm5hbWU6"; //
	// VXNlcm5hbWU6 is base64 for "Username:"
	// private static final String PROMPT_PASSWORD = "334 UGFzc3dvcmQ6"; //
	// UGFzc3dvcmQ6 is base64 for "Password:"

	public SMTPAuthHandler(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	/**
	 * Simulates an authentication process.
	 * <p>
	 * <ul>
	 * <li>first prompts for username;</li>
	 * <li>then, prompts for password;</li>
	 * <li>finally, returns {@code null} to finish the authentication
	 * process;</li>
	 * </ul>
	 * </p>
	 *
	 * @return <code>null</code> if the authentication process is finished,
	 *         otherwise a string to hand back to the client.
	 * @param clientInput
	 *            The client's input, eg "AUTH PLAIN dGVzdAB0ZXN0ADEyMzQ="
	 */
	@Override
	public String auth(String clientInput) {
		String prompt;

		if (++pass == 1) {
			prompt = userName;
		} else if (pass == 2) {
			prompt = password;
		} else {
			pass = 0;
			prompt = null;
		}
		return prompt;
	}

	/**
	 * If the authentication process was successful, this returns the identity
	 * of the user. The type defining the identity can vary depending on the
	 * authentication mechanism used, but typically this returns a String
	 * username. If authentication was not successful, the return value is
	 * undefined.
	 */
	@Override
	public Object getIdentity() {
		return SMTPAuthHandler.USER_IDENTITY;
	}
}
