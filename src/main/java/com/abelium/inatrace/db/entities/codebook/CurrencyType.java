package com.abelium.inatrace.db.entities.codebook;

import com.abelium.inatrace.db.base.CodebookBaseEntity;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "CurrencyType.getAllCurrencyTypes",
                    query = "SELECT c FROM CurrencyType c"),
        @NamedQuery(name = "CurrencyType.getCurrencyTypeByCode",
                    query = "SELECT c FROM CurrencyType c WHERE c.code = :code")
})
public class CurrencyType extends CodebookBaseEntity {
}
