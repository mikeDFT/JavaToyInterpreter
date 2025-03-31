package Model.Stmt;

import Model.ADTs.IDict;
import Model.MyException;
import Model.PrgState;
import Model.Type.*;
import Model.Value.*;

public class VarDeclStmt implements IStmt {
	public String name;
	IType type;

	public VarDeclStmt(String name, IType type) {
		this.name = name;
		this.type = type;
	}

	public String toString() {
		return type + " " + name;
	}

	public PrgState execute(PrgState state) throws MyException {
		IDict<String, IValue> symTable = state.getSymTable();

		if (symTable.containsKey(name)) {
			throw new MyException("Variable " + name + " already declared");
		}

		symTable.put(name, type.defaultValue());

		return null;
	}

	public IStmt deepCopy() {
		return new VarDeclStmt(name, type.deepCopy());
	}

	public IValue defaultValue() {
		return type.defaultValue();
	}

	public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
		if(typeEnv.containsKey(name))
			throw new MyException("Variable " + name + " already in typeEnv (already declared)");

		typeEnv.put(name, type);
		return typeEnv;
	}
}
