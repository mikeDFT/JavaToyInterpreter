package View;

import Controller.Controller;
import Model.MyException;

import java.util.Scanner;



public class View_not_used {
	private final Controller controller;
	boolean printFlag = true;

	public View_not_used(Controller controller) {
		this.controller = controller;
	}

	static public void printMenu() {
		System.out.println(
				"""
				Menu:
				[nr]. Run program with number [nr]
				on. Set print flag on
				off. Set print flag off
				e. Exit
				"""
		);
	}

	public void runProgram(int progIndex) {
		controller.allSteps();
	}

	public void startProgram() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			printMenu();

			try {
				String choice = scanner.next();
				switch (choice) {
					case "on":
						printFlag = true;
						break;
					case "off":
						printFlag = false;
						break;
					case "e":
						return;
					default:
						runProgram(Integer.parseInt(choice));
						break;
				}
			}
			catch(MyException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
