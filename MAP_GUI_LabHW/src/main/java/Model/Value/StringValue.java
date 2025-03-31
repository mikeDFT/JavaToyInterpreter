package Model.Value;

import Model.Type.StringType;
import Model.Type.IType;

public class StringValue implements IValue {
	private String val;

	public StringValue(String v){
		val=v;
	}

	public StringValue(){
		val = ((StringValue)getType().defaultValue()).getVal();
	}

	public String getVal() {
		return val;
	}
	public void setVal(String v) {
		val = v;
	}

	public String toString() {
		return "\"" + val + "\"";
	}

	public IType getType() {
		return new StringType();
	}

	public IValue deepCopy() {
		return new StringValue(val);
	}

	public boolean equals(Object other) {
		return (other instanceof StringValue && ((StringValue)other).val.equals(this.val));
	}
}
