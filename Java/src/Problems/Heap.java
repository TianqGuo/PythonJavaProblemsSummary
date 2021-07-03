package Problems;

import java.util.*;

public class Heap {
	public int size;
	private int capacity;
	public int[] array; 
	private double DEFAULT_LOADFACTOR = 0.8;
	private int DEFAULT_SIZE = 16;
	Heap (int capacity) {
		this.capacity = capacity;
		this.array = new int[this.capacity];
	}
	
	Heap () {
		this.capacity = DEFAULT_SIZE;
		this.array = new int[this.capacity];
	}
	
	private void swap(int i, int j, int[] array) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	private void percolateUp() {
		int cur = size - 1;
		int parent = (cur - 1) / 2;
		
		while (cur > 0 && array[cur] < array[parent]) {
//			System.out.println(cur +  " " +parent);
			swap(cur, parent, array);
			cur = parent;
			parent = (parent - 1) / 2;
		}
		
		return;
	}
	
	private void percolateDown() {
		int cur = 0;
		int childLeft = cur * 2 + 1;
		int childRight = cur * 2 + 2;
		while (cur < size) {
			if (childLeft >= size) {
				break;
			}
			
			int candidate = childLeft; 
			if (array[candidate] > array[childRight]) {
				candidate = childRight;
			}
			
			if (array[cur] > array[candidate]) {
				swap(cur, candidate, array);
			} else {
				break;
			}
			
			cur = candidate;
			childLeft = cur * 2 + 1;
			childRight = cur * 2 + 2;
		}
		
	}
	
	public void offer(int value) {
		if (size > (int) (capacity * DEFAULT_LOADFACTOR)) {
//				reSize();
			array = Arrays.copyOf(array, (int)capacity * 2);
		}
		array[size] = value;
		size++;
		percolateUp();
	}
//	
//	private void reSize() {
//		int[] newArray = new int[size * 2];
//		for (int i = 0; i < array.length; i++) {
//			newArray[i] = array[i];
//		}
//		
//		this.array = newArray;
//	}
	
	public Integer peek() {
		return size == 0 ? null: array[0];
	}
	
	public Integer poll() {
		if (size == 0) {
			return null;
		}
		int ans = array[0];
		array[0] = array[size - 1];
		size--;
		percolateDown();
		
		return ans;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}

}
