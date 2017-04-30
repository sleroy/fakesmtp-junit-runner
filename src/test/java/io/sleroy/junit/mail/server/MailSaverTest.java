package io.sleroy.junit.mail.server;

import static org.junit.Assert.assertNotNull;

import java.io.StringBufferInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.nilhcem.fakesmtp.model.EmailModel;
import com.nilhcem.fakesmtp.model.MailServerModel;

@RunWith(MockitoJUnitRunner.class)
public class MailSaverTest {

	private static final String TO = "to@data.org";

	private static final String FROM = "test@data.org";

	private Charset storageCharSet = Charset.defaultCharset();


	@SuppressWarnings("deprecation")

	// TEST WITH CORRECT CONFIG
	@Test
	public void testSaveEmailAndNotify_correct_config() throws Exception {
		//GIVEN  I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		MailSaver mailSaver = new MailSaver(mailServerModel, storageCharSet);

		List<String> relayDomains = new ArrayList<>();
		relayDomains.add("data.org");
		mailServerModel.setRelayDomains(relayDomains);

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, new StringBufferInputStream("gni"));

		// THEN
		Assert.assertEquals(1, mailServerModel.getEmailModels().size());
		EmailModel emailModel = mailServerModel.getEmailModels().get(0);
		assertNotNull(emailModel);

		//
	}

	// TEST WITH MISSING RELAY DOMAINS
	@Test
	public void testSaveEmailAndNotify_missing_relay() throws Exception {
		//GIVEN  I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		MailSaver mailSaver = new MailSaver(mailServerModel, storageCharSet);

		mailServerModel.setRelayDomains(new ArrayList<>());

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, new StringBufferInputStream("gni"));

		// THEN THE MAILS ARE REJECTED
		Assert.assertEquals(0, mailServerModel.getEmailModels().size());

		//
	}

	// TEST WITH NO RELAY DOMAINS (NO FILTER)
	@Test
	public void testSaveEmailAndNotify_no_relay() throws Exception {
		//GIVEN  I have a server accurately configured (relay domains)
		MailServerModel mailServerModel = new MailServerModel();
		MailSaver mailSaver = new MailSaver(mailServerModel, storageCharSet);

		mailServerModel.setRelayDomains(null);

		// AND I don't forget to register the mailServerModel
		mailSaver.addObserver(mailServerModel);

		// WHEN I sent a mail
		mailSaver.saveEmailAndNotify(FROM, TO, new StringBufferInputStream("gni"));

		// THEN THE MAILS ARE REJECTED
		Assert.assertEquals(1, mailServerModel.getEmailModels().size());
		EmailModel emailModel = mailServerModel.getEmailModels().get(0);
		assertNotNull(emailModel);

		//
	}


	@Test
	public void testDeleteEmails() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetLock() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testSaveEmailToFile() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
