package com.techery.dtat.actions;

import com.techery.dtat.ui.components.Alert;

public abstract class AlertActions extends BaseUiActions {
	protected Alert alert = new Alert();
	public void acceptPermissionRequestAlert() {
		/*---Not required on Android---*/
	}
	public void confirmLogout() {
		//TODO: confirm logout here
	}
}
