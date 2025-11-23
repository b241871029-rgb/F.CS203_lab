package dataStructures;

import java.util.LinkedList;

public class HashChains {
	private int divisor;
	private LinkedList[] table;
	private int size;

	public HashChains(int theDivisor) {
		this.divisor = theDivisor;
		table = new LinkedList[divisor];
		for (int i = 0; i < divisor; i++)
			table[i] = new LinkedList();
		size = 0;
	}

	private int hash(Object key) {
		int hashCode = key.hashCode();
		return Math.abs(hashCode) % divisor;
	}

	public boolean search(Object theKey) {
		int index = hash(theKey);
		for (Object obj : table[index]) {
			Entry entry = (Entry) obj;
			if (entry.key.equals(theKey))
				return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public Object get(Object theKey) {
		int index = hash(theKey);
		for (Object obj : table[index]) {
			Entry entry = (Entry) obj;
			if (entry.key.equals(theKey))
				return entry.element;
		}
		return null;
	}

	public Object put(Object theKey, Object theElement) {
		int index = hash(theKey);
		for (Object obj : table[index]) {
			Entry entry = (Entry) obj;
			if (entry.key.equals(theKey)) {
				Object oldElement = entry.element;
				entry.element = theElement;
				return oldElement;
			}
		}
		table[index].add(new Entry(theKey, theElement));
		size++;
		return null;
	}

	public Object remove(Object theKey) {
		int index = hash(theKey);
		for (Object obj : table[index]) {
			Entry entry = (Entry) obj;
			if (entry.key.equals(theKey)) {
				table[index].remove(entry);
				size--;
				return entry.element;
			}
		}
		return null;
	}

	public void output() {
		for (int i = 0; i < divisor; i++) {
			System.out.print(i + ": ");
			for (Object obj : table[i]) {
				Entry entry = (Entry) obj;
				System.out.print("(" + entry.key + ", " + entry.element + ") ");
			}
			System.out.println();
		}
	}

	public Object updateElement(Object theKey, Object theElement) {
		Object old = get(theKey);
		if (old != null) {
			put(theKey, theElement);
			return old;
		}
		return null;
	}

	public Object updateKey(Object theKey, Object theNewKey) {
		Object elementToUpdate = get(theKey);
		if (elementToUpdate != null) {
			remove(theKey);
			put(theNewKey, elementToUpdate);
			return elementToUpdate;
		}
		return null;
	}

	public void delete(Object theKey) {
		Object removed = remove(theKey);
		if (removed != null)
			System.out.println("Deleted: " + theKey + " → " + removed);
		else
			System.out.println("Key not found: " + theKey);
	}

	public static void main(String[] args) {
		HashChains h = new HashChains(11);
		h.put("null", 0);
		h.put("one", 1);
		h.put("two", 2);
		h.put("three", 3);
		h.put("four", 4);
		h.put("five", 5);
		h.put("six", 6);
		h.put("seven", 7);
		h.put("eight", 8);
		h.put("nine", 9);
		h.put("ten", 10);
		h.put("eleven", 11);
		h.output();
	}

	private static class Entry {
		Object key;
		Object element;

		Entry(Object key, Object element) {
			this.key = key;
			this.element = element;
		}
	}
}
