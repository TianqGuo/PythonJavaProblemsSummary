package Problems;

public class MergeSort {

  public int[] mergesort(int[] array) {
    // Write your solution here
    if (array == null || array.length <= 1) {
      return array;
    }
    
    return this.merge_recursion(array, 0, array.length -1);
   
  }

  private int[] merge_recursion(int[] array, int left, int right) {
	  
	  if (left == right) {
		  return new int[] {array[left]};
	  }

    int mid = left + (right -left)/2;
    int[] left_merged = merge_recursion(array, left, mid);
    int[] right_merged = merge_recursion(array, mid+1, right);

    return this.merge_arrays(left_merged, right_merged);

  }

  private int[] merge_arrays(int[] left, int[] right) {

    int i = 0;
    int j = 0;
    int cur = 0;
    int[] merged = new int[left.length + right.length];

    while (i < left.length && j < right.length) {
      if (left[i] <= right[j]) {
        merged[cur] = left[i];
        cur++;
        i++;
      }

      else {
        merged[cur] = right[j];
        cur++;
        j++;
      }
    }

    while (i < left.length) {
      merged[cur] = left[i];
      cur++;
      i++;
    } 

    while (j < right.length) {
      merged[cur] = right[j];
      cur++;
      j++;
    }

    return merged;
  }

}
