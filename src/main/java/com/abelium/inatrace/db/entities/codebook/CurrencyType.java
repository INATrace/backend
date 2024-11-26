package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "CurrencyType.getAllCurrencyTypes",
                    query = "SELECT c FROM CurrencyType c"),
        @NamedQuery(name = "CurrencyType.getEnabledCurrencyTypes",
                    query = "SELECT c FROM CurrencyType c WHERE c.enabled = true"),
        @NamedQuery(name = "CurrencyType.getDisabledCurrencyTypes",
                    query = "SELECT c FROM CurrencyType c WHERE c.enabled = false"),
        @NamedQuery(name = "CurrencyType.getCurrencyTypeByCode",
                    query = "SELECT c FROM CurrencyType c WHERE c.code = :code")
})
public class CurrencyType extends CodebookBaseEntity {

    @Column
    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
