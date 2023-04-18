package com.abelium.inatrace.components.common;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.common.api.ApiUserCustomerImportResponse;
import com.abelium.inatrace.components.company.CompanyService;
import com.abelium.inatrace.components.company.api.ApiAddress;
import com.abelium.inatrace.components.company.api.ApiUserCustomer;
import com.abelium.inatrace.components.company.api.ApiUserCustomerAssociation;
import com.abelium.inatrace.components.company.api.ApiUserCustomerLocation;
import com.abelium.inatrace.components.company.mappers.CompanyMapper;
import com.abelium.inatrace.components.product.ProductTypeMapper;
import com.abelium.inatrace.components.product.api.ApiBankInformation;
import com.abelium.inatrace.components.product.api.ApiFarmInformation;
import com.abelium.inatrace.components.product.api.ApiPlantInformation;
import com.abelium.inatrace.components.product.api.ApiProductType;
import com.abelium.inatrace.db.entities.codebook.ProductType;
import com.abelium.inatrace.db.entities.common.Country;
import com.abelium.inatrace.db.entities.common.Document;
import com.abelium.inatrace.db.entities.company.Company;
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
import java.util.*;
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

        // map for accessing column indexes by code
        Map<String, Integer> columnIndexesMap = calculcateRowPointers(mainSheet);

        int rowIndex = 5;
        int successful = 0;
        List<ApiUserCustomer> duplicates = new ArrayList<>();

        // company product types that are present in spreadsheet
        List<ApiProductType> customerProductTypes = readCompanyProductTypes(companyId, language, columnIndexesMap);

        while (true) {
            Row row = mainSheet.getRow(rowIndex);
            if (emptyRow(row)) {
                break;
            }
            if (validRow(row,columnIndexesMap)) {
                ApiUserCustomer apiUserCustomer = new ApiUserCustomer();

                // Bank info
                apiUserCustomer.setBank(new ApiBankInformation());
                apiUserCustomer.getBank().setAccountHolderName(getStringOrNumeric(row.getCell(columnIndexesMap.get("A28"))));
                apiUserCustomer.getBank().setAccountNumber(getStringOrNumeric(row.getCell(columnIndexesMap.get("A27"))));
                apiUserCustomer.getBank().setAdditionalInformation(getStringOrNumeric(row.getCell(columnIndexesMap.get("A30"))));
                apiUserCustomer.getBank().setBankName(getStringOrNumeric(row.getCell(columnIndexesMap.get("A29"))));

                apiUserCustomer.setCompanyId(companyId);
                apiUserCustomer.setEmail(getString(row.getCell(columnIndexesMap.get("A18"))));

                // Farm info
                apiUserCustomer.setFarm(new ApiFarmInformation());
                apiUserCustomer.getFarm().setAreaOrganicCertified(getNumericBigDecimal(row.getCell(columnIndexesMap.get("A25"))));
                apiUserCustomer.getFarm().setAreaUnit(getString(row.getCell(columnIndexesMap.get("A22"))));

                apiUserCustomer.getFarm().setPlantInformationList(new ArrayList<>());
                customerProductTypes.forEach(apiProductType -> {
                    ApiPlantInformation apiPlantInformation = new ApiPlantInformation();
                    apiPlantInformation.setProductType(apiProductType);

//                    switch (apiProductType.getName()) {
//                        case "Coffee":
//                            apiPlantInformation.setPlantCultivatedArea(getNumericBigDecimal(row.getCell(columnIndexesMap.get("COFFEE-1"))));
//                            apiPlantInformation.setNumberOfPlants(getNumericInteger(row.getCell(columnIndexesMap.get("COFFEE-2"))));
//                            break;
//                        case "Macadamia":
//                            apiPlantInformation.setPlantCultivatedArea(getNumericBigDecimal(row.getCell(columnIndexesMap.get("MACADAMIA-1"))));
//                            apiPlantInformation.setNumberOfPlants(getNumericInteger(row.getCell(columnIndexesMap.get("MACADAMIA-2"))));
//                            break;
//                        default:
//                    }

                    if (columnIndexesMap.get(apiProductType.getFieldName() + "-1") != null) {
                        apiPlantInformation.setPlantCultivatedArea(getNumericBigDecimal(
                                row.getCell(columnIndexesMap.get(apiProductType.getFieldName() + "-1"))));
                    }
                    if (columnIndexesMap.get(apiProductType.getFieldName() + "-2") != null) {
                        apiPlantInformation.setNumberOfPlants(getNumericInteger(
                                row.getCell(columnIndexesMap.get(apiProductType.getFieldName() + "-2"))));
                    }

                    apiUserCustomer.getFarm().getPlantInformationList().add(apiPlantInformation);
                });


                // set product
                apiUserCustomer.getFarm().setOrganic(getBoolean(row.getCell(columnIndexesMap.get("A24"))));
                apiUserCustomer.getFarm().setStartTransitionToOrganic(getDate(row.getCell(columnIndexesMap.get("A26"))));
                apiUserCustomer.getFarm().setTotalCultivatedArea(getNumericBigDecimal(row.getCell(columnIndexesMap.get("A23"))));

                apiUserCustomer.setFarmerCompanyInternalId(getStringOrNumeric(row.getCell(columnIndexesMap.get("A1"))));
                apiUserCustomer.setGender(getGender(row.getCell(columnIndexesMap.get("A16"))));
                apiUserCustomer.setHasSmartphone(getBoolean(row.getCell(columnIndexesMap.get("A19"))));

                // Location info
                apiUserCustomer.setLocation(new ApiUserCustomerLocation());
                apiUserCustomer.getLocation().setAddress(new ApiAddress());
                apiUserCustomer.getLocation().getAddress().setAddress(getString(row.getCell(columnIndexesMap.get("A11"))));
                apiUserCustomer.getLocation().getAddress().setCell(getString(row.getCell(columnIndexesMap.get("A5"))));
                apiUserCustomer.getLocation().getAddress().setCity(getString(row.getCell(columnIndexesMap.get("A12"))));
                // Country data - specified by code
                apiUserCustomer.getLocation().getAddress().setCountry(CommonApiTools.toApiCountry(getCountryByCode(getString(row.getCell(columnIndexesMap.get("A15"))))));
                apiUserCustomer.getLocation().getAddress().getCountry().setCode(getString(row.getCell(columnIndexesMap.get("A15"))));
                apiUserCustomer.getLocation().getAddress().setHondurasDepartment(getString(row.getCell(columnIndexesMap.get("A10"))));
                apiUserCustomer.getLocation().getAddress().setHondurasFarm(getString(row.getCell(columnIndexesMap.get("A7"))));
                apiUserCustomer.getLocation().getAddress().setHondurasMunicipality(getString(row.getCell(columnIndexesMap.get("A9"))));
                apiUserCustomer.getLocation().getAddress().setHondurasVillage(getString(row.getCell(columnIndexesMap.get("A8"))));
                apiUserCustomer.getLocation().getAddress().setSector(getString(row.getCell(columnIndexesMap.get("A6"))));
                apiUserCustomer.getLocation().getAddress().setState(getString(row.getCell(columnIndexesMap.get("A13"))));
                apiUserCustomer.getLocation().getAddress().setVillage(getString(row.getCell(columnIndexesMap.get("A4"))));
                apiUserCustomer.getLocation().getAddress().setZip(getStringOrNumeric(row.getCell(columnIndexesMap.get("A14"))));
                apiUserCustomer.setName(getString(row.getCell(columnIndexesMap.get("A3"))));
                apiUserCustomer.setPhone(getStringOrNumeric(row.getCell(columnIndexesMap.get("A17"))));
                apiUserCustomer.setSurname(getString(row.getCell(columnIndexesMap.get("A2"))));
                apiUserCustomer.setType(UserCustomerType.FARMER);

                apiUserCustomer.setProductTypes(customerProductTypes);

                // Member of associations
                if (!emptyCell(row.getCell(columnIndexesMap.get("A21")))) {
                    String content = getString(row.getCell(columnIndexesMap.get("A21")));
                    if (content != null) {
                        String[] names = content.split(",");
                        List<ApiUserCustomerAssociation> apiUserCustomerAssociationList = new ArrayList<>();
                        for (String name : names) {
                            Company association = companyService.getAssociationByName(name);
                            if (association != null) {
                                ApiUserCustomerAssociation apiUserCustomerAssociation = new ApiUserCustomerAssociation();
                                apiUserCustomerAssociation.setCompany(CompanyMapper.toApiCompanyBase(association));
                                apiUserCustomerAssociation.setUserCustomer(apiUserCustomer);
                                apiUserCustomerAssociationList.add(apiUserCustomerAssociation);
                            }
                        }
                        apiUserCustomer.setAssociations(apiUserCustomerAssociationList);
                    }
                }

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
     * Creates a hashmap for storing column index values.
     * For example, if value pair ("A1", 0) - excel key "A1" is set to column index 0.
     * Main field keys:
     *  A1 - Company internal ID
     *  A2 - Last name
     *  A3 - First name
     *  A4 - Village
     *  A5 - Cell
     *  A6 - Sector
     *  A7 - Caserio
     *  A8 - Aldea
     *  A9 - Municipio
     *  A10 - Departamento
     *  A11 - Address
     *  A12 - City
     *  A13 - State
     *  A14 - Zip
     *  A15 - Country
     *  A16 - Gender
     *  A17 - Phone number
     *  A18 - E-mail
     *  A19 - Smartphone
     *  A20 - Supplier of
     *  A21 - Member of associations
     *  A22 - Area unit
     *  A23 - Total cultivated area
     *  A24 - Organic production (EU)
     *  A25 - Area organic certified
     *  A26 - Start date of transitioning to organic
     *  A27 - Account number
     *  A28 - Account holder's name
     *  A29 - Bank name
     *  A30 - Additional information
     *  Current list keys for product types:
     *  L-PT-1-1 // Area cultivated with plant coffee
     *  L-PT-1-2 // Number of plants (plant coffee)
     *  L-PT-2-1 // Area cultivated with plant macadamia
     *  L-PT-2-2 // Number of plants (plant macadamia)
     *
     * @return Map of column indexes
     */
    private Map<String, Integer> calculcateRowPointers(XSSFSheet xssfSheet) {

        Map<String, Integer> result = new HashMap<>();

        // Mappings are stored in the row with index 3
        int rowIndex = 3;

        Row row = xssfSheet.getRow(rowIndex);

        int colIndex = 0;
        Cell nextCell = row.getCell(colIndex);

        while (!emptyCell(nextCell)) {
            if (nextCell.getStringCellValue() != null) {
                result.put(nextCell.getStringCellValue(), colIndex);
            }
            colIndex++;
            nextCell = row.getCell(colIndex);
        }

        return result;
    }

    /**
     * List of company product types that are given in the spreadsheet.
     * @param companyId - used for retrieving company product types
     * @param language - request language
     * @param columnIndexesMap - used for additional filtering by given excel fields
     * @return - filtered list of product types.
     */
    private List<ApiProductType> readCompanyProductTypes(Long companyId, Language language, Map<String, Integer> columnIndexesMap) {

        ProductType productTypeProxy = companyService.getCompanyProductTypes(companyId, null);

        Set<String> productNamesFilter = new HashSet<>();
        columnIndexesMap.forEach((key, value) -> {

            if (key.contains("-1") || key.contains("-2")) {
                productNamesFilter.add(key.split("-")[0]);
            }

//            switch (key) {
//                case "L-COFFEE-1":
//                    productNamesFilter.add("Coffee");
//                    break;
//                case "L-MACADAMIA-1":
//                    productNamesFilter.add("Macadamia");
//                    break;
//                default:
//            }
        });

        return  Torpedo.select(productTypeProxy).list(em)
                .stream()
                .filter(productType -> productNamesFilter.contains(productType.getFieldName()))
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

    private boolean validRow(Row row, Map<String, Integer> rowPointers) {
        return validCell(row.getCell(rowPointers.get("A1")), List.of(CellType.STRING, CellType.NUMERIC)) &&     // Company internal ID
                validCell(row.getCell(rowPointers.get("A2")), CellType.STRING) &&                               // Last name
                validCell(row.getCell(rowPointers.get("A3")), List.of(CellType.STRING)) &&                      // First name
                validCell(row.getCell(rowPointers.get("A4")), List.of(CellType.STRING)) &&                      // Village
                validCell(row.getCell(rowPointers.get("A5")), List.of(CellType.STRING)) &&                      // Cell
                validCell(row.getCell(rowPointers.get("A6")), List.of(CellType.STRING)) &&                      // Sector
                validCell(row.getCell(rowPointers.get("A7")), List.of(CellType.STRING)) &&                      // Caserio
                validCell(row.getCell(rowPointers.get("A8")), List.of(CellType.STRING)) &&                      // Aldea
                validCell(row.getCell(rowPointers.get("A9")), List.of(CellType.STRING)) &&                      // Municipio
                validCell(row.getCell(rowPointers.get("A10")), List.of(CellType.STRING)) &&                     // Departamento
                validCell(row.getCell(rowPointers.get("A11")), List.of(CellType.STRING)) &&                     // Address
                validCell(row.getCell(rowPointers.get("A12")), List.of(CellType.STRING)) &&                     // City
                validCell(row.getCell(rowPointers.get("A13")), List.of(CellType.STRING)) &&                     // State
                validCell(row.getCell(rowPointers.get("A14")), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Zip
                validCell(row.getCell(rowPointers.get("A15")), CellType.STRING) &&                              // Country
                validCell(row.getCell(rowPointers.get("A16")), CellType.STRING) &&                              // Gender
                validCell(row.getCell(rowPointers.get("A17")), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Phone number
                validCell(row.getCell(rowPointers.get("A18")), List.of(CellType.STRING)) &&                     // E-mail
                validCell(row.getCell(rowPointers.get("A19")), List.of(CellType.STRING)) &&                     // Smartphone
                validCell(row.getCell(rowPointers.get("A20")), List.of(CellType.STRING)) &&                     // Supplier of
                validCell(row.getCell(rowPointers.get("A21")), List.of(CellType.STRING)) &&                     // Member of associations
                validCell(row.getCell(rowPointers.get("A22")), List.of(CellType.STRING)) &&                     // Area unit
                validCell(row.getCell(rowPointers.get("A23")), List.of(CellType.NUMERIC)) &&                    // Total cultivated area
                validCell(row.getCell(rowPointers.get("A24")), List.of(CellType.STRING)) &&                     // Organic production (EU)
                validCell(row.getCell(rowPointers.get("A25")), List.of(CellType.NUMERIC)) &&                    // Area organic certified
                validCell(row.getCell(rowPointers.get("A26")), List.of(CellType.NUMERIC)) &&                    // Start date of transitioning to organic
                validCell(row.getCell(rowPointers.get("A27")), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Account number
                validCell(row.getCell(rowPointers.get("A28")), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Account holder's name
                validCell(row.getCell(rowPointers.get("A29")), List.of(CellType.STRING, CellType.NUMERIC)) &&   // Bank name
                validCell(row.getCell(rowPointers.get("A30")), List.of(CellType.STRING, CellType.NUMERIC));     // Additional information
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
