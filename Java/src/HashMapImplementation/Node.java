/**
Node for HashMap implemtation

Answer Author: Tianquan Guo
Date: 7/9/2021
 */

package HashMapImplementation;

//import Problems.HashMapTG.Node;

public class Node<K, V> {
	private final K key;
	private V value;
	Node<K, V> next;
	Node(K key, V value) {
		this.key =  key;
		this.value = value;
		next = null;
	}
	
	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
}
