package Model.Stmt;

import Model.ADTs.HeapDict;
import Model.ADTs.IDict;
import Model.Expr.IExpr;
import Model.MyException;
import Model.PrgState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapNewStmt implements IStmt {
    private String var_name;
    private IExpr e;

    public HeapNewStmt(String var_name, IExpr e) {
        this.var_name = var_name;
        this.e = e;
    }

    public String toString() {
        return "new(" + var_name + ", " + e.toString() + ")";
    }

    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> tbl = state.getSymTable();
        HeapDict heap = (HeapDict)state.getHeapTable();

        if(!tbl.containsKey(var_name)) {
            throw new MyException("HeapNew: Variable " + var_name + " not declared in SymbolTable");
        }

        IValue val = tbl.get(var_name);
        if (!(val instanceof RefValue)) {
            throw new MyException("HeapNew: Variable " + var_name + " is not a RefValue");
        }

        // check type
        IValue toChange = e.eval(tbl, heap);
        if (!toChange.getType().equals(((RefType)val.getType()).getInner())) {
            throw new MyException("HeapNew: Variable " + var_name + " is not of the same type as the expression\n" +
                    "Supposed to be of type: " + ((RefType)val.getType()).getInner() + "\n" +
                    "Was of type: " + toChange.getType());
        }


//        int addr = ((RefValue) val).getAddr();
//        if(heap.containsKey(addr)) {
//            throw new MyException("HeapNew: Address " + addr + " already declared in Heap ( for var '" + var_name + "')");
//        }

        int newAddr = heap.allocate(val);
        heap.put(newAddr, toChange);

        tbl.put(var_name, new RefValue(newAddr, ((RefType)val.getType()).getInner()));

        return null;
    }

    public IStmt deepCopy() {
        return new HeapNewStmt(var_name, e.deepCopy());
    }

    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType tVar = typeEnv.get(var_name);
        IType tExpr = e.typecheck(typeEnv);

        if(!(tVar instanceof RefType)) {
            throw new MyException("HeapNew: Variable " + var_name + " is not a RefType");
        }

        if(!(((RefType)tVar).getInner().equals(tExpr))) {
            throw new MyException("HeapNew: Variable " + var_name + " of type " + tVar + " cannot contain type " + tExpr);
        }

        return typeEnv;
    }
}