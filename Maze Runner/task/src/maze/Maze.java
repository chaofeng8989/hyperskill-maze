package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Maze {
    int[][] maze;
    int m, n;
    int inx, iny, outx, outy;
    Random random = new Random();

    public Maze(int m, int n) {
        this.m = m;
        this.n = n;
        generateMaze(m, n);


    }

    public Maze(int[][] maze, int m, int n) {
        this.maze = maze;
        this.m = m;
        this.n = n;

    }

    public Maze(String fileName) {
        //fileName = "res/" + fileName;

        try (Scanner scanner = new Scanner(new File(fileName))){
            int m = 0;
            if (scanner.hasNextInt()) {
                m = scanner.nextInt();
            } else {
                System.out.println("Cannot load the maze. It has an invalid format");
                return;
            }
            this.maze = new int[m][m];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if (!scanner.hasNextInt()) {
                        System.out.println("Cannot load the maze. It has an invalid format");
                        return;
                    }
                    maze[i][j] = scanner.nextInt();
                }
            }
            this.m = m;
            this.n = m;
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist\n", fileName);
        }
    }
    public void save(String fileName) {
        //fileName = "res/" + fileName;
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(file)){
            fw.write(""+m);
            fw.write("\n");
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    fw.write(""+maze[i][j] + " ");
                }
                fw.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private  void generateMaze(int m, int n) {
        maze = new int[m][n];

        for (int i = 0 ; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maze[i][j] = 1;
            }
        }
        Set<String> visited = new HashSet<>();
        Set<String> nodes = new HashSet<>();
        int in = random.nextInt((m-2) / 2) * 2 + 1;
        int out = random.nextInt((m-2) / 2) * 2+1;
        inx = in; iny = 0;
        outx = out; outy = n-1;
        maze[in][0] = 0;
        maze[out][n-1] = 0;
        nodes.add(out +","+ (n-1));
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[6]-b[6]);
        for (int i = 1; i < m-1; i += 2) {
            for (int j = 1; j < n-1; j += 2) {
                nodes.add(i +","+ j);
                maze[i][j] = 0;
            }
        }
        addNeighbors(pq, in, 0, visited, nodes);

        while (!pq.isEmpty()) {
            int[] e = pq.poll();
            int ex = e[2], ey = e[3];
            int px = e[4], py = e[5];
            if (ex < 1 || ex > m-1 || ey < 1 || ey > n-1) continue;
            if (visited.contains(px+","+py)) continue;
            visited.add(px+","+py);
            maze[ex][ey] = 0;
            addNeighbors(pq, px, py, visited, nodes);
        }

    }
    private void addNeighbors(PriorityQueue<int[]> pq, int px, int py, Set<String> visited, Set<String> nodes) {
        if(!visited.contains((px+2) +","+  py) && nodes.contains((px+2) +","+  py))
            pq.offer(new int[]{px, py, px + 1, py, px+2, py, random.nextInt(1000)});
        if(!visited.contains((px-2) +","+  py) && nodes.contains((px-2) +","+  py))
            pq.offer(new int[]{px, py, px - 1, py, px-2, py, random.nextInt(1000)});
        if(!visited.contains(px +","+  (py+2)) && nodes.contains(px +","+  (py+2)))
            pq.offer(new int[]{px, py, px, py + 1, px, py + 2, random.nextInt(1000)});
        if(!visited.contains(px +","+  (py-2)) && nodes.contains(px +","+ (py-2)))
            pq.offer(new int[]{px, py, px, py - 1, px, py - 2, random.nextInt(1000)});

        if(!visited.contains((px+1) +","+  py) && nodes.contains((px+1) +","+  py))
            pq.offer(new int[]{px, py, px + 1, py, px+1, py, random.nextInt(1000)});
        if(!visited.contains((px-1) +","+  py) && nodes.contains((px-1) +","+  py))
            pq.offer(new int[]{px, py, px - 1, py, px-1, py, random.nextInt(1000)});
        if(!visited.contains(px +","+  (py+1)) && nodes.contains(px +","+  (py+1)))
            pq.offer(new int[]{px, py, px, py + 1, px, py + 1, random.nextInt(1000)});
        if(!visited.contains(px +","+  (py-1)) && nodes.contains(px +","+ (py-1)))
            pq.offer(new int[]{px, py, px, py - 1, px, py - 1, random.nextInt(1000)});

        if(!visited.contains((px+1) +","+  (py+1)) && nodes.contains((px+1) +","+  (py+1)))
            pq.offer(new int[]{px, py, px + 1, py + 1, px+1, py+1, random.nextInt(1000)});
        if(!visited.contains((px-1) +","+  (py+1)) && nodes.contains((px-1) +","+  (py+1)))
            pq.offer(new int[]{px, py, px - 1, py + 1, px-1, py+1, random.nextInt(1000)});
        if(!visited.contains((px+1) +","+  (py-1)) && nodes.contains((px+1) +","+  (py-1)))
            pq.offer(new int[]{px, py, px + 1, py - 1, px+1, py-1, random.nextInt(1000)});
        if(!visited.contains((px-1) +","+  (py-1)) && nodes.contains((px-1) +","+  (py-1)))
            pq.offer(new int[]{px, py, px - 1, py - 1, px-1, py-1, random.nextInt(1000)});



    }

    public void display(boolean block) {
        if (block) displayBlock();
        else displayNum();
    }
    public void displayNum() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("1");
                } else {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }

    public void escape() {
        Astar aster = new Astar(maze);
        aster.escape();
    }
    public void displayBlock() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (maze[i][j] == 1) {
                    System.out.print("\u2588\u2588");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

}
