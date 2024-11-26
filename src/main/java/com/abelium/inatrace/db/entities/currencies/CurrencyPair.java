package com.abelium.inatrace.db.entities.currencies;

import com.abelium.inatrace.db.base.BaseEntity;
import com.abelium.inatrace.db.entities.codebook.CurrencyType;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@NamedQuery(name = "CurrencyPair.getLatestDate",
            query = "SELECT c.date FROM CurrencyPair c ORDER BY c.date DESC")
@NamedQuery(name = "CurrencyPair.latestRate",
            query = "SELECT c.value FROM CurrencyPair c WHERE c.from.code = 'EUR' AND c.to.code = :currency ORDER BY c.date DESC")
@NamedQuery(name = "CurrencyPair.rateAtDate",
            query = "SELECT c.value FROM CurrencyPair c WHERE c.from.code = 'EUR' AND c.to.code = :currency AND c.date = :date")
public class CurrencyPair extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private CurrencyType from;

    @ManyToOne
    private CurrencyType to;

    private BigDecimal value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CurrencyType getFrom() {
        return from;
    }

    public void setFrom(CurrencyType from) {
        this.from = from;
    }

    public CurrencyType getTo() {
        return to;
    }

    public void setTo(CurrencyType to) {
        this.to = to;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
