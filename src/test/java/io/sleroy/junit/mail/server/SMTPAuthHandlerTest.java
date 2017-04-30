package io.sleroy.junit.mail.server;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

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
