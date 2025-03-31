package interpreter.map_gui_labhw;


public class Interpreter {
	public static void main(String[] args) {
		WindowManager windowManager = new WindowManager();
		windowManager.main();
	}
}

//public class Interpreter extends Application {
//	public void start(Stage stage) throws IOException {
//		FXMLLoader fxmlLoader = new FXMLLoader(com.example.mapguilabhw.Interpreter.class.getResource("abc.fxml"));
//		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//		stage.setTitle("Hello!");
//		stage.setScene(scene);
//		stage.show();
//		TextMenu menu = new TextMenu();
//		menu.addCommand(new ExitCommand("0", "exit"));
//
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("Input logFilePath: ");
//		String logFilePath = scanner.nextLine();
//
//		int i = 1;
//		for(IStmt stmt: HardcodedPrograms.hardcodedPrograms) {
//			// type checker
//			IDict<String, IType> typeEnv = new Dict<>();
//			try {
//				stmt.typecheck(typeEnv);
//			}
//			catch (MyException e) {
//				System.out.println("Type checker failed for stmt " + Integer.toString(i) + ": " + e.getMessage());
//				i++;
//				continue;
//			}
//
//			IStack<IStmt> exeStack = new Stack<>();
//			IDict<String, IValue> symTable = new Dict<>();
//			IList<IValue> out = new List<>();
//			IDict<String, BufferedReader> fileTable = new Dict<>();
//			IDict<Integer, IValue> heap = new HeapDict();
//
//			PrgState prgState = new PrgState(exeStack, symTable, out, fileTable, heap, stmt);
//
//			IRepo repo = new Repo(prgState, logFilePath);
//
//			Controller controller = new Controller(repo);
//
//			menu.addCommand(new RunExample(Integer.toString(i), "Statement " + i, controller));
//			i += 1;
//		}
//
//		menu.show();
//	}
//
//	public static void main(String[] args) {
//		launch();
//	}
//}