package com.sudoku.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SudokuGrid {
    private static SudokuGrid instance = null;
    private Cell[] cells;
    private Cell[] backUpCells;

    private SudokuGrid() {
        cells = new Cell[81];
        backUpCells = new Cell[81];
        for (int i = 0; i < 81; i++) {
            cells[i] = new Cell();
            backUpCells[i] = new Cell();
        }
    }

    public static SudokuGrid getInstance() {
        if (instance == null) {
            instance = new SudokuGrid();
        }
        return instance;
    }

    public void setCellValue(int index, int value) {
        cells[index].setValue(value);
    }
    public int getCellValue(int row, int col) {
        return cells[row * 9 + col].getValue();
    }

    public boolean isSolved() {
        for (Cell cell : cells) {
            if (cell.getValue() == -1) return false;
        }
        return true;
    }

    public void display(String message) {
        System.out.println(message);
        for (int row = 0; row < 9; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("---------+----------+---------");
            }

            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }

                int value = cells[row * 9 + col].getValue();
                String displayValue = (value == -1) ? "." : Integer.toString(value);

                System.out.printf("%2s ", displayValue);
            }
            System.out.println();
        }
    }



    public void loadFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int cellIndex = 0;
            while ((line = reader.readLine()) != null && cellIndex < 81) {
                String[] values = line.split(",");
                for (String val : values) {
                    int value = Integer.parseInt(val.trim());
                    cells[cellIndex].setValue(value == 0 ? -1 : value);
                    cellIndex++;
                }
            }
        }
    }

    public int[] getRowValues(int row) {
        int[] values = new int[9];
        for (int col = 0; col < 9; col++) {
            values[col] = getCellValue(row, col);
        }
        return values;
    }

    public int[] getColumnValues(int col) {
        int[] values = new int[9];
        for (int row = 0; row < 9; row++) {
            values[row] = getCellValue(row, col);
        }
        return values;
    }

    public int[] getBlockValues(int startRow, int startCol) {
        int[] values = new int[9];
        int index = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                values[index++] = getCellValue(startRow + row, startCol + col);
            }
        }
        return values;
    }

    private boolean isValidGroup(int[] values) {
        Set<Integer> seen = new HashSet<>();
        for (int value : values) {
            if (value == -1) {
                continue;
            }
            if (value < 1 || value > 9 || !seen.add(value)) {
                return false;
            }
        }
        return true;
    }
    public void deepCopy() {
        for (int i = 0; i < 81; i++) {
            backUpCells[i].setValue(cells[i].getValue());
        }
    }

    public void resetGrid() {
        for (int i = 0; i < 81; i++) {
            cells[i].setValue(backUpCells[i].getValue());
        }
    }

    public boolean isValidGrid() {
        for (int i = 0; i < 9; i++) {
            if (!isValidGroup(getRowValues(i)) || !isValidGroup(getColumnValues(i))) {
                return false;
            }
        }

        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!isValidGroup(getBlockValues(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }
}
