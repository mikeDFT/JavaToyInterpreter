package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.IType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class VarExpr implements IExpr {
    private final String id;

    public VarExpr(String v) {
        id = v;
    }

    public String toString() {
        return id;
    }

    public IValue eval(IDict<String, IValue> tbl, IDict<Integer, IValue> heap) throws MyException {
        return tbl.get(id);
    }

    public IExpr deepCopy() {
        return new VarExpr(id);
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException {
        if(!typeEnv.containsKey(id)) {
            throw new MyException("Variable " + id + " not found in typeEnv");
        }

        return typeEnv.get(id);
    }
}
