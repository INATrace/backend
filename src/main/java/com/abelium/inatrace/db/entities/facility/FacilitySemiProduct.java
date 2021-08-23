package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class FacilitySemiProduct extends TimestampEntity {

  @Version
  private long entityVersion;

  @ManyToOne
  @NotNull
  private Facility facility;

  @ManyToOne
  @NotNull
  private SemiProduct semiProduct;

  public long getEntityVersion() {
    return entityVersion;
  }

  public void setEntityVersion(long entityVersion) {
    this.entityVersion = entityVersion;
  }

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

  public FacilitySemiProduct(long entityVersion, @NotNull Facility facility, @NotNull SemiProduct semiProduct) {
    super();
    this.entityVersion = entityVersion;
    this.facility = facility;
    this.semiProduct = semiProduct;
  }

}
