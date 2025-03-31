package Model.Type;

import Model.Value.IValue;
import Model.Value.RefValue;

public class RefType implements IType {
	IType inner;

	public IType getInner() {
		return inner;
	}

	public RefType(IType inner) {
		this.inner = inner;
	}

	@Override
	public boolean equals(Object another) {
		return (another instanceof RefType && inner.equals(((RefType)another).getInner()));
	}

	public String toString() { return "ref(" + inner + ")"; }

	public IType deepCopy() {
		return new RefType(inner.deepCopy());
	}

	public IValue defaultValue() {
		return new RefValue(0, inner);
	}
}
