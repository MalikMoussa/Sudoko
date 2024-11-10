package com.sudoku;

import com.sudoku.helper.SudokuGrid;
import com.sudoku.helper.SudokuSolver;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();
        SudokuGrid grid = SudokuGrid.getInstance();

        String filePath = args.length > 0 ? args[0] : "./sudoku_sample.txt";

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("Error: The file 'sudoku_sample.txt' was not found in the directory: "
                        + file.getAbsolutePath());
                return;
            }

            grid.loadFromFile(file.getAbsolutePath());
            if(!grid.isValidGrid()){
                System.out.println("Input Sudoku Grid Not Valid, Stopping the program...");
                return;
            }
            grid.deepCopy();
            grid.display("Input Grid : ");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        solver.solve();
        //System.out.println( grid.isSolvedCorrectly() ? "Sudoku solved correctly!" : "Sudoku not solved correctly!" );
    }
}

