package maze;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Astar {
    // A* algorithm to find minimal steps wayout;
            /*

██████████
    ██
██  ██  ██
██      ██
██████████
         */
    public static void main(String[] args) {
        Astar aster = new Astar(new int[][]{{1,1,1,1,1},{0,0,1,0,0},{1,0,1,0,1},{1,0,0,0,1}, {1,1,1,1,1}});
        System.out.println("test");
        aster.escape();
    }
    int[][] maze;
    int inx, iny, outx, outy;
    int m, n;
    public Astar(int[][] maze) {
        this.m = maze.length;
        this.n = maze[0].length;
        this.maze = maze;


    }
    private void findInOut(){
        List<int[]> list = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            if (maze[i][0] == 0) {
                list.add(new int[]{i, 0});
            }
            if (maze[i][n-1] == 0) {
                list.add(new int[]{i, n-1});
            }
        }
        for (int j = 0; j < n; j++) {
            if (maze[0][j] == 0) {
                list.add(new int[]{0, j});
            }
            if (maze[m-1][j] == 0) {
                list.add(new int[]{m-1, j});
            }
        }
        inx = list.get(0)[0];
        iny = list.get(0)[1];
        outx = list.get(1)[0];
        outy = list.get(1)[1];
        //System.out.println(list);
    }
    private int h(int x, int y) {
        return Math.abs(outx - x) + Math.abs(outy - y);
    }
    public void escape() {
        findInOut();
        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        int[][][] pre = new int[m][n][3];  // 1 up  2 right 3 down  4 left
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pre[i][j][2] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->(a[2]+a[3]) -(b[2]+b[3]));
        pq.offer(new int[]{inx, iny, 0, h(inx, iny)});
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int px = p[0], py = p[1];
            if (px == outx && py == outy) break;

            int now = p[2];
            for (int[] d : directions) {
                int x = px + d[0], y = py + d[1];
                if (x < 0 || x >= m || y < 0 || y >= n) continue;
                if (maze[x][y] == 1) continue;
                if (now + 1 < pre[x][y][2]) {
                    pq.offer(new int[]{x, y, now+1, h(x, y)});
                    pre[x][y][0] = px;
                    pre[x][y][1] = py;
                    pre[x][y][2] = now+1;
                }
            }
        }

        List<int[]> list = getRoute(pre);
        int[][] solved = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                solved[i][j] = maze[i][j];
            }
        }
        for (int[] e : list) {
            solved[e[0]][e[1]] = 2;
        }
        displayBlock(solved);

    }
    private List<int[]> getRoute(int[][][] pre) {
        int i = outx, j = outy;
        List<int[]> list = new LinkedList<>();
        //System.out.print("("+i+","+j + ")");

        while (i != inx || j != iny) {
            list.add(0, new int[]{i, j});
            int prex = pre[i][j][0], prey = pre[i][j][1];
            i = prex;
            j = prey;
            //System.out.print("<- ("+i+","+j + ")");
        }
        list.add(new int[]{inx, iny});
        return list;
    }
    public void displayBlock(int[][] matrix) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else if (matrix[i][j] == 0){
                    System.out.print("  ");
                } else if (matrix[i][j] == 2) {
                    System.out.print("//");
                }
            }
            System.out.println();
        }
    }
}
