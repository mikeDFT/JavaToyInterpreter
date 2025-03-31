package Model.Stmt;

import Model.ADTs.IDict;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFileStmt implements IStmt {
	private IExpr expr;

	public OpenRFileStmt(IExpr e) {
		expr = e;
	}

	public String toString(){
		return "OpenRFile: " + expr;
	}

	public PrgState execute(PrgState state) throws MyException {
		IValue val = expr.eval(state.getSymTable(), state.getHeapTable());
		if (!(val.getType() instanceof StringType)) {
			throw new MyException("Expression not evaluated to StringType");
		}

		String fileName = ((StringValue)val).getVal();

		IDict<String, BufferedReader> fileTable = state.getFileTable();
		if(fileTable.containsKey(fileName)) {
			throw new MyException("Filename " + fileName + " is already in fileTable");
		}

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileName));
		}
		catch(FileNotFoundException e) {
			throw new MyException("FileNotFoundException: " + e.getMessage());
		}

		fileTable.put(fileName, reader);

		return null;
	}

	public IStmt deepCopy() {
		return new OpenRFileStmt(expr.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if(!(expr.typecheck(typeEnv).equals(new StringType())))
			throw new MyException("FileOpen: Type check failed, expr not string");

		return typeEnv;
	}
}
