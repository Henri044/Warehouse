package ggc.core.transaction.sale;

import ggc.core.Partner;
import ggc.core.product.Product;
import ggc.core.transaction.sale.Sale;

import java.io.Serializable;

public class SaleByCredit extends Sale implements Serializable {
    private int _deadline;
    private double _amountPaid;

    // SALE BY CREDIT WITHOUT INTEREST
    public SaleByCredit(int id, double baseValue, int quantity, int paymentDate, Partner provider, Product product){
        super(id, baseValue, quantity, provider, product, paymentDate);
        _deadline = paymentDate;
        _amountPaid = baseValue;
    }

    // SALE BY CREDIT WITH INTEREST 
    public SaleByCredit(int id, double baseValue, int quantity, Partner provider, Product product, int deadline) {
        super(id, baseValue, quantity, provider, product, 0);
        _deadline = deadline;
        _amountPaid = baseValue;
    }

    //VENDA|id|idParceiro|idProduto|quantidade|valor-base|valor-a-pagamento|data-limite|data-pagamento
    public String toString() {
        if (!this.isPaid()) {
            return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()) + "|" + Math.round(_amountPaid) + "|" + _deadline;
        }
        return "VENDA|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()) + "|" + Math.round(_amountPaid) + "|" + this.getPaymentDate() + "|" + _deadline;
    }
}
