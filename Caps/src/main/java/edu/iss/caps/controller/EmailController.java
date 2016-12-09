package edu.iss.caps.controller;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import edu.iss.caps.model.Course;
import edu.iss.caps.model.StudentDetail;
import edu.iss.caps.service.StudentService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.Message;
import javax.mail.MessagingException;

import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Controller

public class EmailController {
	Properties mailServerProperties;
	javax.mail.Session getMailSession;
	MimeMessage generateMailMessage;
	@Autowired
	private StudentService sService;
	@RequestMapping(value = "/email")
	public void generateAndSendEmail(HttpServletRequest request) throws AddressException, MessagingException {
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		try{
		//Object sid=request.getSession().getAttribute("name");
		//String studentid=(String) sid;
		Course c = (Course) request.getAttribute("course");
		StudentDetail s= (StudentDetail) request.getAttribute("student");
		
	    String email=s.getEmail();
	    
	    
	    
	    
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "25");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = 	javax.mail.Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		generateMailMessage.setSubject("Greetings ");
		String emailBody = "Dear "+s.getLastName()+", You have successfully enrolled in "+
				c.getCourseName()
				+ "<br><br> Regards, <br>Team7";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");

		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "sa43team7@gmail.com", "Password6");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();} catch(Exception e){
			System.out.println(e.getMessage());
		}

		
	}

	
	}


