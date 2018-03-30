package com.pasqualepanuccio.salestaxes.model.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Receipt {

    private Long id;

    private List<ReceiptItem> receiptItemList = new ArrayList<>();

    private BigDecimal salesTaxes;

    private BigDecimal total;

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("Output ");
        builder.append(getId().toString() + ":\n");
        getReceiptItemList().stream().forEach(receiptItem -> builder.append(receiptItem.toString()));
        builder.append("Sales Taxes: " + this.getSalesTaxes());
        builder.append("\nTotal: " + this.getTotal());
        builder.append("\n");
        return builder.toString();
    }
}