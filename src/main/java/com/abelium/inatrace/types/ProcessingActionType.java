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
 */
public enum ProcessingActionType {
    PROCESSING,
    FINAL_PROCESSING,
    SHIPMENT,
    TRANSFER
}
