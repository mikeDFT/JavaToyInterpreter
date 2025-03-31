package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpr {
    IValue eval(IDict<String,IValue> tbl, IDict<Integer, IValue> heap) throws MyException;
    String toString();
    IExpr deepCopy();
    IType typecheck(IDict<String, IType> typeEnv) throws MyException;
}
