package io.sleroy.junit.mail.server;

import java.io.InputStream;
import java.io.StringReader;

import org.apache.commons.io.input.ReaderInputStream;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MailListenerTest {

	@Test
	public void testAccept() throws Exception {
		MailListener mailListener = new MailListener(Mockito.mock(MailSaver.class));
		// WHEN I provide dumb values to the mail listener
		boolean accept = mailListener.accept("gni", "gna");
		// THEN IT ACCEPTS ANYTHING
		Assert.assertTrue(accept);
	}

	@Test
	public void testDeliver() throws Exception {
		MailSaver mailSaver = Mockito.mock(MailSaver.class);
		MailListener mailListener = new MailListener(mailSaver);
		// WHEN deliver a new mail

		mailListener.deliver("me@mail.org", "recipient",new ReaderInputStream( new StringReader("test") ));
		// THEN it invokes the mail saver
		Mockito.verify(mailSaver, Mockito.times(1)).saveEmailAndNotify(Mockito.eq("me@mail.org"), Mockito.eq("recipient"),Mockito.any(InputStream.class) );
	}

}
