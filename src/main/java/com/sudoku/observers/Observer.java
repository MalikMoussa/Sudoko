package com.sudoku.observers;

import com.sudoku.helper.SudokuGrid;

public interface Observer {
    void update(SudokuGrid grid, String difficulty);
}
