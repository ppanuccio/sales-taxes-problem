package com.pasqualepanuccio.salestaxes.service.strategy;

import com.pasqualepanuccio.salestaxes.model.receipt.ReceiptItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.function.Function;

public class NormalTaxesCalculatorStrategy implements TaxesCalculatorStrategy {

    private static final Logger log = LogManager.getLogger();
    private static final BigDecimal roundUpRate_0_05 = new BigDecimal("0.05");

    public static Function<BigDecimal, BigDecimal> roundingOff = valueToRound -> {

        log.debug("valueToRound  {} ", valueToRound);

        BigDecimal roundedValue = roundUpRate_0_05.signum() == 0 ? valueToRound : valueToRound.divide(roundUpRate_0_05, 0, BigDecimal.ROUND_UP).multiply(roundUpRate_0_05);

        log.debug("roundeValue  {} ", roundedValue);

        return roundedValue;

    };
    private BigDecimal basicTaxRate = BigDecimal.ZERO;
    private BigDecimal importTaxRate = BigDecimal.ZERO;


    public NormalTaxesCalculatorStrategy(String basicTaxRate, String importTaxRate) {
        this.basicTaxRate = new BigDecimal(basicTaxRate);
        this.importTaxRate = new BigDecimal(importTaxRate);
    }

    @Override
    public BigDecimal calculateTotalPrice(ReceiptItem item) {
        return (item.getGood().getUnityPrice().add(getTaxesFromGoodItem(item))).multiply(item.getQuantity());
    }


    @Override
    public BigDecimal calculateSalesTaxes(ReceiptItem item) {
        return getTaxesFromGoodItem(item).multiply(item.getQuantity());
    }

    private BigDecimal getTaxesFromGoodItem(ReceiptItem item) {
        BigDecimal itemPrice = item.getGood().getUnityPrice();
        BigDecimal salesTaxes = BigDecimal.ZERO;
        if (item.getGood().getCategory().isSubjectToBasicTax()) {
            salesTaxes = roundingOff.apply((itemPrice.multiply(basicTaxRate)).divide(new BigDecimal("100")));
        }
        if (item.getGood().isImported()) {
            salesTaxes = salesTaxes.add(roundingOff.apply((itemPrice.multiply(importTaxRate)).divide(new BigDecimal("100"))));
        }
        return salesTaxes;
    }
}