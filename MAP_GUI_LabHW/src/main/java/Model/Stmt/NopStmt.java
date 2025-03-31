package Model.Stmt;

import Model.ADTs.IDict;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;

class NopStmt implements IStmt {
	public NopStmt() {}

	public String toString() {
		return "[NopStmt]";
	}

	public PrgState execute(PrgState state) throws MyException {
		return null;
	}

	public IStmt deepCopy() {
		return new NopStmt();
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		return typeEnv;
	}

}
