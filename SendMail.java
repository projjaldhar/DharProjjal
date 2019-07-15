public class SendMailUsingAuthentication {       
	private String HOST_NAME = "smtp.gmail.com";    
	String messageBody;       

	public static void main(String[] args) throws MessagingException {
		new SendMailUsingAuthentication().postMail(new String[]{"abc@gmail.com",
		"abc@gmail.com"}, "Test Mail", "A Test message", "abc@gmail.com", "", 
		new String[]{"./././././output/FileA.xls"});
	}
	public void postMail(String recipients[], String subject, String message,             
			String from, String emailPassword, String[] files) throws MessagingException {
		boolean debug = false;        
		//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
		//Set the host smtp address         
		Properties props = new Properties();
		//props.put("mail.transport.protocol", "smtp");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST_NAME);
		props.put("mail.smtp.port", "465");   
		//props.put("mail.smtp.auth", "true");

		Authenticator authenticator = new SMTPAuthenticator(from, emailPassword);
		Session session = Session.getDefaultInstance(props, authenticator); 
		session.setDebug(debug);
		// create a message        
		Message msg = new MimeMessage(session);
		// set the from and to address         
		InternetAddress addressFrom = new InternetAddress(from);        
		msg.setFrom(addressFrom);           
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {             
			addressTo[i] = new InternetAddress(recipients[i]);         
		}         
		msg.setRecipients(Message.RecipientType.TO, addressTo);
		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/plain");
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(message);
		Multipart multipart = new MimeMultipart();
		//add the message body to the mime message
		multipart.addBodyPart(messageBodyPart); 
		// add any file attachments to the message
		addAtachments(files, multipart);         
		//Put all message parts in the message
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("Sucessfully Sent mail to All Users");     
	}    
