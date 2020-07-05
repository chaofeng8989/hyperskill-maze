package maze;

import java.util.*;
/*

██        ██    ████
██████    ██████
  ████      ██    ██
██  ██████  ████
██    ██          ██
      ██  ██  ████
    ██████      ██
  ██  ██    ██    ██
████    ██
██  ██  ████



 */
public class Main {
    static final String menu = "=== Menu ===";
    static final String[] options = {
            "1. Generate a new maze.",
            "2. Load a maze.",
            "3. Save the maze.",
            "4. Display the maze.",
            "5. Find the escape.",
            "0. Exit."
    };
    static final String promptSize = "Enter the size of a new maze";
    public static void main(String[] args) {
        System.out.println(menu);
        System.out.println(options[0]);
        System.out.println(options[1]);
        System.out.println(options[5]);
        Maze maze = null;
        Scanner scanner = new Scanner(System.in);
        loop : while (true) {
            int op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:{
                    System.out.println("Enter the size of a new maze");
                    int m = scanner.nextInt();
                    maze = new Maze(m, m);
                    maze.display(true);
                }

                break;
                case 2:{
                    String fileName = scanner.nextLine();
                    Maze newMaze = new Maze(fileName);
                    if (newMaze.m != 0) maze = newMaze;
                    //maze.display(true);
                }
                    break;
                case 3:{
                    if (maze == null)System.out.println("Incorrect option. Please try again");
                    else {
                        String fileName = scanner.nextLine();
                        maze.save(fileName);
                    }
                }
                    break;
                case 4:{
                    if (maze == null)System.out.println("Incorrect option. Please try again");
                    else maze.display(true);
                }
                    break;
                case 5 :{
                    maze.escape();
                }
                    break;
                case 0:
                    break loop;
                default:System.out.println("Incorrect option. Please try again");
            }
            System.out.println(menu);

            for (String x : options) {
                System.out.println(x);
            }
        }

    }

}
