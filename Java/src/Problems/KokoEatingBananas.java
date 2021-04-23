/**
875. Koko Eating Bananas
Medium

Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.

Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer k such that she can eat all the bananas within h hours.

 

Example 1:

Input: piles = [3,6,7,11], h = 8
Output: 4
Example 2:

Input: piles = [30,11,23,4,20], h = 5
Output: 30
Example 3:

Input: piles = [30,11,23,4,20], h = 6
Output: 23
 

Constraints:

1 <= piles.length <= 104
piles.length <= h <= 109
1 <= piles[i] <= 109

Answer Author: Tianquan Guo
Date: 4/22/2021
 */

package Problems;

public class KokoEatingBananas {
	public int minEatingSpeed(int[] piles, int h) {
        int min = 1;
        int max = 1_000_000_000;
        while (min + 1< max) {
            int mid = min + (max - min) / 2;
            if (!isValid(piles, h, mid)) {
                min = mid;
            } else {
                max = mid;
            }
        }
        
        if (isValid(piles, h, min)) {
            return min;
        }
        
        return max;
    }
    
    private boolean isValid (int[] piles, int h, int mid) {
        int count = 0;
        for (int cur : piles) {
//             note how count is calculated
            count += (cur - 1) / mid + 1;
        }
        return count <= h;
    }
}
