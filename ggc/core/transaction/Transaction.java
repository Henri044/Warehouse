package ggc.core.transaction;

import ggc.core.Date;
import ggc.core.Partner;
import ggc.core.product.Product;

public abstract class Transaction {
    private int _id;
    private Date _paymentDate;
    double _baseValue;
    private int _quantity;
    private Partner _partner;
    private Product _product;

    public Transaction(int id, Date paymentDate, double baseValue, int quantity, Partner partner, Product product) {
        _id = id;
        _paymentDate = paymentDate;
        _baseValue = baseValue;
        _quantity = quantity;
        _partner = partner;
        _product = product;
    }

    public void registerBreakdown(String partnerId, String productId, int quantity){
        return; //TO DO
    }

    public void registerSale(String partnerId, String productId, int maxPaymentDate, int quantity){
        return; //TO DO
    }

    public void registerPurchase(String partnerId, String productId, int price, int quantity){
        return; //TO DO
    }

    public Date getPaymentDate(){
        return _paymentDate;
    }

    public boolean isPaid(){
        return false; //TO DO
    }
}
