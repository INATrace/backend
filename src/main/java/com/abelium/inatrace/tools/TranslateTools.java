package com.abelium.inatrace.tools;

import com.abelium.inatrace.types.Language;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class TranslateTools {

	public static String getTranslatedValue(MessageSource messageSource, String messageId, Language language) {
		return messageSource.getMessage(messageId, null, Locale.forLanguageTag(language.name()));
	}
}
