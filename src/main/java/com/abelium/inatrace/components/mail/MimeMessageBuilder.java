package com.abelium.inatrace.components.mail;

import jakarta.mail.MessagingException;

import org.springframework.mail.javamail.MimeMessageHelper;

@FunctionalInterface
public interface MimeMessageBuilder {
    MimeMessageHelper build() throws MessagingException;
}
