package ggc.core.transaction.sale;

import ggc.core.product.Product;
import ggc.core.Partner;
import ggc.core.Date;
import ggc.core.transaction.sale.Sale;

import java.io.Serializable;

public class BreakdownSale extends Sale implements Serializable {

    private int _deadline;

    
    public BreakdownSale(int id, double baseValue, int quantity, Partner provider, Product product, int paymentDate){
        super(id, baseValue, quantity, provider, product, paymentDate);
        _deadline = paymentDate;
    }

    public double getComponentPrice(Product component) {
        double price = 0;
        int i = 0;

        price += (this.getProduct().getRecipe().getComponents().get(i).getPrice()*this.getProduct().getRecipe().getQuantities().get(i)*this.getQuantity());
        i++;
        return price;
    }

    @Override
    public String toString(){
        String recipe = new String();
        double allComponentsPrice = 0;

        for (int i = 0; i < this.getProduct().getRecipe().getComponents().size(); i++) {

            recipe += (this.getProduct().getRecipe().getComponents().get(i).getId() + ":" 
            + this.getProduct().getRecipe().getQuantities().get(i)*this.getQuantity() + ":" 
            + (int)Math.round(this.getComponentPrice(this.getProduct().getRecipe().getComponents().get(i))) + "#");

            allComponentsPrice += (int)(Math.round(this.getComponentPrice(this.getProduct().getRecipe().getComponents().get(i))));
        }

        recipe = recipe.substring(0, recipe.length() - 1);

        if ((this.getBaseValue()*this.getQuantity() - allComponentsPrice) > 0) {
            return ("DESAGREGAÇÃO|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()*this.getQuantity() - allComponentsPrice) + "|" + Math.round(this.getBaseValue()*this.getQuantity() - allComponentsPrice) + "|"
            + _deadline + "|" + recipe);
        }

        else {
            return ("DESAGREGAÇÃO|" + this.getId() + "|" + this.getPartner().getId() + "|" + this.getProduct().getId() + "|" + this.getQuantity() + "|"
            + Math.round(this.getBaseValue()*this.getQuantity() - allComponentsPrice) + "|" + 0 + "|" + _deadline + "|" + recipe);
        }
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
