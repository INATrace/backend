package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@NamedQueries({
	@NamedQuery(name = "Facility.listSellingFacilitiesByCompanyAndSemiProduct",
	            query = "SELECT f FROM FacilitySemiProduct fsp JOIN fsp.facility f JOIN f.facilityTranslations t WHERE f.company.id = :companyId AND f.isPublic = true AND fsp.semiProduct.id = :semiProductId AND t.language = :language"),
	@NamedQuery(name = "Facility.countSellingFacilitiesByCompanyAndSemiProduct",
	            query = "SELECT COUNT(fsp.facility) FROM FacilitySemiProduct fsp WHERE fsp.facility.company.id = :companyId AND fsp.facility.isPublic = true AND fsp.semiProduct.id = :semiProductId")
})
public class FacilitySemiProduct extends TimestampEntity {

	@Version
	private long entityVersion;

	@ManyToOne
	@NotNull
	private Facility facility;

	@ManyToOne
	@NotNull
	private SemiProduct semiProduct;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	public SemiProduct getSemiProduct() {
		return semiProduct;
	}

	public void setSemiProduct(SemiProduct semiProduct) {
		this.semiProduct = semiProduct;
	}

	public FacilitySemiProduct() {
		super();
	}

	public FacilitySemiProduct(@NotNull Facility facility, @NotNull SemiProduct semiProduct) {
		super();
		this.facility = facility;
		this.semiProduct = semiProduct;
	}

}
