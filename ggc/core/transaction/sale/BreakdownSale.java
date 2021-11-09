package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.transaction.sale.Sale;

import java.io.Serializable;

public class BreakdownSale extends Sale implements Serializable {
    
    BreakdownSale(int id, double baseValue, int quantity, Partner provider, Product product, int paymentDate){
        super(id, baseValue, quantity, provider, product, paymentDate);
    }

    public String toString(){
        return ""; // TO DO
    }

    @Override
    public boolean isAcquisition(){
        return false;
    }
}
