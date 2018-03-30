package com.pasqualepanuccio.salestaxes.model.receipt;

import com.pasqualepanuccio.salestaxes.model.good.Good;
import com.pasqualepanuccio.salestaxes.service.strategy.TaxesCalculatorStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReceiptItem {

    private Good good;

    private BigDecimal quantity = BigDecimal.ZERO;

    private BigDecimal salesTaxes = BigDecimal.ZERO;

    private BigDecimal totalPrice = BigDecimal.ZERO;

    public ReceiptItem(Good good, BigDecimal quantity) {
        this.good = good;
        this.quantity = quantity;
    }

    public BigDecimal calculateSalesTaxes(TaxesCalculatorStrategy taxesCalculatorStrategy) {
        salesTaxes = taxesCalculatorStrategy.calculateSalesTaxes(this);
        return salesTaxes;
    }

    public BigDecimal calculateTotalPrice(TaxesCalculatorStrategy taxesCalculatorStrategy) {
        totalPrice = taxesCalculatorStrategy.calculateTotalPrice(this);
        return totalPrice;
    }

    @Override
    public String toString() {
        return new StringBuilder(new StringBuffer("")
                .append(this.quantity)
                .append(good.isImported() ? " imported " : " ")
                .append(good.getName())
                .append(": "))
                .append(this.getTotalPrice())
                .append("\n").toString();
    }
}
