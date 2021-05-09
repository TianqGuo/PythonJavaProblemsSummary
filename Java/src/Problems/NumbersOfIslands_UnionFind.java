/**
200. Number of Islands
Medium
Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

 

Example 1:

Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
Example 2:

Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
 

Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] is '0' or '1'.

Answer Author: Tianquan Guo
Date: 5/9/2021
 */

package Problems;

public class NumbersOfIslands_UnionFind {
	int[] pre = new int[100000];;
    int ans = 0;
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        
        
//         check how many '1' in the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pre[i*cols + j] = i*cols + j;
                if (grid[i][j] == '1') {
                    ans++;
                }
            }
        }
        
//         check how many '1' need to connect and union find
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (grid[i][j] == '1') {
                    if (i+1 < rows && grid[i+1][j] == '1') {
                        connect(i*cols + cols + j,i*cols + j);
                    }
                    if (j+1 < cols && grid[i][j+1] == '1') {
                        connect(i*cols + 1 + j,i*cols + j);
                    }
                }
            }
        }
        return ans;
    }
    
    int unionfind(int root) { //���ҵ�����ڵ�
    	int son, tmp;
	    son = root;
	    while(root != pre[root]) { // ͨ��while����pre[root],���root != pre[root],�������Ǹ��ڵ�
	    	root = pre[root];
        }
	    while(son != root)   //·��ѹ������Ҫ����һ�飬��pre[son]���óɸ��ڵ㣬�Ժ���ʾͲ���
	    {
		    tmp = pre[son];
		    pre[son] = root;
		    son = tmp;
	    }
	    return root;
    }
    
    void connect(int A, int B) {  //����A��B������
        int rootA = unionfind(A); // Ѱ��A, B�ĸ��ڵ�
        int rootB = unionfind(B);
        if (rootA != rootB) { // ����A��B�ĸ��ڵ�
            pre[rootB] = rootA;
            ans--;
        }
    }
}
