package ggc.core.status;

import ggc.core.status.Status;

public class StatusContext {

	private Status _status;
	private int _points;

	public StatusContext() {
		_status = Normal.getNormalStatus();
		_points = 0;
	}

	public Status getStatus() {
		return _status;
	}

	public String statusToString() {
		return _status.toString();
	}

	public void setStatus(Status s) {
		_status = s;
	}

	public int getPoints() {
		return _points;
	}

	public void addPoints(int value) {
		_points += value;
	}

	public void updateStatus() {
		if (_points <= 2000) {
			setStatus(Normal.getNormalStatus());
		}
		else if (_points > 2000 && _points <= 25000) {
			setStatus(Selection.getSelectionStatus());
		}
		else {
			setStatus(Elite.getEliteStatus());
		}
	}
}