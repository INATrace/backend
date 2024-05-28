package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.api.ApiUserCustomerImportResponse;
import com.abelium.inatrace.components.company.CompanyService;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomerLocation;
import com.abelium.inatrace.components.product.ProductTypeMapper;
import com.abelium.inatrace.components.product.api.ApiBankInformation;
import com.abelium.inatrace.components.product.api.ApiFarmInformation;
import com.abelium.inatrace.components.product.api.ApiFarmPlantInformation;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.security.service.CustomUserDetails;
import com.abelium.inatrace.types.Gender;
import com.abelium.inatrace.types.Language;
import com.abelium.inatrace.types.UserCustomerType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Service
public class UserCustomerImportService extends BaseService {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private StorageService storageService;

    public ApiUserCustomerImportResponse importFarmersSpreadsheet(Long companyId, Long documentId, CustomUserDetails user, Language language) throws ApiException {
        DocumentData documentData = storageService.downloadDocument(em.find(Document.class, documentId).getStorageKey());
        InputStream inputStream;
        XSSFWorkbook mainWorkbook;

        try {
            inputStream = new ByteArrayInputStream(documentData.file);
            mainWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new ApiException(ApiStatus.ERROR, "Could not read file");
        }

        XSSFSheet mainSheet = mainWorkbook.getSheetAt(0);

        // boolean means that the second product is also present in the Excel
        boolean hasSecondProductType = checkSecondProductType(mainSheet);

        int rowIndex = 5;
        int successful = 0;
        List<ApiUserCustomer> duplicates = new ArrayList<>();

        // company product types (first two)
        List<ApiProductType> companyProductTypes = readCompanyProductTypes(companyId, language);

        // if only first product type is given in Excel,
        // then take only the first element from company product types list
        if (!hasSecondProductType) {
            companyProductTypes = companyProductTypes.subList(0, 1);
        }

        while (true) {

            Row row = mainSheet.getRow(rowIndex);

            if (emptyRow(row)) {
                break;
            }

            if (validRow(row)) {

                ApiUserCustomer apiUserCustomer = new ApiUserCustomer();
                apiUserCustomer.setCompanyId(companyId);
                apiUserCustomer.setType(UserCustomerType.FARMER);
                apiUserCustomer.setProductTypes(companyProductTypes);

                // ID (company-internal)
                apiUserCustomer.setFarmerCompanyInternalId(getStringOrNumeric(row.getCell(0)));

                // Personal data (name, surname, email, phone, etc.)
                apiUserCustomer.setSurname(getString(row.getCell(1)));
                apiUserCustomer.setName(getString(row.getCell(2)));
                apiUserCustomer.setGender(getGender(row.getCell(16)));
                apiUserCustomer.setPhone(getStringOrNumeric(row.getCell(17)));
                apiUserCustomer.setEmail(getStringOrNumeric(row.getCell(18)));
                apiUserCustomer.setHasSmartphone(getBoolean(row.getCell(19)));

                // Address data - location info
                apiUserCustomer.setLocation(new ApiUserCustomerLocation());
                apiUserCustomer.getLocation().setAddress(new ApiAddress());
                apiUserCustomer.getLocation().getAddress().setVillage(getString(row.getCell(3)));
                apiUserCustomer.getLocation().getAddress().setCell(getString(row.getCell(4)));
                apiUserCustomer.getLocation().getAddress().setSector(getString(row.getCell(5)));
                apiUserCustomer.getLocation().getAddress().setHondurasFarm(getString(row.getCell(6)));
                apiUserCustomer.getLocation().getAddress().setHondurasVillage(getString(row.getCell(7)));
                apiUserCustomer.getLocation().getAddress().setHondurasMunicipality(getString(row.getCell(8)));
                apiUserCustomer.getLocation().getAddress().setHondurasDepartment(getString(row.getCell(9)));
                apiUserCustomer.getLocation().getAddress().setAddress(getString(row.getCell(10)));
                apiUserCustomer.getLocation().getAddress().setCity(getString(row.getCell(11)));
                apiUserCustomer.getLocation().getAddress().setState(getString(row.getCell(12)));
                apiUserCustomer.getLocation().getAddress().setZip(getStringOrNumeric(row.getCell(13)));
                apiUserCustomer.getLocation().getAddress().setOtherAddress(getString(row.getCell(14)));
                apiUserCustomer.getLocation().getAddress().setCountry(CommonApiTools.toApiCountry(getCountryByCode(getString(row.getCell(15)))));
                apiUserCustomer.getLocation().getAddress().getCountry().setCode(getString(row.getCell(15)));

                // Farm info
                apiUserCustomer.setFarm(new ApiFarmInformation());
                apiUserCustomer.getFarm().setFarmPlantInformationList(new ArrayList<>());
                apiUserCustomer.getFarm().setAreaUnit(getString(row.getCell(22)));
                apiUserCustomer.getFarm().setTotalCultivatedArea(getNumericBigDecimal(row.getCell(23)));

                ApiFarmPlantInformation apiPlant1Information = new ApiFarmPlantInformation();
                apiPlant1Information.setProductType(companyProductTypes.get(0));
                apiPlant1Information.setPlantCultivatedArea(getNumericBigDecimal(row.getCell(24)));
                apiPlant1Information.setNumberOfPlants(getNumericInteger(row.getCell(25)));
                apiUserCustomer.getFarm().getFarmPlantInformationList().add(apiPlant1Information);

                if (hasSecondProductType && companyProductTypes.get(1) != null) {
                    ApiFarmPlantInformation apiPlant2Information = new ApiFarmPlantInformation();
                    apiPlant2Information.setProductType(companyProductTypes.get(1));
                    apiPlant2Information.setPlantCultivatedArea(getNumericBigDecimal(row.getCell(26)));
                    apiPlant2Information.setNumberOfPlants(getNumericInteger(row.getCell(27)));
                    apiUserCustomer.getFarm().getFarmPlantInformationList().add(apiPlant2Information);
                }

                apiUserCustomer.getFarm().setOrganic(getBoolean(row.getCell(28)));
                if (apiUserCustomer.getFarm().getOrganic() == null) {
                    apiUserCustomer.getFarm().setOrganic(false);
                }

                apiUserCustomer.getFarm().setAreaOrganicCertified(getNumericBigDecimal(row.getCell(29)));
                apiUserCustomer.getFarm().setStartTransitionToOrganic(getDate(row.getCell(30)));

                // Bank info
                apiUserCustomer.setBank(new ApiBankInformation());
                apiUserCustomer.getBank().setAccountNumber(getStringOrNumeric(row.getCell(31)));
                apiUserCustomer.getBank().setAccountHolderName(getStringOrNumeric(row.getCell(32)));
                apiUserCustomer.getBank().setBankName(getStringOrNumeric(row.getCell(33)));
                apiUserCustomer.getBank().setAdditionalInformation(getStringOrNumeric(row.getCell(34)));

                if (companyService.existsUserCustomer(apiUserCustomer)) {
                    duplicates.add(apiUserCustomer);
                } else {
                    companyService.addUserCustomer(companyId, apiUserCustomer, user, language);
                    successful++;
                }
            }

            rowIndex++;
        }

        ApiUserCustomerImportResponse response = new ApiUserCustomerImportResponse();
        response.setSuccessful(successful);
        response.setDuplicates(duplicates);

        return response;
    }

