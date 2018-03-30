package com.pasqualepanuccio.salestaxes.model.cart;

import com.pasqualepanuccio.salestaxes.model.good.Good;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {

    private Good good;

    private BigDecimal quantity;

    @Override
    public String toString() {
        return new StringBuilder("").append(quantity).append(good.toString()).toString();
    }

}
