package ggc.core.transaction.sale;

import ggc.core.Date;
import ggc.core.Partner;
import ggc.core.product.Product;

public class SaleByCredit {
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(Product product, int quantity, Partner provider, int deadline){

    }

    public String toString(){
        return ""; //TO DO
    }
}
