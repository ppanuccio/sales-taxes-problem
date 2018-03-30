package com.pasqualepanuccio.salestaxes.service.strategy;

import com.pasqualepanuccio.salestaxes.model.receipt.ReceiptItem;

import java.math.BigDecimal;

public interface TaxesCalculatorStrategy {

    BigDecimal calculateTotalPrice(ReceiptItem item);

    BigDecimal calculateSalesTaxes(ReceiptItem item);
}
