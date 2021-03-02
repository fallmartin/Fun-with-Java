package sudoku;

import java.util.*;

/**
 * Sudoku solver based on the solution provided in Introduction to Java
 * Programming textbook by Y Daniel Liang. The solution was changed to include a
 * recursive method that produces all possible solutions to a valid grid.
 *
 * The grid is valid
 * -----------------
 * 0 1 0 0 0 0 4 3 0
 * 7 0 0 0 0 0 0 0 0
 * 0 0 0 2 5 4 9 0 0
 * 1 7 0 0 4 0 2 0 6
 * 0 0 0 0 9 0 0 0 3
 * 0 0 3 0 0 6 0 8 0
 * 0 0 1 4 7 0 0 6 0
 * 0 0 0 5 0 8 1 2 0
 * 0 9 0 0 6 0 3 0 4
 *
 * 1 solutions
 * -----------------
 * 5 1 9 6 8 7 4 3 2
 * 7 2 4 9 1 3 6 5 8
 * 3 8 6 2 5 4 9 1 7
 * 1 7 8 3 4 5 2 9 6
 * 6 5 2 8 9 1 7 4 3
 * 9 4 3 7 2 6 5 8 1
 * 2 3 1 4 7 9 8 6 5
 * 4 6 7 5 3 8 1 2 9
 * 8 9 5 1 6 2 3 7 4
 *
 * The grid is valid
 * -----------------
 * 1 0 4 0 0 9 7 0 8
 * 0 2 0 3 0 0 6 0 0
 * 6 0 3 0 0 0 0 0 1
 * 0 0 5 0 0 0 0 0 0
 * 2 0 0 0 0 0 3 0 0
 * 0 0 0 9 0 1 0 0 0
 * 3 0 0 0 5 0 0 0 9
 * 0 0 0 2 0 0 0 0 0
 * 0 4 0 0 0 0 0 7 0
 *
 * 4600 solutions
 * -----------------
 * 1 5 4 6 2 9 7 3 8
 * 7 2 8 3 1 4 6 9 5
 * 6 9 3 5 7 8 2 4 1
 * 4 1 5 7 3 2 9 8 6
 * 2 6 9 4 8 5 3 1 7
 * 8 3 7 9 6 1 5 2 4
 * 3 8 2 1 5 7 4 6 9
 * 9 7 1 2 4 6 8 5 3
 * 5 4 6 8 9 3 1 7 2
 *
 * @author martinfall
 */
public class Sudoku {

    private static final ArrayList<Integer[][]> solutions = new ArrayList<>();

    /**
     * Main method with driver code.
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer[][] grid = {
            {0, 1, 0, 0, 0, 0, 4, 3, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 5, 4, 9, 0, 0},
            {1, 7, 0, 0, 4, 0, 2, 0, 6},
            {0, 0, 0, 0, 9, 0, 0, 0, 3},
            {0, 0, 3, 0, 0, 6, 0, 8, 0},
            {0, 0, 1, 4, 7, 0, 0, 6, 0},
            {0, 0, 0, 5, 0, 8, 1, 2, 0},
            {0, 9, 0, 0, 6, 0, 3, 0, 4}};

        System.out.println("The grid is "
                + (isValid(grid) ? "valid" : "not valid"));
        printGrid(grid);

        solutions.clear();
        solve(grid);
        System.out.println("\n" + solutions.size() + " solutions");

        // Print the first solution to console
        printGrid(solutions.get(0)); // This grid only has one solution

        Integer[][] blank = {
            {1, 0, 4, 0, 0, 9, 7, 0, 8},
            {0, 2, 0, 3, 0, 0, 6, 0, 0},
            {6, 0, 3, 0, 0, 0, 0, 0, 1},
            {0, 0, 5, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 3, 0, 0},
            {0, 0, 0, 9, 0, 1, 0, 0, 0},
            {3, 0, 0, 0, 5, 0, 0, 0, 9},
            {0, 0, 0, 2, 0, 0, 0, 0, 0},
            {0, 4, 0, 0, 0, 0, 0, 7, 0}};

        System.out.println("\nThe grid is "
                + (isValid(grid) ? "valid" : "not valid"));
        printGrid(blank);
        solutions.clear();
        solve(blank);
        System.out.println("\n" + solutions.size() + " solutions");

        // Print the first solution to console
        printGrid(solutions.get(0));
    }

    public static void solve(Integer[][] grid) {
        Integer[][] freeCellList = getFreeCellList(grid);
        if (freeCellList.length == 0) {
            solutions.add(deepCopy(grid));
            // printGrid(grid);
        }

        int k = 0;
        solve(k, grid, freeCellList);
    }

    public static void solve(int k, Integer[][] grid, Integer[][] freeCellList) {

        int i = freeCellList[k][0];
        int j = freeCellList[k][1];

        for (int number = 1; number <= 9; number++) {
            grid[i][j] = number;
            if (isValid(i, j, grid) && k + 1 != freeCellList.length) {
                solve(k + 1, grid, freeCellList);
            }

            if (k + 1 == freeCellList.length
                    && isValid(grid)) {
                solutions.add(deepCopy(grid));
                // printGrid(grid);
            }

            grid[i][j] = 0;
        }

    }

    public static ArrayList<Integer[][]> getSolutions() {
        return solutions;
    }

    public static Integer[][] getFreeCellList(Integer[][] grid) {
        int numberOfFreeCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    numberOfFreeCells++;
                }
            }
        }

        Integer[][] freeCellsList = new Integer[numberOfFreeCells][2];

        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    freeCellsList[index][0] = i;
                    freeCellsList[index++][1] = j;
                }
            }
        }

        return freeCellsList;
    }

    /**
     * Prints the Sudoku grid to the console.
     *
     * @param grid
     */
    public static void printGrid(Integer[][] grid) {
        System.out.println("-----------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns true if the grid is valid.
     *
     * @param grid
     * @return
     */
    public static boolean isValid(Integer[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] < 0
                        || grid[i][j] > 9
                        || (grid[i][j] != 0 && !isValid(i, j, grid))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if the grid is valid at i, j.
     *
     * @param i
     * @param j
     * @param grid
     * @return
     */
    public static boolean isValid(int i, int j, Integer[][] grid) {
        for (int column = 0; column < 9; column++) {
            if (column != j && Objects.equals(grid[i][column], grid[i][j])) {
                return false;
            }
        }

        for (int row = 0; row < 9; row++) {
            if (row != i && Objects.equals(grid[row][j], grid[i][j])) {
                return false;
            }
        }

        for (int row = (i / 3) * 3; row < (i / 3) * 3 + 3; row++) {
            for (int col = (j / 3) * 3; col < (j / 3) * 3 + 3; col++) {
                if (!(row == i && col == j)
                        && Objects.equals(grid[row][col], grid[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a deep copy of the parameter array.
     *
     * @param grid
     * @return
     */
    public static Integer[][] deepCopy(Integer[][] grid) {
        Integer[][] copy = new Integer[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy[i][j] = grid[i][j];
            }
        }

        return copy;
    }
}
