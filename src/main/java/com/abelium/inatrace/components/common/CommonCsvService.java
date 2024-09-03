package com.abelium.inatrace.components.common;

import com.abelium.inatrace.components.payment.api.ApiPayment;
import com.abelium.inatrace.components.stockorder.api.ApiStockOrder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Service for payment csv generator.
 *
 * @author Rene Flores, Sunesis d.o.o.
 */
@Deprecated
@Lazy
@Service
public class CommonCsvService extends BaseService {

	@Deprecated
	public byte[] createPaymentsByCompanyCsv(List<ApiPayment> apiPayments) throws IOException {
		
		// Create CSV byte array
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(bytes), CSVFormat.RFC4180)) {
			
			// Headers
			csvPrinter.printRecord("Payment purpose", "Amount paid to the farmer", "Farmer name", "Company name", "Delivery date", "Payment date",  "Way of payment");

			// Data
			for (ApiPayment p : apiPayments) {
				csvPrinter.printRecord(
					p.getPaymentPurposeType(),
						p.getAmount(),
						p.getRecipientUserCustomer().getName() + " " + p.getRecipientUserCustomer().getSurname(),
						p.getRecipientCompany() != null ? p.getRecipientCompany().getName() : "/",
						p.getProductionDate(),
						p.getFormalCreationTime(),
						p.getPreferredWayOfPayment());
			}
			
		csvPrinter.flush();
		}
		
		return bytes.toByteArray();
	}

	@Deprecated
	public byte[] createPurchasesByCompanyCsv(List<ApiStockOrder> apiStockOrders) throws IOException {
		
		// Create CSV byte array
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(bytes), CSVFormat.RFC4180)) {
			
			// Headers
			csvPrinter.printRecord("Delivery date", "Farmer name", "Semi-product", "Quantity", "Payable", "Open Balance",  "Way of payment");

			// Data
			for (ApiStockOrder p : apiStockOrders) {
				csvPrinter.printRecord(
						p.getProductionDate(),
						p.getProducerUserCustomer().getName() + " " + p.getProducerUserCustomer().getSurname(),
						p.getSemiProduct().getName(),
						p.getTotalQuantity(),
						p.getCost(),
						p.getBalance(),
						p.getPreferredWayOfPayment());
			}
			
		csvPrinter.flush();
		}
		
		return bytes.toByteArray();
	}

}
