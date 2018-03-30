package com.pasqualepanuccio.salestaxes.model.good;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Good {

    private GoodCategory category;

    private String name;

    private BigDecimal unityPrice;

    private boolean imported;

    @Override
    public String toString() {
        return new StringBuilder(this.imported ? " imported " : " ").append(name).append(" at ").append(unityPrice).toString();
    }
}
