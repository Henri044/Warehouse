package ggc.core.comparators;

import java.util.Comparator;

import ggc.core.product.Product;
import ggc.core.Batch;

public class BatchComparator implements Comparator<Batch> {

    // Compare and sort the batches by their product ID's, after partner ID's,
    // after price and finally quantity
    @Override
    public int compare(Batch b1, Batch b2) {
        int compareProductId = b1.getProduct().getId().toLowerCase().compareTo(b2.getProduct().getId().toLowerCase());
        int comparePartnerId = b1.getProvider().getId().toLowerCase().compareTo(b2.getProvider().getId().toLowerCase());
        int comparePrice = b1.getPrice().compareTo(b2.getPrice());
        int compareQuantity = b1.getStock().compareTo(b2.getStock());

        if (compareProductId != 0) {
            return compareProductId;
        }
        else if (comparePartnerId != 0) {
            return comparePartnerId;
        }
        else if (comparePrice != 0) {
            return comparePrice;
        }
        else {
            return compareQuantity;
        }
    }
}