/**
1182. Shortest Distance to Target Color
Medium

246

7

Add to List

Share
You are given an array colors, in which there are three colors: 1, 2 and 3.

You are also given some queries. Each query consists of two integers i and c, return the shortest distance between the given index i and the target color c. If there is no solution return -1.

 

Example 1:

Input: colors = [1,1,2,1,3,2,2,3,3], queries = [[1,3],[2,2],[6,1]]
Output: [3,0,3]
Explanation: 
The nearest 3 from index 1 is at index 4 (3 steps away).
The nearest 2 from index 2 is at index 2 itself (0 steps away).
The nearest 1 from index 6 is at index 3 (3 steps away).
Example 2:

Input: colors = [1,2], queries = [[0,3]]
Output: [-1]
Explanation: There is no 3 in the array.
 

Constraints:

1 <= colors.length <= 5*10^4
1 <= colors[i] <= 3
1 <= queries.length <= 5*10^4
queries[i].length == 2
0 <= queries[i][0] < colors.length
1 <= queries[i][1] <= 3

Answer Author: Tianquan Guo
Date: 6/24/2021
 */

package Problems;

import java.util.*;

public class ShortestDistanceToTarget {
	public List<Integer> shortestDistanceColor(int[] colors, int[][] queries) {
        List<Integer> ans = new ArrayList<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < colors.length; i++) {
            if (!map.containsKey(colors[i])) {
                map.put(colors[i], new ArrayList<Integer>());
            } 
            
            map.get(colors[i]).add(i);
            
        }
        
        // Binary Search
        // System.out.println(map);
        
        for (int[] query : queries) {
            if (!map.containsKey(query[1])) {
                // System.out.println(query[1]);
                // System.out.println(map);
                ans.add(-1);
                continue;
            }
            
            List<Integer> curList = map.get(query[1]);
            
            int start = 0, end = curList.size() - 1;
            while (start + 1 < end) {
                // System.out.println(start + " " + end);
                int mid = (start + end) /2; 
                
                if (curList.get(mid) < query[0]) {
                    start = mid;
                } else {
                    end = mid;
                }
            }
            
            if (Math.abs(curList.get(start) - query[0]) < Math.abs(curList.get(end) - query[0])) {
                ans.add(Math.abs(curList.get(start) - query[0]));
            } else {
                ans.add(Math.abs(curList.get(end) - query[0]));
            }
        }
        
        
        return ans;        
    }
}
