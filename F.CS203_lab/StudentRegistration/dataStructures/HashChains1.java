package dataStructures;

// Node class for each key-value pair
class HashNode {
    Object key;
    Object value;
    HashNode next;

    public HashNode(Object key, Object value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}

public class HashChains1 {
    private int divisor;        
    private HashNode[] table;    

    public HashChains1(int divisor) {
        this.divisor = divisor;
        table = new HashNode[divisor];
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode()) % divisor;
    }

    public void put(Object key, Object value) {
        int index = hash(key);
        HashNode head = table[index];

        HashNode current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashNode newNode = new HashNode(key, value);
        newNode.next = head;
        table[index] = newNode;
    }

    public Object get(Object key) {
        int index = hash(key);
        HashNode current = table[index];
        while (current != null) {
            if (current.key.equals(key))
                return current.value;
            current = current.next;
        }
        return null;
    }

    public Object remove(Object key) {
        int index = hash(key);
        HashNode current = table[index];
        HashNode prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev != null)
                    prev.next = current.next;
                else
                    table[index] = current.next;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public void output() {
        for (int i = 0; i < divisor; i++) {
            HashNode current = table[i];
            while (current != null) {
                if (current.value != null) // null бол хэвлэхгүй
                    System.out.println(current.key + " : " + current.value);
                current = current.next;
            }
        }
    }
}