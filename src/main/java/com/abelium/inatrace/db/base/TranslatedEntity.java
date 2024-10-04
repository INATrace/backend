package com.abelium.inatrace.db.base;

import com.abelium.inatrace.api.types.Lengths;
import com.abelium.inatrace.types.Language;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.util.EnumSet;

/**
 * Base class for "translated entity" (entity with language)
 */
@MappedSuperclass
public class TranslatedEntity extends BaseEntity {
	
	public static final EnumSet<Language> ALLOWED_TRANSLATIONS = EnumSet.of(Language.EN, Language.DE, Language.RW, Language.ES);
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private Language language;
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		if (!ALLOWED_TRANSLATIONS.contains(language)) throw new IllegalArgumentException("Language '" + language + "' is not allowed.");
		this.language = language;
	}
	
	protected TranslatedEntity() {
		
	}

	public TranslatedEntity(Language language) {
		super();
		this.setLanguage(language);
	}
	
}
