package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.util.Objects;

public class LogicExpr implements IExpr {
    private IExpr e1;
    private IExpr e2;
    private String op;

    public LogicExpr(IExpr e1, IExpr e2, String op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    public String toString() {
        return e1 + " " + op + " " + e2;
    }

    public IValue eval(IDict<String, IValue> tbl, IDict<Integer, IValue> heap) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, heap);
        v2 = e2.eval(tbl, heap);
        if (v1.getType() instanceof BoolType && v2.getType() instanceof BoolType) {
            BoolValue b1 = (BoolValue) v1;
            BoolValue b2 = (BoolValue) v2;
            if(op.equals("and"))
                return new BoolValue(b1.getVal() && b2.getVal());
            else if(op.equals("or"))
                return new BoolValue(b1.getVal() || b2.getVal());
        }
        else
            throw new MyException("Operands are not BoolType");

        return new BoolValue(false);
    }

    public IExpr deepCopy() {
        return new LogicExpr(e1.deepCopy(), e2.deepCopy(), op);
    }

    public IType typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType t1 = e1.typecheck(typeEnv);
        IType t2 = e2.typecheck(typeEnv);

        if(!(t1.equals(new BoolType())))
            throw new MyException("first operand is not a bool");

        if(!(t2.equals(new BoolType())))
            throw new MyException("second operand is not a bool");

        if(!(t1.equals(t2)))
            throw new MyException("type mismatch: " + e1.toString() + " vs " + e2.toString());

        return new BoolType();
    }
}
