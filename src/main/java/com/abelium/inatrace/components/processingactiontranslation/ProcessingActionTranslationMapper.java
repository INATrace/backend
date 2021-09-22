package com.abelium.inatrace.components.processingactiontranslation;

import com.abelium.inatrace.components.processingactiontranslation.api.ApiProcessingActionTranslation;
import com.abelium.inatrace.db.entities.processingaction.ProcessingActionTranslation;

public class ProcessingActionTranslationMapper {

    public static ApiProcessingActionTranslation toApiProcessingActionTranslation(ProcessingActionTranslation entity){
        ApiProcessingActionTranslation translation = new ApiProcessingActionTranslation();
        translation.setName(entity.getName());
        translation.setDescription(entity.getDescription());
        translation.setLanguage(entity.getLanguage());
        return translation;
    }
}
