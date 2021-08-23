package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.FacilityType;
import com.abelium.inatrace.db.entities.common.Location;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(indexes = { @Index(columnList = "name") })
public class Facility extends TimestampEntity {

  @Column
  private Long entityVersion;
  
  @Column
  private String name;

  @Column
  private Boolean isCollectionFacility;

  @Column
  private Boolean isPublic;
  
  @OneToOne
  private Location location;
  
  @OneToMany(mappedBy = "facility")
  private List<FacilityCompany> companyFacilities = new ArrayList<>();

  @OneToMany(mappedBy = "facility")
  private List<FacilityType> facilityTypes = new ArrayList<>();

  @OneToMany(mappedBy = "facility")
  private List<FacilitySemiProduct> facilitySemiProducts = new ArrayList<>();

}
