package com.pasqualepanuccio.salestaxes.service;

import com.pasqualepanuccio.salestaxes.model.cart.Cart;
import com.pasqualepanuccio.salestaxes.model.receipt.Receipt;
import com.pasqualepanuccio.salestaxes.model.receipt.ReceiptItem;
import com.pasqualepanuccio.salestaxes.service.strategy.TaxesCalculatorStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Log4j2
@Getter
@Setter
public class PrintReceiptService implements ReceiptService {

    public static BiFunction<ReceiptItem, TaxesCalculatorStrategy, ReceiptItem> updateReceiptItemWithTaxedPrices = (receiptItem, taxesCalculatorStrategy) -> {

        log.debug("Receipt Item to update {}", receiptItem.toString());

        ReceiptItem ri = receiptItem;
        ri.calculateTotalPrice(taxesCalculatorStrategy);
        ri.calculateSalesTaxes(taxesCalculatorStrategy);

        log.debug("Updated Receipt Item {}", ri.toString());

        return ri;
    };
    private TaxesCalculatorStrategy taxesCalculatorStrategy;

    public PrintReceiptService(TaxesCalculatorStrategy taxesCalculatorStrategy) {
        this.taxesCalculatorStrategy = taxesCalculatorStrategy;
    }

    @Override
    public Receipt checkout(Cart cart) {

        // transform Cart item to Receipt item and calculate taxes for each
        List<ReceiptItem> receiptItems = cart.getCartItems().stream()
                .map(Cart.mapCartItemToReceiptItem)
                .map(ri -> updateReceiptItemWithTaxedPrices.apply(ri, taxesCalculatorStrategy))
                .collect(Collectors.toList());

        // sum of taxes
        BigDecimal salesTaxes = receiptItems.stream()
                .map(ri -> ri.getSalesTaxes())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // sum of total price including taxes
        BigDecimal total = receiptItems.stream()
                .map(ri -> ri.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Receipt(cart.getId(), receiptItems, salesTaxes, total);
    }


    @Override
    public void printReceipt(Receipt receipt) {
        log.info(receipt.toString());
    }


}
