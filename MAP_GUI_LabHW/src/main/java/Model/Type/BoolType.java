package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;

public class BoolType implements IType {
    @Override
    public boolean equals(Object another){
        return (another instanceof BoolType);
    }

    public String toString() { return "bool";}

    public IType deepCopy() {
        return new BoolType();
    }

    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
