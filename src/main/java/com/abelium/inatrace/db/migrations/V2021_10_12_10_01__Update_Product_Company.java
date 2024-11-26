package com.abelium.inatrace.db.migrations;

import com.abelium.inatrace.components.flyway.JpaMigration;
import com.abelium.inatrace.db.entities.company.Company;
import com.abelium.inatrace.db.entities.product.Product;
import com.abelium.inatrace.db.entities.product.ProductCompany;
import com.abelium.inatrace.tools.Queries;
import com.abelium.inatrace.types.ProductCompanyType;
import org.springframework.core.env.Environment;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * @author Nejc Rebernik, Sunesis d.o.o.
 */
public class V2021_10_12_10_01__Update_Product_Company implements JpaMigration {

    @Override
    public void migrate(EntityManager em, Environment environment) throws Exception {
        List<ProductCompany> productCompanyList = Queries.getAll(em, ProductCompany.class);
        for (ProductCompany productCompany : productCompanyList) {
            switch (productCompany.getType()) {
                case OWNER:
                    productCompany.setType(ProductCompanyType.EXPORTER);
                    break;
                case ROASTER:
                    productCompany.setType(ProductCompanyType.PROCESSOR);
                    break;
                default:
                    break;
            }
        }

        List<Product> productList = Queries.getAll(em, Product.class);
        for (Product product : productList) {
            if (product.getAssociatedCompanies().stream().noneMatch(productCompany -> productCompany.getType() == ProductCompanyType.OWNER) && product.getCompany() != null && product.getCompany().getId() != null) {
                ProductCompany productCompany = new ProductCompany();
                productCompany.setCompany(em.find(Company.class, product.getCompany().getId()));
                productCompany.setProduct(product);
                productCompany.setType(ProductCompanyType.OWNER);
                product.getAssociatedCompanies().add(productCompany);
            }
        }
    }
}
