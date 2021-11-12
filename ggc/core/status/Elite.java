package ggc.core.status;

import ggc.core.product.Product;
import ggc.core.status.Normal;
import ggc.core.status.Selection;
import ggc.core.status.Status;
import ggc.core.status.StatusContext;

public class Elite implements Status {
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

	public String toString() {
		return "ELITE";
	}
}
