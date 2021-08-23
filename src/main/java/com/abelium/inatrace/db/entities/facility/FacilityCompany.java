package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.company.Company;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
public class FacilityCompany extends TimestampEntity {

  @Version
  private long entityVersion;

  @ManyToOne
  @NotNull
  private Company company;

  @ManyToOne
  @NotNull
  private Facility facility;

  public long getEntityVersion() {
    return entityVersion;
  }

  public void setEntityVersion(long entityVersion) {
    this.entityVersion = entityVersion;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Facility getFacility() {
    return facility;
  }

  public void setFacility(Facility facility) {
    this.facility = facility;
  }

  public FacilityCompany() {
    super();
    // TODO Auto-generated constructor stub
  }

  public FacilityCompany(long entityVersion, @NotNull Company company, @NotNull Facility facility) {
    super();
    this.entityVersion = entityVersion;
    this.company = company;
    this.facility = facility;
  }

}
