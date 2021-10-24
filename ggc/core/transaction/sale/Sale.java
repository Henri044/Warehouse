package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.transaction.Transaction;

public abstract class Sale extends Transaction{
    
    Sale(int id, Date paymentDate, double baseValue, int quantity, Partner provider, Product product){
        super(id, paymentDate, baseValue, quantity, provider, product);
    }
}
