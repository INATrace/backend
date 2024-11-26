package com.abelium.inatrace.db.entities.facility;

import com.abelium.inatrace.db.base.TranslatedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class FacilityTranslation extends TranslatedEntity {

    @Version
    private Long entityVersion;

    @Column
    private String name;

    @ManyToOne
    @NotNull
    private Facility facility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }
}
