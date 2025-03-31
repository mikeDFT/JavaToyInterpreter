package Model.Stmt;

import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt {
	private IExpr expr;

	public CloseRFileStmt(IExpr e) {
		expr = e;
	}

	public String toString() {
		return "CloseRFile: " + expr;
	}

	public PrgState execute(PrgState state) throws MyException {
		IValue val = expr.eval(state.getSymTable(), state.getHeapTable());
		if (!(val.getType() instanceof StringType)) {
			throw new MyException("Expression not evaluated to StringType");
		}

		String fileName = ((StringValue)val).getVal();

		IDict<String, BufferedReader> fileTable = state.getFileTable();
		if(!(fileTable.containsKey(fileName))) {
			throw new MyException("Filename " + fileName + " is not in fileTable");
		}

		try {
			BufferedReader reader = fileTable.get(fileName);
			reader.close();
		}
		catch(IOException e) {
			throw new MyException("IOException: " + e.getMessage());
		}

		fileTable.remove(fileName);

		return null;
	}

	public IStmt deepCopy() {
		return new CloseRFileStmt(expr.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if(!(expr.typecheck(typeEnv).equals(new StringType())))
			throw new MyException("FileClose: Type check failed, expr not string");

		return typeEnv;
	}
}
