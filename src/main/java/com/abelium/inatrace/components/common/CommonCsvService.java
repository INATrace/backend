package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.company.CompanyQueries;
import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import com.abelium.inatrace.db.entities.company.Company;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Service for payment csv generator.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Lazy
@Service
public class CommonCsvService extends BaseService {
	
	@Autowired
    private CompanyQueries companyQueries;
	
	public void createPaymentsByCompanyCsv(List<ApiPayment> apiPayments, Long companyId) throws ApiException, IOException {
		
		Company company = companyQueries.fetchCompany(companyId);

        // Create CSV file
		FileWriter fileWriter = new FileWriter("./payments_" + company.getName() + ".csv", false);
		try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180)) {
			
			// Headers
			csvPrinter.printRecord("Payment purpose", "Amount paid to the farmer", "Farmer name", "Company name", "Delivery date", "Payment date",  "Way of payment");
			// Data
			for (ApiPayment p : apiPayments) {
				csvPrinter.printRecord(
					p.getPaymentPurposeType(), p.getAmountPaidToTheFarmer(), 
					p.getRecipientUserCustomer().getName(), "TBD",
					p.getProductionDate(), p.getFormalCreationTime(), 
					p.getPreferredWayOfPayment());
			}
			
		csvPrinter.flush();
		}
	}
	
	public void createPurchasesByCompanyCsv(List<ApiStockOrder> apiStockOrders, Long companyId) throws ApiException, IOException {
		
		Company company = companyQueries.fetchCompany(companyId);

        // Create CSV file
		FileWriter fileWriter = new FileWriter("./purchases_" + company.getName() + ".csv", false);
		try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180)) {
			
			// Headers
			csvPrinter.printRecord("Delivery date", "Farmer name", "Quantity", "Payable", "Open Balance",  "Way of payment");
			// Data
			for (ApiStockOrder p : apiStockOrders) {
				csvPrinter.printRecord(
					p.getProductionDate(),
					p.getProducerUserCustomer().getName(), p.getTotalQuantity(),
					p.getPaid(), p.getBalance(), 
					p.getPreferredWayOfPayment());
			}
			
		csvPrinter.flush();
		}
	}

}
