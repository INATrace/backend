package com.abelium.INATrace.components.user;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.abelium.INATrace.components.common.BaseEngine;

@Lazy
@Component
public class NotificationEngine extends BaseEngine {
	
    @Value("${INATrace.info.mail}")
    private String infoMail;

    @Autowired
    private SpringTemplateEngine templateEngine;


    public String createEmailConfirmationEmail(String name, String surname, String link) {
        Context context = new Context(Locale.ENGLISH);
        context.setVariable("heading", true);
        context.setVariable("greetingsTitle", "Thank you for signing up & welcome to INATrace");
        context.setVariable("greeting", "Dear " + name + " " + surname);
        context.setVariable("message", 
        	"<p>We received your details and will get back to you as soon as possible.</p>" + 
        	"<p>We make sure that everyone who registers is who they say they are, and for that we will step in contact with you to perform our standard verification due diligence. " +
        	"Please prepare the following documents in advance for your verification process to be as quick and efficient as possible: <p>" + 
        	"<ul>" +
        	"<li> Incorporation documentation" + 
        	"<li> 1 recent utility bill with clearly stated company name and address from the last 3 months*" + 
        	"</ul>" +
        	"<p>*In some cases, we will need some other documents to complete the verification process</p>" + 
        	"<p>You can login into your INATrace dashboard using the email and password you used to register and get a head start in preparing your profile!</p>" + 
        	"<p>If you have any further questions regarding INATrace, do not hesitate to contact us at <a href=\"mailto:" + infoMail + "\">" + infoMail + "</a> or use the support chat in your INATrace " +
        	"dashboard (green button in bottom right corner of the screen).</p>");
        context.setVariable("signatureMessage", "We look forward to chatting soon!");
        context.setVariable("signature", "Best Regards,");
        context.setVariable("INATraceTeam", "Team INATrace!");
        context.setVariable("link", link);
        context.setVariable("linkText", "START NOW");
        return templateEngine.process("inline/basic-ar.html", context);	
    }

    public String createConfirmationEmail(String name, String surname) {
        Context context = new Context(Locale.ENGLISH);
        context.setVariable("heading", true);
        context.setVariable("greetingsTitle", "Congratulations, your registration is complete!");
        context.setVariable("greeting", "Dear " + name + " " + surname);
        context.setVariable("message", 
        	"<p>Your account has been successfully verified. Congratulations on taking the first step towards Transparency for Better Returns!</p>" + 
        	// "<p>Follow this link to the login site where you will login into your dashboard and you will be able to start inputting your products.<p>" + 
        	"<p>If you need any assistance, please contact us through the support chat on your INATrace dashboard " +
        	"(green button in bottom right corner of the screen) or at <a href=\"mailto:" + infoMail + "\">" + infoMail + "</a> and we will get" + 
        	"in touch with you as soon as possible!</p>");
        context.setVariable("signatureMessage", "We look forward to chatting soon!");
        context.setVariable("signature", "Best Regards,");
        context.setVariable("INATraceTeam", "Team INATrace!");
        return templateEngine.process("inline/basic-ar.html", context);	
    }
    
    public String createPasswordResetEmail(String link) {
        Context context = new Context(Locale.ENGLISH);
        context.setVariable("heading", true);
        context.setVariable("greetingsTitle", "INATrace account password reset");
        context.setVariable("greeting", "");
        context.setVariable("message", 
            "<p>Click the button below to reset your INATrace account password.</p>" +
    		"<p>If you didn't request a new password, please delete this email.</p>" +
    	    "<p>If that doesn't work please copy and paste the following link in your browser: " +
    	    "<a href=\"" + link + "\">" + link + "</a>" +
    	    "</p>");
        context.setVariable("signatureMessage", "");
        context.setVariable("signature", "Best Regards,");
        context.setVariable("INATraceTeam", "Team INATrace!");
        context.setVariable("link", link);
        context.setVariable("linkText", "RESET PASSWORD");
        return templateEngine.process("inline/basic-ar.html", context);	
    }    
    
}

