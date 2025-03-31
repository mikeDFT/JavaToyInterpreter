package Model.ADTs;

import Model.Value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HeapDict implements IDict<Integer, IValue> {
	private final ConcurrentHashMap<Integer, IValue> dictionary;
	Integer firstEmptyAddr;

	public int allocate(IValue value) {
		this.put(firstEmptyAddr, value);
		firstEmptyAddr += 1;
		return firstEmptyAddr - 1;
	}

	public HeapDict() {
		dictionary = new ConcurrentHashMap<>();
		firstEmptyAddr = 1;
	}

	public String toString() {
		StringBuilder out = new StringBuilder();
		for (Integer key : dictionary.keySet()) {
			out.append(key.toString() + " = " + dictionary.get(key).toString() + "\n");
		}
		return out.toString().trim();
	}

	@Override
	public void put(Integer key, IValue value){
		synchronized (dictionary) {
			dictionary.put(key, value);
		}
	}

	@Override
	public IValue get(Integer key){
		synchronized (dictionary) {
			return dictionary.get(key);
		}
	}

	@Override
	public boolean containsKey(Integer id) {
		synchronized (dictionary) {
			return dictionary.containsKey(id);
		}
	}

	@Override
	public Set<Integer> keySet() {
		synchronized (dictionary) {
			return dictionary.keySet();
		}
	}

	@Override
	public void remove(Integer key) {
		synchronized (dictionary) {
			dictionary.remove(key);
		}
	}

	@Override
	public Map<Integer, IValue> getContent() {
		synchronized (dictionary) {
			return dictionary;
		}
	}

	@Override
	public IDict<Integer, IValue> copy() {
		IDict<Integer, IValue> toReturn = new Dict<>();
		for (Integer key : keySet())
			toReturn.put(key, get(key));
		return toReturn;
	}

	public void setContent(Map<Integer, IValue> map) {
		synchronized (dictionary) {
			dictionary.clear();
			dictionary.putAll(map);
		}
	}

	public Set<Map.Entry<Integer, IValue>> entrySet() {
		return dictionary.entrySet();
	}
}