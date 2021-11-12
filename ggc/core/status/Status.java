package ggc.core.status;

import ggc.core.product.Product;

public interface Status {
	void promote(StatusContext statusContext);
	void unpromote(StatusContext statusContext);
	double applyFine(double value, int paymentDate, int deadline, Product product);
	double applyDiscount(double value, int paymentDate, int deadline, Product product);
	String toString();
}
