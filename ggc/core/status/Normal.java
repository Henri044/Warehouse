package ggc.core.status;

import ggc.core.product.Product;
import ggc.core.status.Selection;
import ggc.core.status.Elite;
import ggc.core.status.Status;
import ggc.core.status.StatusContext;

public class Normal implements Status {
	private static Normal _normalStatus = new Normal();

	public static Normal getNormalStatus() {
		return _normalStatus;
	}

	public void promote(StatusContext statusContext) {
		// PROMOTE TO SELECTION
		statusContext.setStatus(Selection.getSelectionStatus());
	}

	public void unpromote(StatusContext statusContext) {
		return;
	}

	/*
	public double applyFine(double value, int paymentDate, int deadline, Product product) {
		int N = 0;

		if (product.isSimpleProduct())
			N = 5;
		else
			N = 3;

		if ((paymentDate - deadline) >= 0 && (paymentDate - deadline) <= N)
			return value += value*0.05;
		else if ((paymentDate - deadline) > N)
			return value += value*0.1;
		return value;
	}

	public double applyDiscount(double value, int paymentDate, int deadline, Product product) {
		int N = 0;

		if (product.isSimpleProduct())
			N = 5;
		else
			N = 3;

		if (deadline - paymentDate >= N)
			return value -= value*0.1;
		if (deadline - paymentDate >= 0 && deadline - paymentDate < N)
			return value -= value*0.1;
		return value;
	}
	*/

	public String toString() {
		return "NORMAL";
	}
}