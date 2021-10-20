package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.company.CompanyService;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomerLocation;
import com.abelium.inatrace.components.product.api.ApiBankInformation;
import com.abelium.inatrace.components.product.api.ApiFarmInformation;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.types.Gender;
import com.abelium.inatrace.types.UserCustomerType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.torpedoquery.jpa.Torpedo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Lazy
@Service
public class UserCustomerImportService extends BaseService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StorageService storageService;

    public void importFarmersSpreadsheet(Long companyId, Long documentId) throws ApiException {
        DocumentData documentData = storageService.downloadDocument(em.find(Document.class, documentId).getStorageKey());
        InputStream inputStream;
        XSSFWorkbook mainWorkbook;

        try {
            inputStream = new ByteArrayInputStream(documentData.file);
            mainWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            System.out.println("Could not read file");
            return;
        }

        XSSFSheet mainSheet = mainWorkbook.getSheetAt(0);

        int rowIndex = 5;

        while (true) {
            Row row = mainSheet.getRow(rowIndex);
            if (emptyRow(row)) {
                break;
            }
            if (validRow(row)) {
                ApiUserCustomer apiUserCustomer = new ApiUserCustomer();

                // Bank info
                apiUserCustomer.setBank(new ApiBankInformation());
                apiUserCustomer.getBank().setAccountHolderName(getStringOrNumeric(row.getCell(28)));
                apiUserCustomer.getBank().setAccountNumber(getStringOrNumeric(row.getCell(27)));
                apiUserCustomer.getBank().setAdditionalInformation(getStringOrNumeric(row.getCell(30)));
                apiUserCustomer.getBank().setBankName(getStringOrNumeric(row.getCell(29)));

                apiUserCustomer.setCompanyId(companyId);
                apiUserCustomer.setEmail(getString(row.getCell(17)));

                // Farm info
                apiUserCustomer.setFarm(new ApiFarmInformation());
                apiUserCustomer.getFarm().setAreaOrganicCertified(getNumericBigDecimal(row.getCell(25)));
                apiUserCustomer.getFarm().setCoffeeCultivatedArea(getNumericBigDecimal(row.getCell(22)));
                apiUserCustomer.getFarm().setNumberOfTrees(getNumericInteger(row.getCell(23)));
                apiUserCustomer.getFarm().setOrganic(getBoolean(row.getCell(24)));
                apiUserCustomer.getFarm().setStartTransitionToOrganic(getDate(row.getCell(26)));
                apiUserCustomer.getFarm().setTotalCultivatedArea(getNumericBigDecimal(row.getCell(21)));

                apiUserCustomer.setFarmerCompanyInternalId(getStringOrNumeric(row.getCell(0)));
                apiUserCustomer.setGender(getGender(row.getCell(15)));
                apiUserCustomer.setHasSmartphone(getBoolean(row.getCell(18)));

                // Location info
                apiUserCustomer.setLocation(new ApiUserCustomerLocation());
                apiUserCustomer.getLocation().setAddress(new ApiAddress());
                apiUserCustomer.getLocation().getAddress().setAddress(getString(row.getCell(10)));
                apiUserCustomer.getLocation().getAddress().setCell(getString(row.getCell(4)));
                apiUserCustomer.getLocation().getAddress().setCity(getString(row.getCell(11)));
                // Country data - specified by code
                apiUserCustomer.getLocation().getAddress().setCountry(CommonApiTools.toApiCountry(getCountryByCode(getString(row.getCell(14)))));
                apiUserCustomer.getLocation().getAddress().getCountry().setCode(getString(row.getCell(14)));
                apiUserCustomer.getLocation().getAddress().setHondurasDepartment(getString(row.getCell(9)));
                apiUserCustomer.getLocation().getAddress().setHondurasFarm(getString(row.getCell(6)));
                apiUserCustomer.getLocation().getAddress().setHondurasMunicipality(getString(row.getCell(8)));
                apiUserCustomer.getLocation().getAddress().setHondurasVillage(getString(row.getCell(7)));
                apiUserCustomer.getLocation().getAddress().setSector(getString(row.getCell(5)));
                apiUserCustomer.getLocation().getAddress().setState(getString(row.getCell(12)));
                apiUserCustomer.getLocation().getAddress().setVillage(getString(row.getCell(3)));
                apiUserCustomer.getLocation().getAddress().setZip(getStringOrNumeric(row.getCell(13)));
                apiUserCustomer.setName(getString(row.getCell(2)));
                apiUserCustomer.setPhone(getStringOrNumeric(row.getCell(16)));
                apiUserCustomer.setSurname(getString(row.getCell(1)));
                apiUserCustomer.setType(UserCustomerType.FARMER);

                companyService.addUserCustomer(companyId, apiUserCustomer);
            }

            rowIndex++;
        }
    }

    private boolean emptyRow(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = 0; i < 30; i++) {
            if (!emptyCell(row.getCell(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean emptyCell(Cell cell) {
        return cell == null || CellType._NONE.equals(cell.getCellType()) || CellType.BLANK.equals(cell.getCellType());
    }

    private boolean validRow(Row row) {
        return validCell(row.getCell(0), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(1), CellType.STRING) &&
                validCell(row.getCell(2), CellType.STRING) &&
                validCell(row.getCell(3), CellType.STRING) &&
                validCell(row.getCell(4), CellType.STRING) &&
                validCell(row.getCell(5), CellType.STRING) &&
                validCell(row.getCell(6), CellType.STRING) &&
                validCell(row.getCell(7), CellType.STRING) &&
                validCell(row.getCell(8), CellType.STRING) &&
                validCell(row.getCell(9), CellType.STRING) &&
                validCell(row.getCell(10), CellType.STRING) &&
                validCell(row.getCell(11), CellType.STRING) &&
                validCell(row.getCell(12), CellType.STRING) &&
                validCell(row.getCell(13), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(14), CellType.STRING) &&
                validCell(row.getCell(15), CellType.STRING) &&
                validCell(row.getCell(16), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(17), CellType.STRING) &&
                validCell(row.getCell(18), CellType.STRING) &&
                validCell(row.getCell(19), List.of()) &&
                validCell(row.getCell(20), List.of()) &&
                validCell(row.getCell(21), CellType.NUMERIC) &&
                validCell(row.getCell(22), CellType.NUMERIC) &&
                validCell(row.getCell(23), CellType.NUMERIC) &&
                validCell(row.getCell(24), CellType.STRING) &&
                validCell(row.getCell(25), CellType.NUMERIC) &&
                validCell(row.getCell(26), CellType.NUMERIC) &&
                validCell(row.getCell(27), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(28), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(29), List.of(CellType.STRING, CellType.NUMERIC)) &&
                validCell(row.getCell(30), List.of(CellType.STRING, CellType.NUMERIC));
    }

    private boolean validCell(Cell cell, List<CellType> cellTypeList) {
        return validCell(cell, CellType._NONE) || validCell(cell, CellType.BLANK) ||  cellTypeList.stream().anyMatch(cellType -> validCell(cell, cellType));
    }

    private boolean validCell(Cell cell, CellType cellType) {
        return cell == null || cellType.equals(cell.getCellType());
    }

    private String getString(Cell cell) {
        return emptyCell(cell) ? null : cell.getStringCellValue();
    }

    private Integer getNumericInteger(Cell cell) {
        return emptyCell(cell) ? null : (int) cell.getNumericCellValue();
    }

    private BigDecimal getNumericBigDecimal(Cell cell) {
        return emptyCell(cell) ? null : BigDecimal.valueOf(cell.getNumericCellValue());
    }

    private Boolean getBoolean(Cell cell) {
        if (emptyCell(cell)) {
            return null;
        }
        switch (cell.getStringCellValue()) {
            case "Y":
                return Boolean.TRUE;
            case "N":
                return Boolean.FALSE;
            default:
                return null;
        }
    }

    private Gender getGender(Cell cell) {
        if (emptyCell(cell)) {
            return null;
        }
        switch (cell.getStringCellValue()) {
            case "M":
                return Gender.MALE;
            case "F":
                return Gender.FEMALE;
            default:
                return null;
        }
    }

    private Date getDate(Cell cell) {
        return emptyCell(cell) ? null : cell.getDateCellValue();
    }

    private String getStringOrNumeric(Cell cell) {
        if (emptyCell(cell)) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(Double.valueOf(cell.getNumericCellValue()).longValue());
            default:
                return null;
        }
    }

    private Country getCountryByCode(String code) {
        Country country = Torpedo.from(Country.class);
        Torpedo.where(country.getCode()).eq(code);
        List<Country> countries = Torpedo.select(country).list(em);
        return countries.size() == 1 ? countries.get(0) : null;
    }
}
