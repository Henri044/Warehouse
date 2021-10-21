package ggc.core;

public abstract class Transaction {
    private int _id;
    private Date _paymentDate;
    private double _baseValue;
    private int _quantity;
    private Partner _partner;
    private Product _product;

    public Transaction(){

    }

    public void registerBreakdown(String partnerId, String productId, int quantity){

    }

    public void registerSale(String partnerId, String productId, int maxPaymentDate, int quantity){

    }

    public void registerPurchase(String partnerId, String productId, int price, int quantity){

    }

    public Date getPaymentDate(){

    }

    public boolean isPaid(){
        
    }
}
