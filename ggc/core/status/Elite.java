package ggc.core.status;

import ggc.core.product.Product;
import ggc.core.status.Normal;
import ggc.core.status.Selection;
import ggc.core.status.Status;
import ggc.core.status.StatusContext;

import java.io.Serializable;

public class Elite implements Status, Serializable {
	private static Elite _eliteStatus = new Elite();

	public static Elite getEliteStatus() {
		return _eliteStatus;
	}

	public void promote(StatusContext statusContext) {
		return;
	}

	public void unpromote(StatusContext statusContext) {
		// UNPROMOTE TO SELECTION
		statusContext.setStatus(Selection.getSelectionStatus());
		return;
	}

	public double applyFine(double baseValue, int paymentDate, int deadline, Product product) {

		return baseValue;

	}

	public double applyDiscount(double baseValue, int paymentDate, int deadline, Product product) {

		int N = 0;

		if (product.isSimpleProduct())
			N += 5;
		else
			N += 3;

		if (deadline - paymentDate >= N)
			return baseValue -= baseValue*0.1;
		if (deadline - paymentDate >= 0 && deadline - paymentDate < N)
			return baseValue -= baseValue*0.1;
		if (paymentDate - deadline > 0 && paymentDate - deadline <= N)
			return baseValue -= baseValue*0.05;
		return baseValue;

	}

	public String toString() {
		return "ELITE";
	}
}
