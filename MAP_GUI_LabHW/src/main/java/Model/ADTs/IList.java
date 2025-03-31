package Model.ADTs;

import java.util.List;

public interface IList<T> extends Iterable<T>{
    void add(T element);
    T get(int index);
    List<T> getAll();
    void remove(int index);
    int size();
    String toString();
    void clear();
    boolean isEmpty();
}