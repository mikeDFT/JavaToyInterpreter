package Model.ADTs;

import Model.MyException;

import java.util.Iterator;

public interface IStack<T> {
    void push(T element);
    T pop() throws MyException;
    T top() throws MyException;
    boolean empty();
    int size();
    T get(int index);
}
