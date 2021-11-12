package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.transaction.sale.Sale;

import java.io.Serializable;

public class BreakdownSale extends Sale implements Serializable {

    private int _deadline;
    private double _amountPaid;

    
    public BreakdownSale(int id, double baseValue, int quantity, Partner provider, Product product, int paymentDate){
        super(id, baseValue, quantity, provider, product, paymentDate);
        _deadline = paymentDate;
        _amountPaid = baseValue;
    }

    public double getAllComponentsPrice() {
        double price = 0;
        int i = 0;

        while (this.getProduct().getRecipe().getComponents().size() > i){
            price += (int)(Math.round(this.getProduct().getRecipe().getComponents().get(i).getPrice()*this.getProduct().getRecipe().getQuantities().get(i)*this.getQuantity()));
            i++;
        }

        return price;
    }

    @Override
    public String toString(){
        String recipe = new String();

        for (int i = 0; i < this.getProduct().getRecipe().getComponents().size(); i++) {
            recipe += (this.getProduct().getRecipe().getComponents().get(i).getId() + ":" + this.getProduct().getRecipe().getQuantities().get(i) + ":" + this.getAllComponentsPrice() + "#");
        }

        recipe = recipe.substring(0, recipe.length() - 1);

        return ("DESAGREGAÇÃO|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()*this.getQuantity() - this.getAllComponentsPrice()*this.getQuantity()) + "|" + Math.round(_amountPaid) + "|" + _deadline + "|" + recipe);
    }

    @Override
    public boolean isAcquisition(){
        return false;
    }

    @Override
    public int getDeadline() {
        return _deadline;
    }
}
