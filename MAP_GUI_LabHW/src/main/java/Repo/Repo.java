package Repo;

import Model.HardcodedPrograms;
import Model.MyException;
import Model.PrgState;
import Model.ADTs.*; // there will be more PrgStates
import Model.Stmt.IStmt;
import Model.Value.IValue;

import java.io.*;


public class Repo implements IRepo {
	private java.util.List<PrgState> prgStates;
	private PrintWriter logFile;
	private String logFilePath;
	private PrgState initPrgState;

	public void log(String message) {
		logFile.println(message);
		logFile.flush();
	}

	public Repo(PrgState prgS, String logFPath) {
		initPrgState = prgS;

		prgStates = new java.util.ArrayList<>();
		prgStates.add(prgS);

		logFilePath = logFPath;

		try {
			logFile = new PrintWriter(new BufferedWriter(new FileWriter("files\\" + logFilePath, true)));
			logFile.flush();
		}
		catch (IOException exception) {
			throw new MyException("IOException: " + exception.getMessage());
		}
	}

	@Override
	public java.util.List<PrgState> getPrgList() throws MyException {
		return prgStates;
	}

	@Override
	public void resetPrgStates() {
		prgStates.clear();
		initPrgState.resetPrgState();
		prgStates.add(initPrgState);
	}

	@Override
	public void setPrgList(java.util.List<PrgState> prgStates) {
		this.prgStates = prgStates;
	}

	public void updatePrgList(java.util.List<PrgState> prgList) {
		for (PrgState prgState: prgList) {
			if (!prgStates.contains(prgState))
				prgStates.add(prgState);
		}
	}

	public String getLogString(PrgState state) {
		StringBuilder logString = new StringBuilder();

		if(!state.isNotCompleted())
			logString.append("< Completed >\n");

		logString.append("--[[ ProgramState ID: " + state.getId() + " ]]--\n");
		logString.append("ExeStack:\n");
		IStack<IStmt> exeStack = state.getExeStack();
		if (!exeStack.empty())
			logString.append(exeStack + "\n");

		logString.append("\nSymbolTable:\n");
		IDict<String, IValue> symTable = state.getSymTable();
		for (String varName: symTable.keySet()) {
			IValue symVal = symTable.get(varName);
			logString.append(symVal.getType().toString() + " " + varName + " = " + symVal + "\n");
		}

		logString.append("\nOutput:\n");
		IList<IValue> out = state.getOut();
		if (!out.isEmpty())
			logString.append(state.getOut() + "\n");

		logString.append("\nFileTable:\n");
		IDict<String, BufferedReader> fileTable = state.getFileTable();
		for (String filename: fileTable.keySet()) {
			logString.append(filename + "\n");
		}

		logString.append("\nHeapTable:\n");
		IDict<Integer, IValue> heapTable = state.getHeapTable();
		for (Integer addr: heapTable.keySet()) {
			logString.append(addr + " " + heapTable.get(addr) + "\n");
		}

		return logString.toString();
	}

	public void logPrgStateExec(PrgState state) throws MyException {
		logFile.println("--[[ ProgramState ID: " + state.getId() + " ]]--");
		logFile.println("ExeStack:");
		IStack<IStmt> exeStack = state.getExeStack();
		if (!exeStack.empty())
			logFile.println(exeStack);

		logFile.println("\nSymbolTable:");
		IDict<String, IValue> symTable = state.getSymTable();
		for (String varName: symTable.keySet()) {
			IValue symVal = symTable.get(varName);
			logFile.println(symVal.getType().toString() + " " + varName + " = " + symVal);
		}

		logFile.println("\nOutput:");
		IList<IValue> out = state.getOut();
		if (!out.isEmpty())
			logFile.println(state.getOut());

		logFile.println("\nFileTable:");
		IDict<String, BufferedReader> fileTable = state.getFileTable();
		for (String filename: fileTable.keySet()) {
			logFile.println(filename);
		}

		logFile.println("\nHeapTable:");
		IDict<Integer, IValue> heapTable = state.getHeapTable();
		for (Integer addr: heapTable.keySet()) {
			logFile.println(addr + " " + heapTable.get(addr));
		}

		logFile.println("\n");
		logFile.flush();
	}

	public void writeFinishedExecInFile() {
		logFile.println("--[[ Finished execution ]]--\n");
		logFile.flush();
	}
}
