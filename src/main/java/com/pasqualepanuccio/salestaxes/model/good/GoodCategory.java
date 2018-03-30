package com.pasqualepanuccio.salestaxes.model.good;

public enum GoodCategory {
    BOOK(false),
    FOOD(false),
    MEDICAL(false),
    GENERIC(true);

    private final boolean isSubjectToBasicTax;

    GoodCategory(boolean isSubjectToBasicTax) {
        this.isSubjectToBasicTax = isSubjectToBasicTax;
    }

    public boolean isSubjectToBasicTax() {
        return this.isSubjectToBasicTax;
    }
}
