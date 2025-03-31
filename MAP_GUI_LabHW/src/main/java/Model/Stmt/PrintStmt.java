package Model.Stmt;

import Model.ADTs.IDict;
import Model.ADTs.IList;
import Model.Expr.IExpr;
import Model.Expr.VarExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStmt implements IStmt {
	public IExpr exp;

	public PrintStmt(IExpr v) {
		exp = v;
	}

	public String toString() {
		return "print(" + exp.toString() + ")";
	}

	public PrgState execute(PrgState state) throws MyException {
		IList<IValue> out = state.getOut();
		out.add(exp.eval(state.getSymTable(), state.getHeapTable()));

		return null;
	}

	public IStmt deepCopy() {
		return new PrintStmt(exp.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		exp.typecheck(typeEnv);
		return typeEnv;
	}
}