package Problems;

import java.util.*;

public class NQueens {
	public List<List<Integer>> nQueens(int n) {
		List<List<Integer>> ans = new ArrayList<>();
		List<Integer> subset = new ArrayList<>();
		
		helper(n, ans, subset);
		
		return ans;
	}
	
	private void helper(int n, List<List<Integer>> ans, List<Integer> subset) {
		if (subset.size() == n) {
			ans.add(new ArrayList<Integer>(subset));
			return;
		}
		
		for (int i = 0; i < n; i++) {
			if (isValid(subset, i)) {
				subset.add(i);
				helper(n, ans, subset);
				subset.remove(subset.size() - 1);
			}
		}
		
		return;
	}
	
	private boolean isValid(List<Integer> subset, int cur) {
		for (int row = 0; row < subset.size(); row++) {
			if (subset.get(row) == cur) {
				return false;
			}
			
//			check current not threaten the previous queens
			if (Math.abs(row - subset.size()) == Math.abs(subset.get(row) - cur)) {
				return false;
			}
		}
		
		return true;
	}
	
	
}
