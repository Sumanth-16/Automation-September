package com.utilities;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.ArrayUtils;

public class EmailUtilities {
	
	/*
	 * Before using this class, Turn on "Allow less secured Apps" option in the Gmail account for which Email need
	 * to be verified (using both below links) - "Allow less secured Apps" option to be enabled in
	 * both the below links
	 * 
	 * Link 1: https://myaccount.google.com/security?pli=1#connectedapps (bottom of the page)
	 * Link 2: https://www.google.com/settings/security/lesssecureapps

	
	 * Sample Usage Code
	 * 
	 * EmailUtilities.connectToMailbox("<email id>", "<password>");
	 * 
	 * EmailUtilities.verifyMailSubject("<Expected Subject>", "<The mail id from which mail is received>");
	 * 
	 * EmailUtilities.verifyEmailBody("<The mail id from which mail is received>", "<Expected Subject>", "<Expected Mail Content>");
	 * Note: <Expected Mail Content> can only be a part of the entire content that is to be verified. Only the content 
	 * part which has more significance and worth of verifying to be passed.
	 * 
	 * EmailUtilities.getLatestEmailBody("<email id>", "<password>")
	 */
	
	private static String receivedToMailAddress = "";
	private static Store store; 
	
	// This method is used to connect to Gmail account using the given mail id and password
	public static void connectToMailbox(String emailID, String password) throws Exception {
		Properties properties = new Properties();
		receivedToMailAddress = emailID;
	
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.imap.port", "993");
		properties.setProperty("mail.imap.socketFactory.fallback", "false");
		properties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		//props.setProperty("map.googlemail.com", "imaps");

		// Put all other Properties here
		Session session = Session.getDefaultInstance(props, null);
		store = session.getStore("imaps");
		store.connect("imap.gmail.com", emailID, password);
		System.out.println("specified Gmail account " + emailID + " connected successfully");
	}

	/* This method is used to verify whether there is a mail existing with the given expectedSubject received from 
	 * the mail id "fromAddress" 
	 */
	public static boolean verifyMailSubject(String expectedSubject, String receivedFromMailAddress) throws Exception {
		boolean subFlag = true, fromFlag = true, toFlag = true;
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message[] msgs = inbox.getMessages();
		ArrayUtils.reverse(msgs);
		int i = msgs.length;

		System.out.println("Total Mails in Inbox: " + i);
		System.out.println("Checking mail with subject : \"" + expectedSubject + "\"");

		for (Message msg : msgs) {
			System.out.print(".");
			Address[] to = msg.getAllRecipients();
			Address[] in = msg.getFrom();
			if (receivedToMailAddress != null) {
				toFlag = false;
				for (Address address : to) {
					if (address.toString().contains(receivedToMailAddress))
						toFlag = true;
				}
			}
			if (receivedFromMailAddress != null) {
				fromFlag = false;
				for (Address address : in) {
					if (address.toString().contains(receivedFromMailAddress))
						fromFlag = true;
				}
			}
			if (fromFlag && toFlag && expectedSubject != null && msg.getSubject() != null) {
				subFlag = false;
				if (msg.getSubject().equals(expectedSubject)) {
					System.out.println("Mail with Subject "+expectedSubject+" exists in mailbox");
					subFlag = true;
					break;
				}
			}
		}
	
		return subFlag;
	}

	
	/* This method is used to verify whether a mail exists received from the given mail "receivedFromMailAddress"
	 * with the expected Subject "expectedSubject" and with the expected Mail content "expectedEmailContent" (Expected mail content
	 * can be a part of the entire mail content)
	 */
	public static boolean verifyEmailBody(String receivedFromMailAddress, String expectedSubject, String expectedEmailContent) throws Exception {
		boolean subFlag = true, fromFlag = true, toFlag = true;
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message[] msgs = inbox.getMessages();
		ArrayUtils.reverse(msgs);
		int i = msgs.length;

		System.out.println("Total Mails in Inbox: " + i);
		System.out.println("Checking mail with email body : \"" + expectedEmailContent + "\"");

		Message actualMail = null;

		for (Message msg : msgs) {
			System.out.print(".");
			Address[] to = msg.getAllRecipients();
			Address[] in = msg.getFrom();
			if (receivedToMailAddress != null) {
				toFlag = false;
				for (Address address : to) {
					if (address.toString().contains(receivedToMailAddress))
						toFlag = true;
				}
			}
			if (receivedFromMailAddress != null) {
				fromFlag = false;
				for (Address address : in) {
					if (address.toString().contains(receivedFromMailAddress))
						fromFlag = true;
				}
			}
			if (expectedSubject != null && msg.getSubject() != null) {
				subFlag = false;
				if(msg.getSubject().equals(expectedSubject))
					subFlag = true;
			}
			if (toFlag && fromFlag && subFlag) {
				actualMail = msg;

				if (actualMail.getContent() instanceof String) {
					String actualMailContent = (String) actualMail.getContent();

					if (actualMailContent.contains(expectedEmailContent)) {
						System.out.println("Mail with mail body "+expectedEmailContent+" exists in mailbox");

						return true;
					}
				} else if (actualMail.getContent() instanceof Multipart) {
					MimeMultipart mp = (MimeMultipart) actualMail.getContent();
					if (getTextFromMimeMultipart(mp).contains(expectedEmailContent)) {
						System.out.println("Mail with mail body "+expectedEmailContent+" exists in mailbox");
						return true;
					}
				}
			}
		}
		System.out.println("Mail with mail body "+expectedEmailContent+" not exists in mailbox");
		return subFlag;
	}
	
	// Retrieves the latest mail body content from the given Email id and password
	public static String getLatestEmailBody(String emailId, String password) throws Exception 
	{
		connectToMailbox(emailId, password);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message[] msgs = inbox.getMessages();
		ArrayUtils.reverse(msgs);

		Message actualMail = msgs[0];
		if (actualMail.getContent() instanceof String) {
			return (String) actualMail.getContent();

		} else if (actualMail.getContent() instanceof Multipart) {
			MimeMultipart mp = (MimeMultipart) actualMail.getContent();
			return getTextFromMimeMultipart(mp);
			
		}
		return "";
	}

	// This method is used to retrieve the raw mail body content from a mime multi part content type 
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
		String result = "";
		int count = mimeMultipart.getCount();
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; 
			} 
			else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}
}
