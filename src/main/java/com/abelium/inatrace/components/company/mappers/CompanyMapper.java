package com.abelium.inatrace.components.company.mappers;

import com.abelium.inatrace.components.common.mappers.CertificationMapper;
import com.abelium.inatrace.components.common.mappers.DocumentMapper;
import com.abelium.inatrace.components.company.api.ApiCompany;
import com.abelium.inatrace.db.entities.company.Company;

import java.util.stream.Collectors;

public class CompanyMapper {

    public static ApiCompany toApiCompany(Company entity){
        ApiCompany apiCompany = new ApiCompany();
        apiCompany.setId(entity.getId());
        apiCompany.setDocuments(entity.getDocuments()
                .stream()
                .map(CompanyDocumentMapper::toApiCompanyDocument)
                .collect(Collectors.toList()));
        apiCompany.setCertifications(entity.getCertifications()
                .stream()
                .map(CertificationMapper::toApiCertificationFromCompanyCertification)
                .collect(Collectors.toList()));
        apiCompany.setInterview(entity.getInterview());
        apiCompany.setLogo(DocumentMapper.toApiDocument(entity.getLogo()));
        apiCompany.setAbout(entity.getAbout());
        apiCompany.setAbbreviation(entity.getAbbreviation());
        apiCompany.setEmail(entity.getEmail());
        apiCompany.setHeadquarters(AddressMapper.toApiAddress(entity.getHeadquarters()));
        apiCompany.setManager(entity.getManager());
        apiCompany.setName(entity.getName());
        apiCompany.setPhone(entity.getPhone());
        apiCompany.setWebPage(entity.getWebPage());
        apiCompany.setMediaLinks(entity.getMediaLinks());
        return apiCompany;
    }

}
