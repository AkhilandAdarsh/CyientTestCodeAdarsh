
import java.util.ArrayList;
import java.util.List;

public class HashTable {
	private static final int INITIAL_CAPACITY = 16;
	private static final double LOAD_FACTOR_THRESHOLD = 0.7;

	private List<KeyValue>[] table;
	private int size;

	public HashTable() {
		this.table = new List[INITIAL_CAPACITY];
		this.size = 0;
	}

	private int hash(String key) {
		// A simple hash function that returns the index for the key
		return key.hashCode() % table.length;
	}

	private void resize() {
		int newCapacity = table.length * 2;
		List<KeyValue>[] newTable = new List[newCapacity];

		for (List<KeyValue> bucket : table) {
			if (bucket != null) {
				for (KeyValue entry : bucket) {
					int newIndex = hash(entry.key);
					if (newTable[newIndex] == null) {
						newTable[newIndex] = new ArrayList<>();
					}
					newTable[newIndex].add(entry);
				}
			}
		}

		table = newTable;
	}

	public void put(String key, String value) {
		int index = hash(key);

		if (table[index] == null) {
			table[index] = new ArrayList<>();
		}

		for (KeyValue entry : table[index]) {
			if (entry.key.equals(key)) {
				entry.value = value;
				return;
			}
		}

		table[index].add(new KeyValue(key, value));
		size++;

		if ((double) size / table.length > LOAD_FACTOR_THRESHOLD) {
			resize();
		}
	}

	public String get(String key) {
		int index = hash(key);

		if (table[index] != null) {
			for (KeyValue entry : table[index]) {
				if (entry.key.equals(key)) {
					return entry.value;
				}
			}
		}

		return null; // Key not found
	}

	public void remove(String key) {
		int index = hash(key);

		if (table[index] != null) {
			List<KeyValue> bucket = table[index];
			for (int i = 0; i < bucket.size(); i++) {
				if (bucket.get(i).key.equals(key)) {
					bucket.remove(i);
					size--;
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		HashTable ht = new HashTable();
		ht.put("apple", "red");
		ht.put("banana", "yellow");
		ht.put("cherry", "red");
		ht.put("date", "brown");

		System.out.println("Lookup 'banana': " + ht.get("banana"));
		System.out.println("Lookup 'grape': " + ht.get("grape"));

		ht.remove("banana");
		System.out.println("Lookup 'banana' after removal: " + ht.get("banana"));
	}

}

