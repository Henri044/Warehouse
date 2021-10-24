package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.transaction.sale.Sale;

public class BreakdownSale extends Sale {
    
    BreakdownSale(int id, Date paymentDate, double baseValue, int quantity, Partner provider, Product product){
        super(id, paymentDate, baseValue, quantity, provider, product);
    }

    public String toString(){
        return ""; // TO DO
    }
}
