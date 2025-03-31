package Model.Stmt;

import Model.ADTs.IDict;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapWriteStmt implements IStmt {
    private IExpr e;
    private String var_name;

    public HeapWriteStmt(String var_name, IExpr e) {
        this.e = e;
        this.var_name = var_name;
    }

    public String toString() {
        return "wH(" + var_name + ", " + e.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> tbl = state.getSymTable();
        IDict<Integer, IValue> heap = state.getHeapTable();

        if(!tbl.containsKey(var_name)) {
            throw new MyException("Variable '" + var_name + "' not declared in SymbolTable");
        }

        IValue val = tbl.get(var_name);
        if (!(val instanceof RefValue)) {
            throw new MyException("Variable '" + var_name + "' is not a RefValue");
        }

        int addr = ((RefValue) val).getAddr();
        if(!(heap.containsKey(addr))) {
            throw new MyException("Address " + addr + " with var name " + var_name + " not declared in Heap");
        }

        IValue toChange = e.eval(tbl, heap);
        IValue heapVal = heap.get(addr);
        if (!toChange.getType().equals(heapVal.getType())) {
            throw new MyException("Variable " + var_name + " of type " + heapVal.getType()
                    + " can't get values of type " + toChange.getType());
        }

        heap.put(addr, toChange);

        return null;
    }

    public IStmt deepCopy() {
        return new HeapWriteStmt(var_name, e.deepCopy());
    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType tVar = typeEnv.get(var_name);
        IType tExpr = e.typecheck(typeEnv);

        if(!(tVar instanceof RefType)) {
            throw new MyException("HeapWrite: Variable " + var_name + " is not a RefType");
        }

        if(!(((RefType)tVar).getInner().equals(tExpr))) {
            throw new MyException("HeapWrite: Variable " + var_name + " of type " + tVar + " cannot contain type " + tExpr);
        }

        return typeEnv;
    }
}