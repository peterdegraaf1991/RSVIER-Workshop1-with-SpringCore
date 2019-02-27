package view;

import org.springframework.stereotype.Component;

import dao.DaoFactory;

@Component
public class LoginView extends View {

	public void printMenuHeader() {

		terminal.println(" _       _         _                                   ");
		terminal.println("( )  _  ( )       (_ )                                 ");
		terminal.println("| | ( ) | |   __   | |    ___    _     ___ ___     __  ");
		terminal.println("| | | | | | / __ \\ | |  / ___) / _ \\ /  _   _  \\ / __ \\");
		terminal.println("| (_/ \\_) |(  ___/ | | ( (___ ( (_) )| ( ) ( ) |(  ___/");
		terminal.println(" \\_______/  \\____)(___) \\____) \\___/ (_) (_) (_) \\____)\n\n\n");
	}

	public void printMenuOptions() {
		terminal.println("1. Login");
		terminal.println("9. Exit Application" + "\n");
	}

	public String requestInputUsername() {
		String username = textIO.newStringInputReader()
				.withDefaultValue("AdminAccount@email.com")
				.read("Enter Username");
		return username;
	}

	public String requestInputPassword() {
		String password = null;
		do {
			password = textIO.newStringInputReader().withMinLength(6)
					.withInputMasking(true).withDefaultValue("AdminPassword")
					.read("Enter Password");
			if (!passwordIsValid(password))
				passwordNotValid();
		} while (!passwordIsValid(password));
		return password;
	}

	private void passwordNotValid() {
		terminal.println("The entered password doesnt match the password pattern. Atleast one capital is required.\nPlease try again.\n");
	}

	public void incorrectEmailOrPassword() {
		terminal.println("The combination of email and password is incorrect");
	}

	public void loginSuccesfull() {
		terminal.println("Login Succesfull");
	}

	public void unknownUsername() {
		terminal.println("This username doesn't exist");
	}

	public boolean passwordIsValid(String password) {
		String emailPattern = "(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{1,}";
		boolean result = password.matches(emailPattern);
		return result;
	}

	public void useSQLOrMongo() {
		int input = textIO.newIntInputReader().withDefaultValue(1)
				.withInlinePossibleValues(1, 2)
				.read("Do you wish to use SQL(1) or Mongo(2) as database?");
		new DaoFactory(input);
	}
}
