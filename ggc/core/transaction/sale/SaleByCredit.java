package ggc.core.transaction.sale;

import ggc.core.Partner;
import ggc.core.product.Product;
import ggc.core.transaction.sale.Sale;

import java.io.Serializable;

public class SaleByCredit extends Sale implements Serializable {
    private int _deadline;
    private double _amountPaid;
    private boolean _isPaid;

    // SALE BY CREDIT WITHOUT INTEREST
    public SaleByCredit(int id, double baseValue, int quantity, int paymentDate, Partner provider, Product product){
        super(id, baseValue, quantity, provider, product, paymentDate);
        _deadline = paymentDate;
        _amountPaid = baseValue;
        _isPaid = true;
    }

    // SALE BY CREDIT WITH INTEREST 
    public SaleByCredit(int id, double baseValue, int quantity, Partner provider, Product product, int deadline) {
        super(id, baseValue, quantity, provider, product, 0);
        _deadline = deadline;
        _amountPaid = baseValue;
        _isPaid = false;
    }

    //VENDA|id|idParceiro|idProduto|quantidade|valor-base|valor-a-pagamento|data-limite|data-pagamento
    public String toString() {
        if (!this.isPaid()) {
            return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()) + "|" + Math.round(_amountPaid) + "|" + _deadline;
        }
        return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()) + "|" + Math.round(_amountPaid) + "|" + _deadline + "|" + this.getPaymentDate();
    }

    public void toggleIsPaid() {
        _isPaid = true;
    }

    public void setAmountPaid(double value) {
        _amountPaid = value;
    }

    @Override
    public boolean isAcquisition(){
        return false;
    }

    @Override
    public int getDeadline() {
        return _deadline;
    }

    @Override
    public boolean isPaid() {
        return _isPaid;
    }

    @Override
    public boolean isSaleByCredit(){
        return true;
    }
}
