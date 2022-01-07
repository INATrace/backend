package com.abelium.inatrace.components.mail;

@SuppressWarnings("serial")
public class MailSendingException extends Exception
{
    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
