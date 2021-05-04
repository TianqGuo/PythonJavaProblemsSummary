package Problems;

import java.util.*;

public class TopKFrequent {
	public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        PriorityQueue<HashMap.Entry<Integer, Integer>> pq = new PriorityQueue<HashMap.Entry<Integer, Integer>>(new Comparator<HashMap.Entry<Integer, Integer>>() {
            @Override
            public int compare(HashMap.Entry<Integer, Integer> a, HashMap.Entry<Integer, Integer> b) {
                if (a.getValue() == b.getValue()) {
                    return 0;
                }
                
                return a.getValue() < b.getValue() ? 1 : -1;
            }
        });
        
        for (int num : nums) {
            if (!map.containsKey(num)){
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }
        }
        
        for (HashMap.Entry<Integer, Integer> cur : map.entrySet()) {
            pq.offer(cur);
        }
        
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = pq.poll().getKey();
        }
        
        return ans;
    }
}
