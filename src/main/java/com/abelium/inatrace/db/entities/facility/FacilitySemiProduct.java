package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TimestampEntity;
import com.abelium.inatrace.db.entities.codebook.SemiProduct;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Getter
@Setter
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

}
