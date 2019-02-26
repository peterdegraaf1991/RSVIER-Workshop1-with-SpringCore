package view;

public class MainView extends View {

	@Override
	public void printMenuHeader() {
		terminal.println("Main Menu\n");

	}

	@Override
	public void printMenuOptions() {
		terminal.println("1. Account Management");
		terminal.println("2. Orders");
		terminal.println("3. Products");
		terminal.println("4. Customers");
		terminal.println("9. Logout");
	}
}
