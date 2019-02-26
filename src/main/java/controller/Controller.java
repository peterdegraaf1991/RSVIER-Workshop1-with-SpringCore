package controller;

import view.View;

public abstract class Controller {
	public abstract void runController();

	View view = new View();

	static boolean newView = true;

	public boolean isAdmin() {
		if (LoginController.loggedInAccount.getAccountTypeId() == 3)
			return true;
		else
			return false;
	}

	public boolean workerOrAdminPermission() {
		if (LoginController.loggedInAccount.getAccountTypeId() == (2 | 3)) {
			return true;
		} else {
			view.noPermission();
			return false;
		}
	}

	public boolean adminPermission() {
		if (LoginController.loggedInAccount.getAccountTypeId() == (3)) {
			return true;
		} 
		else {
			view.noPermission();
			return false;
		}
	}

	public void requestNewMenu() {
		view.requestContinue();
		newView = true;
	}
}
