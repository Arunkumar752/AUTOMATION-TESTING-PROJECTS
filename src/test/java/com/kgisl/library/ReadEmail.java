package com.kgisl.library;

import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;

import org.junit.Assert;

import javax.mail.search.FlagTerm;

public class ReadEmail {

	private ReportOut reportout;
	private String result = null;
	private static String saveDirectory = System.getProperty("user.dir")+"/TestData/";
	public ReadEmail(ReportOut reportout) {
		this.reportout = reportout;
	}
	
	public void main(String args) {
		emailVerify("cholams.qa@gmail.com","pwcmzorpmnjgeuep","3404/00005154/000/00");
	}

	public void emailVerify(String username, String password, String subject) {
		//public static void main(String[] args){

		String hostName = "imap.gmail.com";
		int messageCount;
		int unreadMailCount;

		/*
		 * Properties sysProps = System.getProperties();
		 * sysProps.setProperty("mail.store.protocol", "imaps"); try {
		 * 
		 * Session session = Session.getInstance(sysProps, null);
		 * 
		 * Store store = session.getStore();
		 * 
		 * store.connect(hostName, username, password);
		 */

		
		  Properties sysProps = System.getProperties();
		  sysProps.setProperty("mail.store.protocol", "imaps");
		  sysProps.setProperty("mail.imap.port", "993");
		  sysProps.setProperty("mail.imap.connectiontimeout", "5000");
		  sysProps.setProperty("mail.imap.timeout", "5000"); 
		  try {
		  
			  Session session = Session.getInstance(sysProps, null);
				Store store = session.getStore();

				store.connect(hostName, username, password);
		  
		 // Store store = session.getStore();
		 

		/* String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		 Properties pr =  new Properties();
		 pr.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
		 pr.setProperty("mail.pop3.socketFactory.fallback", "false");
		 pr.setProperty("mail.pop3.port",  "995");
		 pr.setProperty("mail.pop3.socketFactory.port", "995");
		 pr.put("mail.pop3.host", "pop.gmail.com");
		 pr.setProperty("mail.store.protocol", "pop3");

		       try {
		         Session session = Session.getDefaultInstance(pr, null);
		         Store store = session.getStore();
		         store.connect(username, password);*/
			//store.connect(hostName, username, password);
		//	store.connect(username, password);

				Folder emailInbox = store.getFolder("INBOX");

			messageCount = emailInbox.getMessageCount();
			System.out.println("Total Message Count: " + messageCount);

			unreadMailCount = emailInbox.getUnreadMessageCount();
			System.out.println("No. of Unread Mails = " + unreadMailCount);

			emailInbox.open(Folder.READ_WRITE);

			// Fetch unseen messages from inbox folder
			Message[] messages = emailInbox.search(
					new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			Thread.sleep(2000);
			// Sort messages from recent to oldest
			Arrays.sort( messages, ( m1, m2 ) -> {
				try {
					return m2.getSentDate().compareTo( m1.getSentDate() );
				} catch ( MessagingException e ) {
					throw new RuntimeException( e );
				}
			} );

			boolean msgReceived = false ;
			for ( Message message : messages ) {
				System.out.println( 
						"sendDate: " + message.getSentDate()
						+ " subject:" + message.getSubject() );
				
				Thread.sleep(1000);
			//	for (int i = messages.length - 1; i > 0; i--) {
					
				if(message.getSubject().contains(subject)){
										
					if (message.getContentType().toLowerCase().contains("text")) {
						if (message.isMimeType("text/html")) {
							System.out.println("Inside Loop");
							 String html = (String) message.getContent();
						        result = org.jsoup.Jsoup.parse(html).text();
						        System.out.println(result);
						           	reportout.setEmailContent(result);
						        	assertTrue(result.contains("thank you for making payment"));
						        
						}
				} 
					
					else if(message.getContent() instanceof Multipart) {

						Multipart multipart = (Multipart) message.getContent();
						
						for (int j = 0; j < multipart.getCount(); j++) {
							MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(j);
							
							if (part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
								String File = part.getFileName();
								if(File.equalsIgnoreCase(subject.replace("/", ""))) {
									part.saveFile(saveDirectory + File);
									System.out.println(File);
									//break;
									if(message.isMimeType("multipart/*")) {
										MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
								        result = getTextFromMimeMultipart(mimeMultipart);
								        reportout.setEmailContent(result);
										System.out.println("Multipart content: " +result);
									}
									
								}
																
							}
						
						}

					}
					msgReceived = true ;
					message.setFlag(Flags.Flag.SEEN, true);
					break;
				}//}
			}
			 Assert.assertTrue(msgReceived);
			emailInbox.close(true);	 
			 session.getStore("imaps").close();

		} catch (Exception mex) {	 
			mex.printStackTrace();
			Assert.fail("Not able to read mail");
		}
		 
	//	return result;
	}

	private static String getTextFromMimeMultipart(
			   MimeMultipart mimeMultipart) throws Exception{
			int count = mimeMultipart.getCount();
		    if (count == 0)
		        throw new MessagingException("Multipart with no body parts not supported.");
		    boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
		    if (multipartAlt)
		        // alternatives appear in an order of increasing 
		        // faithfulness to the original content. Customize as req'd.
		        return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		    String result = "";
		    for (int i = 0; i < count; i++) {
		        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
		        result += getTextFromBodyPart(bodyPart);
		    }
		    return result;
			}
		
		
		private static String getTextFromBodyPart(
		        BodyPart bodyPart) throws Exception {
		    
		    String result = "";
		    if (bodyPart.isMimeType("text/plain")) {
		        result = (String) bodyPart.getContent();
		    } else if (bodyPart.isMimeType("text/html")) {
		        String html = (String) bodyPart.getContent();
		        result = org.jsoup.Jsoup.parse(html).text();
		    } else if (bodyPart.getContent() instanceof MimeMultipart){
		        result = getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
		    }
		    return result;
		}
	}
				
