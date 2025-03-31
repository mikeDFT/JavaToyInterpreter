package Model.ADTs;

import Model.MyException;


public class Stack<T> implements IStack<T> {
    java.util.Stack<T> stack;

    public Stack(){
        this.stack = new java.util.Stack<>();
    }

    @Override
    public void push(T element){
        stack.push(element);
    }

    @Override
    public T pop() throws MyException {
        if(stack.isEmpty()){
            throw new MyException("Cannot pop from an empty stack");
        }
        return stack.pop();
    }

    @Override
    public T top() throws MyException {
        if(stack.isEmpty()){
            throw new MyException("Cannot pop from an empty stack");
        }
        return stack.peek();
    }

    @Override
    public boolean empty(){
        return stack.empty();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        for (Object obj : stack) {
            out.insert(0, obj.toString() + "\n");
        }

        // .trim() removes the whitespace before and after the string (removes the new line after it)
        return out.toString().trim(); //Arrays.toString(stack.toArray());
    }

    @Override
    public int size(){
        return stack.size();
    }

    @Override
    public T get(int index) {
        return stack.get(index);
    }
}
