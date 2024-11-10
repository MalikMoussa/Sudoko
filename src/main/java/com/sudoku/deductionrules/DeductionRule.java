package com.sudoku.deductionrules;

import com.sudoku.helper.SudokuGrid;
import com.sudoku.observers.Observer;

import java.util.HashSet;
import java.util.Set;

public abstract class DeductionRule implements Observer {
    public abstract void applyRule(SudokuGrid grid);
    @Override
    public void update(SudokuGrid grid, String difficulty) {
        grid.display("");
        System.out.println("\n\n\n*****Sudoku solved ! The grid was " + difficulty + "*****\n\n\n ");
    }

    Set<Integer> getPossibleValues(SudokuGrid grid, int row, int col) {
        Set<Integer> possibleValues = new HashSet<>();

        for (int i = 1; i <= 9; i++) {
            possibleValues.add(i);
        }

        for (int i = 0; i < 9; i++) {
            possibleValues.remove(grid.getCellValue(row, i));
            possibleValues.remove(grid.getCellValue(i, col));
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                possibleValues.remove(grid.getCellValue(startRow + i, startCol + j));
            }
        }

        return possibleValues;
    }
}
