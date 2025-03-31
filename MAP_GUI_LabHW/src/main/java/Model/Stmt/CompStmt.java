package Model.Stmt;

import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;

public class CompStmt implements IStmt {
	public IStmt first;
	public IStmt second;

	public CompStmt(IStmt v, IStmt v1) {
		first = v;
		second = v1;
	}

	public String toString() {
		 return "("+first.toString() + ";\n" + second.toString()+";)";
	}

	public PrgState execute(PrgState state) throws MyException {
		IStack<IStmt> exeStack = state.getExeStack(); // pop()

		exeStack.push(second);
		exeStack.push(first);

		return null;
	}

	public IStmt deepCopy() {
		return new CompStmt(first.deepCopy(), second.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		return second.typecheck(first.typecheck(typeEnv));
	}
}