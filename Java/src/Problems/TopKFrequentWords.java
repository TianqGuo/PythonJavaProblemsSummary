package Problems;

import java.util.*;

public class TopKFrequentWords {
	public String[] topKFrequent(String[] Combo, int k) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		for (int i = 0; i < Combo.length; i++) {
			if (map.containsKey(Combo[i])) {
				map.put(Combo[i], map.get(Combo[i]) + 1);
      } else {
        map.put(Combo[i], 1);
      }
    }

		PriorityQueue<HashMap.Entry<String, Integer>> pq = new PriorityQueue<>(new Comparator<HashMap.Entry<String, Integer>> ()
    {
			@Override
 			public  int compare (HashMap.Entry<String, Integer> num1, HashMap.Entry<String, Integer> num2) {
				if (num1.getValue() == num2.getValue()) {
					return 0;
        }

        return num1.getValue() < num2.getValue() ? 1 : -1;
      }
    } );

    for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
      pq.offer(entry);
    }

    int num = Math.min(k, pq.size());
    String[] ans = new String[num];
    
    for (int i = 0; i < num; i++) {

      ans[i] = pq.poll().getKey();
    }

    return ans;
  }
}
