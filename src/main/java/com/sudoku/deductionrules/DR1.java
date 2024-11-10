package com.sudoku.deductionrules;

import com.sudoku.helper.SudokuGrid;

import java.util.Set;

public class DR1 extends DeductionRule {
    @Override
    public void applyRule(SudokuGrid grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid.getCellValue(row, col) == -1) {
                    Set<Integer> possibleValues = getPossibleValues(grid, row, col);

                    if (possibleValues.size() == 1) {
                        int value = possibleValues.iterator().next();
                        grid.setCellValue(row * 9 + col, value);
                    }
                }
            }
        }
    }
}
