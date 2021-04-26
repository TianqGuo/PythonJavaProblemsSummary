/**
253. Meeting Rooms II
Medium
Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.

 

Example 1:

Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
Example 2:

Input: intervals = [[7,10],[2,4]]
Output: 1
 

Constraints:

1 <= intervals.length <= 104
0 <= starti < endi <= 106

Answer Author: Tianquan Guo
Date: 4/25/2021
 */

package Problems;

import java.util.*;

public class MeetingRooms2 {
	public int minMeetingRooms(int[][] intervals) {
        List<Integer> start = new ArrayList<>();
        List<Integer> end = new ArrayList<>();
        
//        no need to sort here
        // Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        for (int[] interval : intervals) {
            start.add(interval[0]);
            end.add(interval[1]);
        }
        
//        note how to sort for collections
        Collections.sort(start);
        Collections.sort(end);
        
        int startIndex = 0;
        int endIndex = 0;
        int curMeetings = 0;
        int ans = 0;
        
//        check start and end value, and if the next is start then curMeetings++
//        ele curMeetings--
        while (startIndex < start.size()) {
            if (start.get(startIndex) < end.get(endIndex)) {
                curMeetings += 1;
                ans = Math.max(ans, curMeetings);
                startIndex++;
            } else {
                curMeetings -= 1;
                endIndex++;
            }
            
        }
        
        return ans;
    }
}
