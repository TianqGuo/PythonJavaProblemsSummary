/**
设有N堆沙子排成一排，其编号为1,2,3,...,N(N<=100)。每堆沙子有一定的数量。现要将N堆沙子并成为一堆。归并的过程只能每次将相邻的两堆沙子堆成一堆（每次合并花费的代价为当前两堆沙子的总数量），这样经过N-1次归并后成为一堆，归并的总代价为每次合并花费的代价和。找出一种合理的归并方法，使总的代价最小。

例如：有3堆沙子，数量分别为13,7,8，有两种合并方案。
第一种方案：先合并1,2号堆，合并后的新堆沙子数量为20，本次合并代价为20，再拿新堆与第3堆沙子合并，合并后的沙子数量为28，本次合并代价为28，将3堆沙子合并到一起的总代价为第一次合并代价20加上第二次合并代价28，即48；
第二种方案：先合并2,3号堆，合并后的新堆沙子数量为15，本次合并代价为15，再拿新堆与第1堆沙子合并，合并后的沙子数量为28，本次合并代价为28，将3堆沙子合并到一起的总代价为第一次合并代价15加上第二次合并代价28，即43；
采用第二种方案可取得最小总代价，值为43。


Examples:

[13, 7, 8]

13, 7, 8 => 20, 8 => 48
13,7,8 => 13, 15 => 43


Answer Author: Tianquan Guo
Date: 2/14/2020
 */

package Problems;

import java.util.Arrays;

public class MinCombineSands {
	public int minCombine(int[] array) {
		
//		note we need 2D dp array to solve this problem
		int[][] M = new int[array.length + 1][array.length + 1];
		
//		initialize M		
		for (int i = 0; i <= array.length; i ++) {
			M[i][i] = 0;
		}
		
//		initialize prefixSum
		int[] prefixSum = new int[array.length + 1];
		prefixSum[0] = 0;
		for (int i = 1; i <= array.length; i ++) {
			prefixSum[i] += prefixSum[i - 1] + array[i - 1];
		}
		
//		start iteration
		for (int i = 2; i <= array.length; i ++) {
			for (int j = i - 1; j > 0 ; j--) {
				if (j + 1 == i) {
//					note here for array we need j - 1 and i - 1
					M[j][i] = array[j - 1] + array[i - 1];
				} else {
//					reset M[j][i]
					M[j][i] = Integer.MAX_VALUE;
//					get current sum between i and j
					int curSum = prefixSum[i] - prefixSum[j - 1];
//					key step, get min of either M[j][i] or M[j][k] + M[k + 1][i] + curSum
//					note when k + 1 = i, then M[k + 1][i] is 0 since it is included in curSume
					for (int k = j; k < i; k++) {
						M[j][i] = Math.min(M[j][i], M[j][k] + M[k + 1][i] + curSum );
					}  
				}
			}
		}
		
		System.out.println(Arrays.deepToString(M));
		System.out.println(Arrays.toString(array));
		System.out.println(Arrays.toString(prefixSum));

		return M[1][array.length];
	}
}
