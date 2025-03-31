package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

public class IntValue implements IValue {
    private int val;

    public IntValue(int v){
        val=v;
    }

    public IntValue(){
        val = ((IntValue)getType().defaultValue()).getVal();
    }

    public int getVal() {
        return val;
    }
    public void setVal(int v) {
        val = v;
    }

    public String toString() {
        return ""+val;
    }

    public IType getType() {
        return new IntType();
    }

    public IValue deepCopy() {
        return new IntValue(val);
    }

    public boolean equals(Object other) {
        return (other instanceof IntValue && ((IntValue)other).val == this.val);
    }
}
