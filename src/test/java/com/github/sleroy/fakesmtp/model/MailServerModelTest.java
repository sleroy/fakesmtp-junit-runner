/*
 *
 */
package com.github.sleroy.fakesmtp.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MailServerModelTest {

    @Test
    public void testRejectAndDeleteMail() throws Exception {
	// GIVEN a mail server model
	final MailServerModel mailServerModel = new MailServerModel();
	// THEN I save a mail
	final EmailModel email = new EmailModel();
	mailServerModel.rejectedMail(email);
	// AND indeed the mail final has been stored
	assertTrue(mailServerModel.getRejectedMails().contains(email));
	// THEN I remove it
	mailServerModel.deleteAllMails();
	// AND I don't have mails stored
	assertFalse(mailServerModel.getEmailModels().contains(email));
    }

    @Test
    public void testRejectMail() throws Exception {
	// GIVEN a mail server model
	final MailServerModel mailServerModel = new MailServerModel();
	// THEN I reject a mail
	final EmailModel email = new EmailModel();
	mailServerModel.rejectedMail(email);
	// AND indeed the rejected mail has been stored
	assertTrue(mailServerModel.getRejectedMails().contains(email));

    }

    @Test
    public void testSaveAndDeleteMail() throws Exception {
	// GIVEN a mail server model
	final MailServerModel mailServerModel = new MailServerModel();
	// THEN I save a mail
	final EmailModel email = new EmailModel();
	mailServerModel.saveMail(email);
	// AND indeed the mail has been stored
	assertTrue(mailServerModel.getEmailModels().contains(email));
	// THEN I remove it
	mailServerModel.deleteAllMails();
	// AND I don't have mails stored
	assertFalse(mailServerModel.getEmailModels().contains(email));
    }

    @Test
    public void testSaveMail() throws Exception {
	// GIVEN a mail server model
	final MailServerModel mailServerModel = new MailServerModel();
	// THEN I save a mail
	final EmailModel email = new EmailModel();
	mailServerModel.saveMail(email);
	// AND indeed the mail final has been stored
	assertTrue(mailServerModel.getEmailModels().contains(email));

    }

}
