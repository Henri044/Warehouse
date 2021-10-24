package ggc.core.transaction.sale;

import ggc.core.Date;
import ggc.core.Partner;
import ggc.core.product.Product;
import ggc.core.transaction.sale.Sale;

public class SaleByCredit extends Sale {
    private Date _deadline;
    private double _amountPaid;

    SaleByCredit(int id, Date paymentDate, double baseValue, int quantity, Partner provider, Product product, Date deadline, double amountPaid){
        super(id, paymentDate, baseValue, quantity, provider, product);
        _deadline = deadline;
        _amountPaid = amountPaid;
    }

    public String toString(){
        return ""; //TO DO
    }
}
