package io.sleroy.junit.mail.server;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.subethamail.smtp.AuthenticationHandler;

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
