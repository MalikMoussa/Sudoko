package com.sudoku.deductionrules;

import com.sudoku.helper.SudokuGrid;

public class DR3 extends DeductionRule {

    @Override
    public void applyRule(SudokuGrid grid) {
        if (!solveWithBacktracking(grid)) {
            System.out.println("The Sudoku puzzle is unsolvable.");
        }
    }

    private boolean solveWithBacktracking(SudokuGrid grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid.getCellValue(row, col) == -1) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValueValid(grid, row, col, num)) {
                            grid.setCellValue(row * 9 + col, num);
                            if (solveWithBacktracking(grid)) {
                                return true;
                            }
                            grid.setCellValue(row * 9 + col, -1);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValueValid(SudokuGrid grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid.getCellValue(row, i) == num || grid.getCellValue(i, col) == num) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getCellValue(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
