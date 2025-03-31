package Model.Stmt;

import Model.ADTs.IDict;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;

public interface IStmt {
	PrgState execute(PrgState state) throws MyException;
	String toString();

	IStmt deepCopy();
	IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException;
}