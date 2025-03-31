package View.Commands;

import Controller.Controller;
import Model.MyException;

public class RunExample extends Command {
	private final Controller controller;

	public RunExample(String key, String desc, Controller ctr) {
		super(key, desc);
		controller = ctr;
	}

	@Override
	public void execute() {
		try {
			controller.allSteps();
		} catch (MyException e) {
			System.out.println(e.getMessage());
		}
	}
}