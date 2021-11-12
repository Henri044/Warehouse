package ggc.core.status;

import ggc.core.product.Product;
import ggc.core.status.Normal;
import ggc.core.status.Elite;
import ggc.core.status.Status;
import ggc.core.status.StatusContext;

public class Selection implements Status {
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

	public String toString() {
		return "SELECTION";
	}
}