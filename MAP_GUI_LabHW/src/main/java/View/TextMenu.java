package View;

import Model.ADTs.Dict;
import Model.ADTs.IDict;
import View.Commands.Command;

import java.util.Scanner;

public class TextMenu {
	private final IDict<String, Command> commands;

	public TextMenu() {
		commands = new Dict<>();
	}

	public void addCommand(Command c) {
		commands.put(c.getKey(), c);
	}

	private void printMenu() {
		for (Command com : commands.getContent().values()) {
			String line = String.format("%s : %s", com.getKey(), com.getDescription());
			System.out.println(line);
		}
	}

	public void show() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printMenu();
			System.out.println("Input the option: ");

			String key = scanner.nextLine();
			Command com = commands.get(key);

			if (com == null) {
				System.out.println("Invalid Option");
				continue;
			}

			com.execute();
		}
	}
}