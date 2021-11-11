package ggc.core.comparators;

import java.util.Comparator;

import ggc.core.product.Product;

public class ProductsComparator implements Comparator<Product> {

    // Compare and sort the products by their ID's
    public int compare(Product p1, Product p2){
        return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
    }
}