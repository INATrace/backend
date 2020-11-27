package com.abelium.INATrace.components.mail;

import javax.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

@FunctionalInterface
public interface MimeMessageBuilder {
    MimeMessageHelper build() throws MessagingException;
}