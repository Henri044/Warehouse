package ggc.core.status;

import ggc.core.product.Product;
import ggc.core.status.Normal;
import ggc.core.status.Elite;
import ggc.core.status.Status;
import ggc.core.status.StatusContext;

import java.io.Serializable;

public class Selection implements Status, Serializable {
	private static Selection _selectionStatus = new Selection();

	public static Selection getSelectionStatus() {
		return _selectionStatus;
	}

	public void promote(StatusContext statusContext) {
		// PROMOTE TO ELITE
		statusContext.setStatus(Elite.getEliteStatus());
	}

	public void  unpromote(StatusContext statusContext) {
		// UNPROMOTE TO NORMAL 
		statusContext.setStatus(Normal.getNormalStatus());
	}

	public double applyFine(double baseValue, int paymentDate, int deadline, Product product) {

		int N = 0;

		if (product.isSimpleProduct())
			N += 5;
		N += 3;

		if (1 <= paymentDate - deadline && paymentDate - deadline <= N) {
			return baseValue += baseValue*0.05;
		}
		if (paymentDate - deadline > N)
			return baseValue += baseValue*0.1;
		return baseValue;

	}

	public double applyDiscount(double baseValue, int paymentDate, int deadline, Product product) {

		int N = 0;

		if (product.isSimpleProduct())
			N += 5;
		N += 3;

		if (deadline - paymentDate >= N-2)
			return baseValue -= baseValue*0.1;
		if (deadline - paymentDate >= 2)
			return baseValue -= baseValue*0.05;
		return baseValue;

	}

	public String toString() {
		return "SELECTION";
	}
}