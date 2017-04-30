package io.sleroy.junit.mail.server;

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;

import com.nilhcem.fakesmtp.model.MailServerModel;

@RunWith(MockitoJUnitRunner.class)
public class SMTPServerHandlerTest {
	@Mock
	private SimpleMessageListener _mailListener;

	@Mock
	private MailSaver _mailSaver;

	@Mock
	private AuthenticationHandlerFactory smtpAuthHandlerFactory;

	@Mock
	private MailServerModel mailServerModel;

	@InjectMocks
	private SMTPServerHandler sMTPServerHandler;

	@Test
	public void testStartServer() throws Exception {
		sMTPServerHandler.startServer(0, InetAddress.getByName("localhost"));
		Assert.assertTrue(sMTPServerHandler.isRunning());
		sMTPServerHandler.stopServer();
		Assert.assertFalse(sMTPServerHandler.isRunning());

	}

}
