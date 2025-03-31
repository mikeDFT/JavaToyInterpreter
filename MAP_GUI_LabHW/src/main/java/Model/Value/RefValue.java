package Model.Value;

import Model.Type.IType;
import Model.Type.RefType;

public class RefValue implements IValue {
	int addr;
	IType type;

	public RefValue(int addr, IType type) {
		this.addr = addr;
		this.type = type;
	}

	public int getAddr() {
		return addr;
	}

	public void setAddr(int addr) {
		this.addr = addr;
	}

	@Override
	public IType getType() {
		return new RefType(type);
	}

	@Override
	public IValue deepCopy() {
		return new RefValue(addr, type.deepCopy());
	}

	public String toString() {
		return "(" + addr + ", " + type.toString() + ")";
	}

	public boolean equals(Object other) {
		return (other instanceof RefValue && ((RefValue)other).addr == this.addr);
	}
}
