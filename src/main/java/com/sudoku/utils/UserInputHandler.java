package com.sudoku.utils;

import com.sudoku.helper.SudokuGrid;

import java.util.Scanner;


public class UserInputHandler {
    public static void promptUserForInput(SudokuGrid grid) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cell position and value in the format ROW,COL-VALUE [1-9],[1-9]-[1-9] (e.g., 1,2-7): ");

        try {
            String input = scanner.nextLine();
            String[] mainParts = input.split("-");
            if (mainParts.length != 2) {
                System.out.println("Invalid format. Please enter in the format ROW,COL-VALUE.");
                return;
            }

            String[] position = mainParts[0].split(",");
            if (position.length != 2) {
                System.out.println("Invalid format. Please enter in the format ROW,COL-VALUE.");
                return;
            }

            int row = Integer.parseInt(position[0].trim()) -1 ;
            int col = Integer.parseInt(position[1].trim()) - 1;
            int value = Integer.parseInt(mainParts[1].trim());

            if (row < 0 || row > 8 || col < 0 || col > 8 || value < 1 || value > 9) {
                System.out.println("Invalid input. Please enter row and column between 1-9 and a value between 1-9.");
                return;
            }

            if (grid.getCellValue(row, col) != -1) {
                System.out.println("Cell is already filled. Please choose an empty cell.");
                promptUserForInput(grid);
                return;
            }

            grid.setCellValue(row * 9 + col, value);
            if (!isGridValid(grid)) {
                System.out.println("Invalid move! This value violates Sudoku rules. Game over. Please restart the solving !");
                System.exit(0);
            }
            else {
                System.out.println("Move accepted.");
                grid.display("Grid after move : ");
                if(grid.isSolved()) {
                    System.out.println("Congratulations! You solved the Sudoku puzzle!");
                    System.exit(0);
                }
            }


        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter numbers in the format ROW,COL-VALUE.");
        }
    }

    private static boolean isGridValid(SudokuGrid grid) {
        for (int i = 0; i < 9; i++) {
            if (!isValidGroup(grid.getRowValues(i)) || !isValidGroup(grid.getColumnValues(i))) {
                return false;
            }
        }

        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!isValidGroup(grid.getBlockValues(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidGroup(int[] values) {
        boolean[] seen = new boolean[10];
        for (int value : values) {
            if (value != -1) {
                if (seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }
}