    /**
     * Checks if second product type exist. Checks if title of the second product type is present in the Excel.
     *
     * @param xssfSheet - xsssheet
     * @return if second product header cell is set
     */
    private boolean checkSecondProductType(XSSFSheet xssfSheet) {

        // Column headers are present in the row with index 4
        int rowIndex = 4;

        Row row = xssfSheet.getRow(rowIndex);

        // check for 25th cell if second product type is specified with title
        int colIndex = 26;

        return !emptyCell(row.getCell(colIndex));
    }

    /**
     * List of company product types.
     *
     * @param companyId - used for retrieving company product types
     * @param language - request language
     * @return - list of product types.
     */
    private List<ApiProductType> readCompanyProductTypes(Long companyId, Language language) {

        ProductType productTypeProxy = companyService.getCompanyProductTypes(companyId, null);

        // currently max of 2 product types are supported
        int numberOfMaxSupportedProductTypes = 2;

        return  Torpedo.select(productTypeProxy).list(em)
                .stream()
                .limit(numberOfMaxSupportedProductTypes)
                .map(pt -> ProductTypeMapper.toApiProductTypeDetailed(pt, language))
                .collect(Collectors.toList());
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
        return validCell(row.getCell(0), List.of(CellType.STRING, CellType.NUMERIC)) &&     // Company internal ID
                validCell(row.getCell(1), CellType.STRING) &&                               // Last name
                validCell(row.getCell(2), List.of(CellType.STRING)) &&                      // First name
                validCell(row.getCell(3), List.of(CellType.STRING)) &&                      // Village
                validCell(row.getCell(4), List.of(CellType.STRING)) &&                      // Cell
                validCell(row.getCell(5), List.of(CellType.STRING)) &&                      // Sector
                validCell(row.getCell(6), List.of(CellType.STRING)) &&                      // Caserio
                validCell(row.getCell(7), List.of(CellType.STRING)) &&                      // Aldea
                validCell(row.getCell(8), List.of(CellType.STRING)) &&                      // Municipio
                validCell(row.getCell(9), List.of(CellType.STRING)) &&                      // Departamento
                validCell(row.getCell(10), List.of(CellType.STRING)) &&                     // Street Address
                validCell(row.getCell(11), List.of(CellType.STRING)) &&                     // City / Town / Village
                validCell(row.getCell(12), List.of(CellType.STRING)) &&                     // State / Province / Region
                validCell(row.getCell(13), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Zip / Postal Code / P.O. Box
                validCell(row.getCell(14), List.of(CellType.STRING)) &&                     // Additional / Other address
                validCell(row.getCell(15), CellType.STRING) &&                              // Country
                validCell(row.getCell(16), CellType.STRING) &&                              // Gender
                validCell(row.getCell(17), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Phone number
                validCell(row.getCell(18), List.of(CellType.STRING)) &&                     // E-mail
                validCell(row.getCell(19), List.of(CellType.STRING)) &&                     // Smartphone
                validCell(row.getCell(20), List.of(CellType.STRING)) &&                     // Supplier of
                validCell(row.getCell(21), List.of(CellType.STRING)) &&                     // Member of associations
                validCell(row.getCell(22), List.of(CellType.STRING)) &&                     // Area unit
                validCell(row.getCell(23), List.of(CellType.NUMERIC)) &&                    // Total cultivated area
                validCell(row.getCell(24), List.of(CellType.NUMERIC)) &&                    // Area cultivated with first plant
                validCell(row.getCell(25), List.of(CellType.NUMERIC)) &&                    // Number of (first plant) trees
                validCell(row.getCell(26), List.of(CellType.NUMERIC)) &&                    // Area cultivated with second plant
                validCell(row.getCell(27), List.of(CellType.NUMERIC)) &&                    // Number of (second plant) trees
                validCell(row.getCell(28), List.of(CellType.STRING)) &&                     // Organic production (EU)
                validCell(row.getCell(29), List.of(CellType.NUMERIC)) &&                    // Area organic certified
                validCell(row.getCell(30), List.of(CellType.NUMERIC)) &&                    // Start date of transitioning to organic
                validCell(row.getCell(31), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Account number
                validCell(row.getCell(32), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Account holder's name
                validCell(row.getCell(33), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Bank name
                validCell(row.getCell(34), List.of(CellType.STRING, CellType.NUMERIC));     // Additional information
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
            case "N/A":
                return Gender.N_A;
            case "DIVERSE":
                return Gender.DIVERSE;
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
