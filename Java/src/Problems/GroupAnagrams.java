package Problems;

import java.util.*;

public class GroupAnagrams {
	public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) {
            return new ArrayList<>();
        }
        HashMap<String, List<String>> anagrams = new HashMap<>();
        
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            if (anagrams.containsKey(key)) {
                anagrams.get(key).add(str);
            } else {
                anagrams.put(key, new ArrayList<>());
                anagrams.get(key).add(str);
            }
        }
        
        List<List<String>> ans = new ArrayList<>();
        for (HashMap.Entry<String, List<String>> cur : anagrams.entrySet()) {
            ans.add(cur.getValue());
        }
        
        return ans;
    }
}
