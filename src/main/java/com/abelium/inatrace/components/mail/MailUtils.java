package com.abelium.inatrace.components.mail;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.abelium.inatrace.tools.ResourceTools;


public class MailUtils
{
    private static final String LOGO = "/images/logo-inatrace-tm.png";
    
    /**
     * Parse a comma separated list of internet adddresses.
     * @see InternetAddress#parse(String)
     * @param helper a {@link MimeMessageHelper} to supply encoding 
     * @param addresses a comma separated list of addresses in RFC822 syntax 
     * @return a list of {@link InternetAddress} objects, to be used in {@link MimeMessageHelper#setTo(InternetAddress[])},
     *   {@link MimeMessageHelper#setCc(InternetAddress[])}, etc.
     * @throws MessagingException if addresses cannot be parsed or encoded
     */
    public static InternetAddress[] parseAddresses(MimeMessageHelper helper, String addresses) throws MessagingException {
        try {
            addresses = StringUtils.replace(addresses, ";", ",");
            InternetAddress[] parsed = InternetAddress.parse(addresses);
            String encoding = helper.getEncoding();
            if ( encoding != null ) {
                for ( int i = 0; i < parsed.length; i++ ) {
                    InternetAddress raw = parsed[i];
                    parsed[i] = new InternetAddress(raw.getAddress(), raw.getPersonal(), encoding);
                }
            }
            return parsed;
        } catch ( AddressException ex ) {
            throw new MessagingException("Cannnot parse email address(es)", ex);
        } catch (UnsupportedEncodingException ex) {
            throw new MessagingException("Failed to parse embedded personal name to correct encoding", ex);
        }
    }

    /**
     * Add inline image to mail message.
     * @param helper helper building the mail
     * @param resourceName complete path to the image resource
     * @param inlineName image name within mail message
     * @throws MessagingException if resource doesn't exist or some other error occurs when reading resource
     */
    public static void addInlineImage(MimeMessageHelper helper, String resourceName, String inlineName, String fileName) throws MessagingException {
        try {
            byte[] data = ResourceTools.loadResourceBytes(resourceName);
            addInlineImage(helper, data, inlineName, fileName);
        } catch (FileNotFoundException e) {
            throw new MessagingException("Resource '" + resourceName + "' not found");
        } catch (Exception e) {
            throw new MessagingException("Error reading image resource", e);
        }
    }
    
    /**
     * Add inline image to mail message.
     * @param helper helper building the mail
     * @param imageDataSource image data source
     * @param inlineName image name within mail message
     * @throws MessagingException if resource doesn't exist or some other error occurs when reading resource
     */
    public static void addInlineImage(MimeMessageHelper helper, byte[] imageDataSource, String inlineName, String fileName) throws MessagingException {
        try {
            String mediaType = ResourceTools.mediaTypeDetector().detect(imageDataSource);
            ByteArrayDataSource resource = new ByteArrayDataSource(imageDataSource, mediaType);
            resource.setName(inlineName);
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDisposition(MimeBodyPart.INLINE);
            mimeBodyPart.setHeader("Content-ID", "<" + inlineName + ">");
            mimeBodyPart.setDataHandler(new DataHandler(resource));
            mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));
            helper.getMimeMultipart().addBodyPart(mimeBodyPart);
        } catch (UnsupportedEncodingException ex) {
            throw new MessagingException("Failed to encode attachment filename", ex);
        }
    }
    
    public static void addCsvFile(MimeMessageHelper helper, byte[] imageDataSource, String fName) throws MessagingException {
        ByteArrayDataSource resource = new ByteArrayDataSource(imageDataSource, "text/csv");
        resource.setName(fName);
        helper.addAttachment(fName, resource);
    }

    public static void addHtmlEmailImages(MimeMessageHelper helper) throws MessagingException {
        addInlineImage(helper, LOGO, "logo", "logo-inatrace-tm.png");
    }
}
