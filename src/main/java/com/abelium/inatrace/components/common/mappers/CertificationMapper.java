package com.abelium.inatrace.components.common.mappers;

import com.abelium.inatrace.components.common.api.ApiCertification;
import com.abelium.inatrace.db.entities.company.CompanyCertification;
import com.abelium.inatrace.db.entities.stockorder.Certification;

import java.time.LocalDate;

public class CertificationMapper {

    public static ApiCertification toApiCertificationFromCompanyCertification(CompanyCertification entity){
        ApiCertification apiCertification = new ApiCertification();
        apiCertification.setCertificate(DocumentMapper.toApiDocument(entity.getCertificate()));
        apiCertification.setType(entity.getType());
        apiCertification.setDescription(entity.getDescription());
        apiCertification.setValidity(entity.getValidity());
        return apiCertification;
    }

    public static ApiCertification toApiCertification(Certification entity) {
        ApiCertification apiCertification = new ApiCertification();
        apiCertification.setCertificate(DocumentMapper.toApiDocument(entity.getCertificate()));
        apiCertification.setType(entity.getType());
        apiCertification.setDescription(entity.getDescription());
        apiCertification.setValidity(LocalDate.parse(entity.getValidity()));
        return apiCertification;
    }
}
