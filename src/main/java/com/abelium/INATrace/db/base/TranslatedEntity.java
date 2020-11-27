
package com.abelium.INATrace.db.base;

import java.util.EnumSet;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.abelium.INATrace.api.types.Lengths;
import com.abelium.INATrace.types.Language;

/**
 * Base class for "translated entity" (entity with language)
 */
@MappedSuperclass
public class TranslatedEntity extends BaseEntity {
	
	public static final EnumSet<Language> ALLOWED_TRANSLATIONS = EnumSet.of(Language.DE, Language.RW);
	
	protected TranslatedEntity() {
	}

	public TranslatedEntity(Language language) {
		super();
		this.setLanguage(language);
	}
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = Lengths.ENUM)
    private Language language;

	public Language getLanguage() {
		return language;
	}

	protected void setLanguage(Language language) {
		if (!ALLOWED_TRANSLATIONS.contains(language)) throw new IllegalArgumentException("Language '" + language + "' is not allowed.");
		this.language = language;
	}
}
