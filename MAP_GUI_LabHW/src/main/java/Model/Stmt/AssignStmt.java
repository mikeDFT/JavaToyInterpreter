package Model.Stmt;

import Model.ADTs.*;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStmt implements IStmt{
	public String id;
	public IExpr exp;

	public AssignStmt(String id, IExpr exp){
		this.id = id;
		this.exp = exp;
	}

	public String toString(){ return id+" = "+ exp.toString();}

	@Override
	public PrgState execute(PrgState state) throws MyException {
		IDict<String, IValue> symTable= state.getSymTable();

		if (symTable.containsKey(id)) {
			IValue val = exp.eval(symTable, state.getHeapTable());
			IType typId = (symTable.get(id)).getType();
			if (val.getType().equals(typId)) { // try with == as well
				symTable.put(id, val);
			}
	        else throw new MyException("declared type of variable "+id+" and type of the assigned expression do not match");
		}
        else throw new MyException("the used variable " + id + " was not declared before");
		return null;
	}

	public IStmt deepCopy() {
		return new AssignStmt(id, exp.deepCopy());
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if (!(typeEnv.containsKey(id)))
			throw new MyException("variable " + id + " not added to typeEnv (not declared)");

		IType tVar = typeEnv.get(id);
		IType tExpr = exp.typecheck(typeEnv);
		if (!(tVar.equals(tExpr)))
			throw new MyException("variable " + id + " not of type expression: " + tExpr);

		return typeEnv;
	}
}