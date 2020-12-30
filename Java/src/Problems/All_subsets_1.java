package Problems;
import java.util.*;

public class All_subsets_1 {
	public List<String> subSets(String set) {
	    // Write your solution here.
	    List<String> ans = new ArrayList<String>();
	    
	    if (set == null) {
		      return ans;
		    }
	    
	    if (set.length() == 0) {
	      ans.add("");
	      return ans;
	    }	    

	    StringBuilder subset = new StringBuilder("");

	    helper(set, ans, 0, subset);
	    
	    return ans;
	  }

	  private void helper(String set, List<String> ans, int index, StringBuilder subset) {
	    if (index == set.length()) {
	      ans.add(subset.toString());
	      return;
	    }

	    helper(set, ans, index + 1, subset);
	    helper(set, ans, index + 1, subset.append(set.charAt(index)));
	    
	    subset.deleteCharAt(subset.length() - 1);

	    return;
	  }

}
