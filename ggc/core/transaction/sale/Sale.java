package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.transaction.Transaction;

import java.io.Serializable;

public abstract class Sale extends Transaction implements Serializable {
    
    public Sale(int id, double baseValue, int quantity, Partner provider, Product product, int paymentDate){
        super(id, baseValue, quantity, provider, product, paymentDate);
    }

    public abstract String toString();

    public abstract boolean isAcquisition();

    public abstract int getDeadline();
}
