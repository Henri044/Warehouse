package ggc.core.transaction;

import ggc.core.Partner;
import ggc.core.product.Product;
import ggc.core.Date;
import ggc.core.transaction.Transaction;

import java.io.Serializable;

public class Acquisition extends Transaction implements Serializable {

    public Acquisition(int id, int paymentDate, double baseValue, int quantity, Partner provider, Product product){
        super(id, baseValue, quantity, provider, product, paymentDate);
    }

    @Override
    public String toString(){
        return ("COMPRA|" + getId() + "|" + getPartner().getId() + "|" + getProduct().getId() + "|" +
        getQuantity() + "|" + Math.round(getPrice()) + "|" + getPaymentDate());
    }

    @Override
    public boolean isAcquisition(){
        return true;
    }
}
