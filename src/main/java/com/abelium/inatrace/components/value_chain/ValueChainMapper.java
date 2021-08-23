package com.abelium.inatrace.components.value_chain;

import com.abelium.inatrace.components.codebook.facility_type.FacilityTypeMapper;
import com.abelium.inatrace.components.codebook.grade_abbreviation.GradeAbbreviationMapper;
import com.abelium.inatrace.components.codebook.measure_unit_type.MeasureUnitTypeMapper;
import com.abelium.inatrace.components.codebook.processing_evidence_type.ProcessingEvidenceTypeMapper;
import com.abelium.inatrace.components.codebook.semiproduct.SemiProductMapper;
import com.abelium.inatrace.components.value_chain.api.ApiValueChain;
import com.abelium.inatrace.db.entities.value_chain.ValueChain;

import java.util.stream.Collectors;

/**
 * Mapper for value chain entity.
 *
 * @author Pece Adjievski, Sunesis d.o.o.
 */
public final class ValueChainMapper {

	private ValueChainMapper () {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Mapping {@link ValueChain} into {@link ApiValueChain}. This method maps only the basic attributes - collections are not mapped.
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiValueChain toApiValueChainBase(ValueChain entity) {

		ApiValueChain apiValueChain = new ApiValueChain();
		apiValueChain.setId(entity.getId());
		apiValueChain.setName(entity.getName());
		apiValueChain.setDescription(entity.getDescription());
		apiValueChain.setValueChainStatus(entity.getValueChainStatus());

		return apiValueChain;
	}

	/**
	 * Mapping {@link ValueChain} into {@link ApiValueChain}. This method maps the basic attributes and all the linked collections.
	 * @param entity DB entity.
	 * @return API model entity.
	 */
	public static ApiValueChain toApiValueChain(ValueChain entity) {

		ApiValueChain apiValueChain = ValueChainMapper.toApiValueChainBase(entity);

		// Map facility types
		if (!entity.getFacilityTypes().isEmpty()) {
			apiValueChain.setFacilityTypes(entity.getFacilityTypes().stream()
					.map(vcFacilityType -> FacilityTypeMapper.toApiFacilityType(vcFacilityType.getFacilityType()))
					.collect(Collectors.toList()));
		}

		// Map measure unit types
		if (!entity.getMeasureUnitTypes().isEmpty()) {
			apiValueChain.setMeasureUnitTypes(entity.getMeasureUnitTypes().stream()
					.map(vcMeasureUnitType -> MeasureUnitTypeMapper.toApiMeasureUnitTypeBase(
							vcMeasureUnitType.getMeasureUnitType())).collect(Collectors.toList()));
		}

		// Map grade abbreviations
		if (!entity.getGradeAbbreviations().isEmpty()) {
			apiValueChain.setGradeAbbreviations(entity.getGradeAbbreviations().stream()
					.map(vcGradeAbbreviation -> GradeAbbreviationMapper.toApiGradeAbbreviation(
							vcGradeAbbreviation.getGradeAbbreviationType())).collect(Collectors.toList()));
		}

		// Map processing evidence types
		if (!entity.getProcEvidenceTypes().isEmpty()) {
			apiValueChain.setProcessingEvidenceTypes(entity.getProcEvidenceTypes().stream()
					.map(vcProcEvidenceType -> ProcessingEvidenceTypeMapper.toApiProcessingEvidenceTypeBase(
							vcProcEvidenceType.getProcessingEvidenceType())).collect(Collectors.toList()));
		}

		// Map semi-products
		if (!entity.getSemiProducts().isEmpty()) {
			apiValueChain.setSemiProducts(entity.getSemiProducts().stream()
					.map(vcSemiProduct -> SemiProductMapper.toApiSemiProductBase(vcSemiProduct.getSemiProduct()))
					.collect(Collectors.toList()));
		}

		return apiValueChain;
	}
}
