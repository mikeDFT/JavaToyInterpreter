package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue {
    private boolean val;

    public BoolValue(boolean v){
        val=v;
    }

    public BoolValue(){
        val = ((BoolValue)getType().defaultValue()).getVal();
    }

    public boolean getVal() {
        return val;
    }
    public void setVal(boolean v) {
        val = v;
    }

    public String toString() {
        return ""+val;
    }

    public IType getType() {
        return new BoolType();
    }

    public IValue deepCopy() {
        return new BoolValue(val);
    }

    public boolean equals(Object other) {
        return (other instanceof BoolValue && ((BoolValue)other).val == this.val);
    }
}
