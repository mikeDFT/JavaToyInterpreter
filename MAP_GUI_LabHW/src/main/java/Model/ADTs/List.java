package Model.ADTs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class List<T> implements IList<T>{
    final java.util.List<T> list;

    public List() {
        this.list = new ArrayList<>();
    }

    public List(java.util.List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T element) {
        synchronized (list) {
            list.add(element);
        }
    }

    @Override
    public T get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    @Override
    public java.util.List<T> getAll() {
        return list;
    }

    @Override
    public void remove(int index) {
        synchronized (list) {
            list.remove(index);
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (T element : list) {
            out.append(element.toString()).append("\n");
        }

        return out.toString().trim();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return list.spliterator();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}