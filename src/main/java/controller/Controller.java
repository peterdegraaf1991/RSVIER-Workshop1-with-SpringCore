package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import view.View;

@Component
public abstract class Controller {
	public abstract void runController();

	@Autowired
	View view;

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
