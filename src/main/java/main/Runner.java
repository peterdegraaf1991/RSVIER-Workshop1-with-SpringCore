package main;

import controller.DatabaseController;
import controller.LoginController;

public class Runner extends DatabaseController {
	public static int databaseOption;
	
	public static void main(String[] args) {
		
		clearDatabase();
		initDatabase();

		LoginController loginController = new LoginController();
		loginController.runController();
	}
}

