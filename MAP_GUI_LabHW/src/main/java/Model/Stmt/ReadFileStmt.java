package Model.Stmt;

import Model.ADTs.Dict;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {
	private IExpr expr;
	private String varName;

	public ReadFileStmt(IExpr e, String vn) {
		expr = e;
		varName = vn;
	}

	public String toString(){
		return "ReadFile: " + varName + " = " + expr;
	}

	public PrgState execute(PrgState state) throws MyException {
		IDict<String, IValue> symTable = state.getSymTable();
		if (!(symTable.containsKey(varName))) {
			throw new MyException("Variable '" + varName + "' not found in SymbolTable");
		}

		IValue symVal = symTable.get(varName);
		if (!(symVal.getType() instanceof IntType)) {
			throw new MyException("Variable '" + varName + "' is not an IntType");
		}

		IValue exprVal = expr.eval(state.getSymTable(), state.getHeapTable());
		if (!(exprVal.getType() instanceof StringType)) {
			throw new MyException("Expression not evaluated to StringType");
		}

		String filename = ((StringValue)exprVal).getVal();
		IDict<String, BufferedReader> fileTable = state.getFileTable();
		if (!(fileTable.containsKey(filename))) {
			throw new MyException("File '" + filename + "' not found in fileTable");
		}

		int valueRead;
		try {
			BufferedReader reader = fileTable.get(filename);
			String line = reader.readLine();
			if (line == null)
				valueRead = 0;
			else
				valueRead = Integer.parseInt(line);
		}
		catch (IOException e) {
			throw new MyException("IOException: " + e.getMessage());
		}

		symTable.put(varName, new IntValue(valueRead));

		return null;
	}

	public IStmt deepCopy() {
		return new ReadFileStmt(expr.deepCopy(), varName);
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if(!(expr.typecheck(typeEnv).equals(new StringType())))
			throw new MyException("FileRead: Type check failed, expr not string");

		return typeEnv;
	}
}
