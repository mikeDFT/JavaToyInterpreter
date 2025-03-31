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

public class WhileStmt implements IStmt {
    public IExpr exp;
    public IStmt s;
    public WhileStmt(IExpr e, IStmt s) {
        exp=e; this.s=s;
    }
    public String toString(){
        return "WHILE ("+ exp.toString()+")\nDO (" +s.toString() +")";
    }

    public PrgState execute(PrgState state) throws MyException {
        IValue res = exp.eval(state.getSymTable(), state.getHeapTable());

        IStack<IStmt> exeStack = state.getExeStack();
        if (res.getType().equals(new BoolType())) {
            if (((BoolValue) res).getVal()) {
                exeStack.push(this);
                exeStack.push(s);
            }
        }
        else {
            throw new MyException("(WHILE) Result not of type Bool");
        }

        return null;
    }

    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), s.deepCopy());
    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        if(!(exp.typecheck(typeEnv).equals(new BoolType())))
            throw new MyException("(IF) Expression type mismatch (not bool)");

        s.typecheck(typeEnv.copy());

        return typeEnv;
    }
}
