package Problems;

public class Move_0s_to_end {
	public int[] moveZero(int[] array) {
	    // Write your solution here
	    int i = 0;
	    int j = 0;

	    while (j < array.length) {
	      if (array[j] != 0) {
	        swap(array, i, j);
	        i++;
	        j++;
	      }

	      else {
	        j++;
	      }
	    }

	    return array;
	  }

	  private void swap(int[] array, int i, int j) {
	    int temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	  }

}
