package Model.Type;

import Model.Value.IValue;
import Model.Value.IntValue;

public class IntType implements IType {
    @Override
    public boolean equals(Object another){
        return (another instanceof IntType);
    }

    public String toString() { return "int";}

    public IType deepCopy() {
        return new IntType();
    }

    public IValue defaultValue() {
        return new IntValue(0);
    }
}
