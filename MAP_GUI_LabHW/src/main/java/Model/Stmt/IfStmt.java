package Model.Stmt;

import Model.ADTs.IDict;
import Model.ADTs.IStack;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

class IfStmt implements IStmt {
	public IExpr exp;
	public IStmt thenS;
	public IStmt elseS;
	public IfStmt(IExpr e, IStmt t, IStmt el) {
		exp=e; thenS=t; elseS=el;
	}
	public String toString(){
		return "(IF ("+ exp.toString()+")\nTHEN (" +thenS.toString() +")\nELSE ("+elseS.toString()+"))";
	}
	public PrgState execute(PrgState state) throws MyException {
		IValue res = exp.eval(state.getSymTable(), state.getHeapTable());
		IStack<IStmt> exeStack = state.getExeStack();

		if (res.getType().equals(new BoolType())) {
			if (((BoolValue) res).getVal())
				exeStack.push(thenS);
			else
				exeStack.push(elseS);
		}
		else {
			throw new MyException("(IF) Result not of type Bool");
		}

		return null;
	}

	public IStmt deepCopy() {
		return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if(!(exp.typecheck(typeEnv).equals(new BoolType())))
			throw new MyException("(IF) Expression type mismatch (not bool)");

		thenS.typecheck(typeEnv.copy());
		elseS.typecheck(typeEnv.copy());

		return typeEnv;
	}
}
