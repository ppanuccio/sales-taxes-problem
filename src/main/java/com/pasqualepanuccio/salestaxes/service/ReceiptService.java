package com.pasqualepanuccio.salestaxes.service;

import com.pasqualepanuccio.salestaxes.model.cart.Cart;
import com.pasqualepanuccio.salestaxes.model.receipt.Receipt;

public interface ReceiptService {

    Receipt checkout(Cart cart);

    void printReceipt(Receipt receipt);

}
