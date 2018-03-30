package com.pasqualepanuccio.salestaxes;

import com.pasqualepanuccio.salestaxes.model.cart.Cart;
import com.pasqualepanuccio.salestaxes.model.cart.CartItem;
import com.pasqualepanuccio.salestaxes.model.good.Good;
import com.pasqualepanuccio.salestaxes.model.good.GoodCategory;
import com.pasqualepanuccio.salestaxes.model.receipt.ReceiptItem;
import com.pasqualepanuccio.salestaxes.service.PrintReceiptService;
import com.pasqualepanuccio.salestaxes.service.ReceiptService;
import com.pasqualepanuccio.salestaxes.service.strategy.NormalTaxesCalculatorStrategy;
import com.pasqualepanuccio.salestaxes.service.strategy.TaxesCalculatorStrategy;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class SalesTaxesProblemTest {

    @Test
    public void bookItemShouldNotBeSubjectToBasicTaxes() {
        Good book = new Good(GoodCategory.BOOK, "book", new BigDecimal("12.49"), false);
        TaxesCalculatorStrategy taxStrategy = new NormalTaxesCalculatorStrategy("10", "5");
        ReceiptItem bookItem = new ReceiptItem(book, new BigDecimal("1"));

        Assert.assertNotNull(bookItem);
        Assert.assertFalse("The book item should not be subject to basic taxes", bookItem.getGood().getCategory().isSubjectToBasicTax());
    }

    @Test
    public void normalTaxesStrategyShouldBeCorrect() {
        Good musicCd = new Good(GoodCategory.GENERIC, "music CD", new BigDecimal("14.99"), false);
        TaxesCalculatorStrategy taxStrategy = new NormalTaxesCalculatorStrategy("10", "5");
        ReceiptItem bookItem = new ReceiptItem(musicCd, new BigDecimal("1"));

        Assert.assertEquals("The normal taxes should be calulated correctly", bookItem.calculateTotalPrice(taxStrategy), new BigDecimal("16.49"));
    }

    @Test
    public void taxesShouldBeRoundUp() {
        BigDecimal toRound = new BigDecimal("12.54");

        BigDecimal rounded = NormalTaxesCalculatorStrategy.roundingOff.apply(toRound);
        Assert.assertEquals(rounded, new BigDecimal("12.55"));

        toRound=new BigDecimal("58.98");
        rounded=NormalTaxesCalculatorStrategy.roundingOff.apply(toRound);
        Assert.assertEquals(rounded,new BigDecimal("59.00"));
    }

    @Test
    public void testReceipt() {
        log.info("INPUT:");
        log.info("\n");

        List<CartItem> input = Arrays.asList(new CartItem(new Good(GoodCategory.BOOK, "book", new BigDecimal("12.49"), false), new BigDecimal("2")),
                new CartItem(new Good(GoodCategory.GENERIC, "music CD", new BigDecimal("14.99"), false), new BigDecimal("1")),
                new CartItem(new Good(GoodCategory.FOOD, "chocolate bar", new BigDecimal("0.85"), false), new BigDecimal("1")));

        Cart cart1 = new Cart(1l, input);
        log.info(cart1.toString());

        List<CartItem> input2 = Arrays.asList(new CartItem(new Good(GoodCategory.FOOD, "box of chocolates", new BigDecimal("10.00"), true), new BigDecimal("1")),
                new CartItem(new Good(GoodCategory.GENERIC, "bottle of perfume", new BigDecimal("47.50"), true), new BigDecimal("1")));

        Cart cart2 = new Cart(2l, input2);
        log.info(cart2.toString());

        List<CartItem> input3 = Arrays.asList(new CartItem(new Good(GoodCategory.GENERIC, "bottle of perfume", new BigDecimal("27.99"), true), new BigDecimal("1")),
                new CartItem(new Good(GoodCategory.GENERIC, "bottle of perfume", new BigDecimal("18.99"), false), new BigDecimal("1")),
                new CartItem(new Good(GoodCategory.MEDICAL, "packet of headache pills", new BigDecimal("9.75"), false), new BigDecimal("1")),
                new CartItem(new Good(GoodCategory.FOOD, "box of chocolates", new BigDecimal("11.25"), true), new BigDecimal("3")));

        Cart cart3 = new Cart(3l, input3);
        log.info(cart3.toString());

        log.info("OUTPUT:");
        log.info("\n");

        ReceiptService service = new PrintReceiptService(new NormalTaxesCalculatorStrategy("10", "5"));

        service.printReceipt(service.checkout(cart1));

        service.printReceipt(service.checkout(cart2));

        service.printReceipt(service.checkout(cart3));
    }

}
