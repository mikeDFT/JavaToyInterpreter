package Model;

import Model.ADTs.Dict;
import Model.ADTs.*;
import Model.Stmt.IStmt;
import Model.Stmt.VarDeclStmt;
import Model.Value.IValue;
import Model.Value.RefValue;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {
	private IStack<IStmt> exeStack;
	private IDict<String, IValue> symTable;
	private IList<IValue> out;
	private IDict<String, BufferedReader> fileTable;
	private IDict<Integer, IValue> heapTable;
	private final IStmt prgStatement;
	static public AtomicInteger static_id = new AtomicInteger(0);
	private final int id;

	public int getId() {
		return id;
	}

	public PrgState(IStack<IStmt> s, IDict<String, IValue> d, IList<IValue> l,
	                IDict<String, BufferedReader> ft, IDict<Integer, IValue> ht, IStmt prg) throws MyException {
		exeStack = s;
		symTable = d;
		out = l;
		fileTable = ft;
		heapTable = ht;

		prgStatement = prg;
		id = static_id.incrementAndGet();

		exeStack.push(prg.deepCopy());
	}

	public PrgState oneStep() {
		IStmt stmt = exeStack.pop();
		return stmt.execute(this);
	}

	public boolean isNotCompleted () {
		return !exeStack.empty();
	}

	public IStack<IStmt> getExeStack() {
		return exeStack;
	}

	public IDict<String, IValue> getSymTable() {
		return symTable;
	}

	public IList<IValue> getOut() {
		return out;
	}

	public IDict<String, BufferedReader> getFileTable() {
		return fileTable;
	}

	public IDict<Integer, IValue> getHeapTable() {
		return heapTable;
	}

	public void resetPrgState() {
		exeStack = new Stack<>();
		symTable = new Dict<>();
		out = new List<>();
		fileTable = new Dict<>();
		heapTable = new HeapDict();

		exeStack.push(prgStatement.deepCopy());
	}

	public String toString() {
		return "PrgState: " + id;
	}
}
