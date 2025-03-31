package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.RefType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.RefValue;

public class HeapReadExpr implements IExpr {
	private IExpr e;

	public HeapReadExpr(IExpr e) {
		this.e = e;
	}

	public String toString() {
		return "rH(" + e.toString() + ")";
	}

	public IValue eval(IDict<String, IValue> tbl, IDict<Integer, IValue> heap) throws MyException {
		IValue v;
		v = e.eval(tbl, heap);
		if (v.getType() instanceof RefType) {
			RefValue r = (RefValue) v;

			int addr = r.getAddr();
			if(!heap.containsKey(addr)) {
				throw new MyException("Heap doesn't contain " + addr);
			}

			return heap.get(addr);
		}
		else
			throw new MyException("RH: Operand is not RefType");
	}

	public IExpr deepCopy() {
		return new HeapReadExpr(e.deepCopy());
	}

	public IType typecheck(IDict<String, IType> typeEnv) throws MyException {
		IType type = e.typecheck(typeEnv);
		if (!(type instanceof RefType)) {
			throw new MyException("RH: Operand is not RefType");
		}

		return ((RefType)type).getInner();
	}
}
