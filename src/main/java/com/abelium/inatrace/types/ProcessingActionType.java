package com.abelium.inatrace.types;

/**
 * Enum that holds the possible types of ation that Processing action can execute.
 *
 * PROCESSING - Processing a semi-product. We always have an input semi-product and a processed output semi-product.
 *
 * FINAL_PROCESSING - Processing an input semi-product into a final product. For example rosted coffe beans into packages for end customers.
 *
 * SHIPMENT - Also know as Quote action - Represent an Quote order placed between companies in the value chain (Product stakeholders).
 * Can be also used for transfer final products between facilities. In this case, there is a field in ProcessingAction entity
 * which specifeis that action is for final product.
 *
 * TRANSFER - Transfer a semi-product from one facility into onther. This is inside one company. Can be also used for transfer
 * final products between facilities. In this case, there is a field in ProcessingAction entity which specifeis that action is for final product.
 *
 * GENERATE_QR_CODE - Special processing action which is used for generating QR code tag that's used to tag a stock order representing some quantity of selected semi-product.
 * The input and output semi-product is the same in this case.
 */
public enum ProcessingActionType {
    PROCESSING,
    FINAL_PROCESSING,
    SHIPMENT,
    TRANSFER,
    GENERATE_QR_CODE
}
