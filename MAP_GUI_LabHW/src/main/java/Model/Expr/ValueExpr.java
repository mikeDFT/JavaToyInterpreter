package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.IType;
import Model.Value.IValue;


public class ValueExpr implements IExpr {
    private final IValue val;
    public ValueExpr(IValue v) { val = v; }
    public IValue eval(IDict<String, IValue> tbl, IDict<Integer, IValue> heap) throws MyException { return val; }

    public String toString() { return val.toString(); }

    public IExpr deepCopy() {
        return new ValueExpr(val.deepCopy());
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException {
        return val.getType();
    }
}
