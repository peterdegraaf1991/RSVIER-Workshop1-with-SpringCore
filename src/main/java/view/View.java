package view;

import java.util.concurrent.TimeUnit;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class View {
	public TextIO textIO = TextIoFactory.getTextIO();
	TextTerminal<?> terminal = textIO.getTextTerminal();

	public int requestMenuOption() {
		int option = textIO.newIntInputReader().read(
				"Choose an option from the menu");
		return option;
	}

	public void invalidInput() {
		terminal.println("Your input is invalid");
	}

	public void clearTerminal() {
		terminal.printf("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}

	public void noPermission() {
		terminal.println("You have no permission for this action");
	}

	public void printMenuOptions() {
	}

	public void printMenuHeader() {
	}

	public void requestContinue() {
		String option = textIO.newStringInputReader()
				.withMinLength(0)
				.read("\nPress any key to go back to the menu");
	}

	public void logoutTimer() {
		try {
			terminal.println("Logging out in 3");
			TimeUnit.SECONDS.sleep(1);
			terminal.println("Logging out in 2");
			TimeUnit.SECONDS.sleep(1);
			terminal.println("Logging out in 1");
			TimeUnit.SECONDS.sleep(1);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void printString(String string) {
		terminal.println(string);
	}
}
