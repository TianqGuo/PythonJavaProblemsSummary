/**
����N��ɳ���ų�һ�ţ�����Ϊ1,2,3,...,N(N<=100)��ÿ��ɳ����һ������������Ҫ��N��ɳ�Ӳ���Ϊһ�ѡ��鲢�Ĺ���ֻ��ÿ�ν����ڵ�����ɳ�Ӷѳ�һ�ѣ�ÿ�κϲ����ѵĴ���Ϊ��ǰ����ɳ�ӵ�������������������N-1�ι鲢���Ϊһ�ѣ��鲢���ܴ���Ϊÿ�κϲ����ѵĴ��ۺ͡��ҳ�һ�ֺ���Ĺ鲢������ʹ�ܵĴ�����С��

���磺��3��ɳ�ӣ������ֱ�Ϊ13,7,8�������ֺϲ�������
��һ�ַ������Ⱥϲ�1,2�Ŷѣ��ϲ�����¶�ɳ������Ϊ20�����κϲ�����Ϊ20�������¶����3��ɳ�Ӻϲ����ϲ����ɳ������Ϊ28�����κϲ�����Ϊ28����3��ɳ�Ӻϲ���һ����ܴ���Ϊ��һ�κϲ�����20���ϵڶ��κϲ�����28����48��
�ڶ��ַ������Ⱥϲ�2,3�Ŷѣ��ϲ�����¶�ɳ������Ϊ15�����κϲ�����Ϊ15�������¶����1��ɳ�Ӻϲ����ϲ����ɳ������Ϊ28�����κϲ�����Ϊ28����3��ɳ�Ӻϲ���һ����ܴ���Ϊ��һ�κϲ�����15���ϵڶ��κϲ�����28����43��
���õڶ��ַ�����ȡ����С�ܴ��ۣ�ֵΪ43��


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
