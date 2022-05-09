package com.abelium.inatrace.db.entities.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ProductLabelCompanyDocument implements Serializable {

    @Id
    private Long productLabelId;

    @Id
    private Long companyDocumentId;

    public Long getProductLabelId() {
        return productLabelId;
    }

    public void setProductLabelId(Long productLabelId) {
        this.productLabelId = productLabelId;
    }

    public Long getCompanyDocumentId() {
        return companyDocumentId;
    }

    public void setCompanyDocumentId(Long companyDocumentId) {
        this.companyDocumentId = companyDocumentId;
    }
}
