package Model.Expr;

import Model.ADTs.IDict;
import Model.MyException;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;


public class RelExpr implements IExpr {
	private IExpr e1;
	private IExpr e2;
	private String op;

	public RelExpr(IExpr e1, IExpr e2, String op) {
		this.e1 = e1;
		this.e2 = e2;
		this.op = op;
	}

	public String toString() {
		return "("+e1+" "+op+" "+e2+")";
	}

	public IValue eval(IDict<String, IValue> tbl, IDict<Integer, IValue> heap) throws MyException {
		System.out.println("RelExpr eval");
		IValue v1, v2;
		v1 = e1.eval(tbl, heap);
		v2 = e2.eval(tbl, heap);

		if (v1.getType() instanceof IntType && v2.getType() instanceof IntType) {
			IntValue i1 = (IntValue) v1;
			IntValue i2 = (IntValue) v2;

			int n1, n2;
			n1 = i1.getVal();
			n2 = i2.getVal();

			if (op.equals("<")) return new BoolValue(n1 < n2);
			if (op.equals("<=")) return new BoolValue(n1 <= n2);
			if (op.equals(">")) return new BoolValue(n1 > n2);
			if (op.equals(">=")) return new BoolValue(n1 >= n2);
			if (op.equals("==")) return new BoolValue(n1 == n2);
			if (op.equals("!=")) return new BoolValue(n1 != n2);
		}
		else
			throw new MyException("first or second operand is not an integer");

		return new IntValue(-1000000000);
	}

	public IExpr deepCopy() {
		return new RelExpr(e1.deepCopy(), e2.deepCopy(), op);
	}

	public IType typecheck(IDict<String, IType> typeEnv) throws MyException {
		IType t1 = e1.typecheck(typeEnv);
		IType t2 = e2.typecheck(typeEnv);

		if(!(t1.equals(new IntType())))
			throw new MyException("first operand is not an int");

		if(!(t2.equals(new IntType())))
			throw new MyException("second operand is not an int");

		if(!(t1.equals(t2)))
			throw new MyException("type mismatch: " + e1.toString() + " vs " + e2.toString());

		return new BoolType();
	}
}
