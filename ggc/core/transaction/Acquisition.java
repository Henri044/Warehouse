package ggc.core.transaction;

import ggc.core.Partner;
import ggc.core.product.Product;
import ggc.core.Date;
import ggc.core.transaction.Transaction;

public class Acquisition extends Transaction{

    Acquisition(int id, Date paymentDate, double baseValue, int quantity, Partner provider, Product product){
        super(id, paymentDate, baseValue, quantity, provider, product);
    }

    public String toString(){
        return ""; //TO DO
    }
}
