package Model.Stmt;

import Model.ADTs.Dict;
import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.ADTs.Stack;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;

public class ForkStmt implements IStmt {
    public IStmt stmt;

    public ForkStmt(IStmt s) {
        stmt = s;
    }

    public String toString() {
        return "fork("+stmt.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
	    return new PrgState(
	        new Stack<>(),
	        state.getSymTable().copy(),
	        state.getOut(),
	        state.getFileTable(),
	        state.getHeapTable(),
	        stmt
	    );
    }

    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		stmt.typecheck(typeEnv.copy());
		return typeEnv;
	}
}