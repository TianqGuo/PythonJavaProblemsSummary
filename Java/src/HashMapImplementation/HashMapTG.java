/**
HashMap implementation Main class, available apis are:

put, get, remove, containsKey, size, isEmpty

This implementation is genetic. 
Default capacity is 10
Default load factor is 0.8

Answer Author: Tianquan Guo
Date: 7/9/2021
 */

package HashMapImplementation;

public class HashMapTG<K, V> {
	int capacity;
	int size;
	Node<K, V>[] array;
	int DEFAULT_CAPACITY = 10;
	float DEFAULT_LOADFACTOR = 0.8f;
	
	HashMapTG () {
		array = (Node<K,V>[]) (new Node[DEFAULT_CAPACITY]);
		capacity = DEFAULT_CAPACITY;
	}
	
	HashMapTG (int capacity) {
		array = (Node<K,V>[])(new Node[capacity]);
		this.capacity = capacity;
	}
	
	public void put(K key, V value) {
		if (key == null) {
			return;
		} 
		
		int index = hash(key);
		
		if (array[index] == null) {
			array[index] = new Node<K, V>(key, value);
		}	else {
			Node<K, V> cur = array[index];
			Node<K, V> tail = null;
			
			while (cur !=null) {
				if (cur.getKey().equals(key)) {
					cur.setValue(value);
				} else {
					if (cur.next == null) {
						tail = cur;
					}
					cur = cur.next;
				}
			}
			
			if (tail != null) {
				tail.next = new Node<K, V>(key, value);
			}
			
			size++;
			
			if ((size + 0.0f) / capacity >= DEFAULT_LOADFACTOR) {
				rehash();
			}
			
		}
		
		return;
	}
	
	public V get (K key) {
		if (key == null) {
			return null;
		}
		
		int index = hash(key);
		
		Node<K, V> cur = array[index];
		
		while (cur != null) {
			if (cur.getKey().equals(key)) {
				return cur.getValue();
			} else {
				cur = cur.next;
			}
		}
		
		return null;
	}
	
	
	
	private int hash(K key) {
		if (key == null) {
			return 0;
		}
		
		int hashNum = key.hashCode();
		int index = hashNum % capacity;
		
		return index;
	}
	
	public boolean remove(K key) {
		if (key == null) {
			return false;
		}
		
		int index = hash(key);
		
		Node<K, V> cur = array[index];
		Node<K, V> pre = cur;
		
		if (cur.getKey().equals(key)) {
			array[index] = cur.next;
			size--;
			return true;
		}
		
		while (cur != null) {
			cur = cur.next;
			if (cur.getKey().equals(key)) {
				pre.next = cur.next;
				size--;
				return true;
			} else {
				pre = pre.next;
			}
		}
		
		return false;
	}
	
	private void rehash() {
		Node<K, V>[] oldArray  = array;
		array = (Node<K, V>[])(new Node[capacity * 2]);
		capacity = array.length;
		for (int i = 0; i < oldArray.length; i ++) {
			Node<K, V> cur = oldArray[i];
			if (cur == null) {
				continue;
			}
			
			while (cur != null) {
				int index = hash(cur.getKey());
				Node<K, V> next = cur.next;
				cur.next = array[index];
				array[index] = cur;
				cur = next;
			}
		}
	}
	
	public boolean containsKey(K key) {
		if (key == null) {
			return false;
		}
		
		int index = hash(key);
		
		Node<K, V> cur = array[index];
		
		while (cur != null) {
			if (cur.getKey().equals(key)) {
				return true;
			} else {
				cur = cur.next;
			}
		}
		
		return false;
	}
	
//	private Node findNode(K key) {
//		if (key == null) {
//			return null;
//		}
//		
//		int index = hash(key);
//		
//		Node<K, V> cur = array[index];
//		
//		while (cur != null) {
//			if (cur.getKey().equals(key)) {
//				return true;
//			} else {
//				cur = cur.next;
//			}
//		}
//		
//	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
//	class Node<K, V> {
//		private final K key;
//		private V value;
//		Node<K, V> next;
//		Node(K key, V value) {
//			this.key =  key;
//			this.value = value;
//			next = null;
//		}
//		
//		public K getKey() {
//			return key;
//		}
//		
//		public V getValue() {
//			return value;
//		}
//		
//		public void setValue(V value) {
//			this.value = value;
//		}
//	}
}
