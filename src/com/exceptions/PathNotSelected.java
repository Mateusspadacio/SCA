package com.exceptions;

import com.alerts.AlertsHandler;

public class PathNotSelected extends Exception {

	private static final long serialVersionUID = 1L;

	public PathNotSelected(String msg) {
		AlertsHandler.alert(msg);
	}

}
