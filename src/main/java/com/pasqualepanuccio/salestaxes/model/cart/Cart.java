package com.pasqualepanuccio.salestaxes.model.cart;

import com.pasqualepanuccio.salestaxes.model.receipt.ReceiptItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@Log4j2
public class Cart {

    public static Function<CartItem, ReceiptItem> mapCartItemToReceiptItem = cartItem -> {

        log.debug("Cart Item to transform {}", cartItem.toString());

        ReceiptItem ri = new ReceiptItem(cartItem.getGood(), cartItem.getQuantity());

        log.debug("Transformed receipt Item {}", ri.toString());

        return ri;
    };
    private Long id;
    private List<CartItem> cartItems = new ArrayList<CartItem>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Input " + getId() + ":\n");
        cartItems.forEach(ci -> sb.append(ci.toString() + "\n"));
        return sb.toString();
    }

}
