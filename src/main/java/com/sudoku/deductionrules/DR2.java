package com.sudoku.deductionrules;

import com.sudoku.helper.SudokuGrid;

import java.util.*;
import java.util.stream.Collectors;

public class DR2 extends DeductionRule {

    @Override
    public void applyRule(SudokuGrid grid) {
        simpleElimination(grid);

        hiddenPairsAndTriplets(grid);

        uniqueCandidate(grid);

        blockLineElimination(grid);
    }

    private void simpleElimination(SudokuGrid grid) {
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

    private void hiddenPairsAndTriplets(SudokuGrid grid) {

        for (int i = 0; i < 9; i++) {
            findHiddenPairsOrTriplets(grid, getIndexedCells(i, "row"));
            findHiddenPairsOrTriplets(grid, getIndexedCells(i, "column"));

            int startRow = (i / 3) * 3;
            int startCol = (i % 3) * 3;
            findHiddenPairsOrTriplets(grid, getIndexedCells(startRow, startCol, "block"));
        }
    }

    private void findHiddenPairsOrTriplets(SudokuGrid grid, List<int[]> cells) {
        List<int[]> emptyCells = getEmptyCellsInGroup(grid, cells);

        Map<Set<Integer>, List<int[]>> candidates = new HashMap<>();

        for (int[] cell : emptyCells) {
            Set<Integer> possibleValues = getPossibleValues(grid, cell[0], cell[1]);
            if (possibleValues.size() == 2 || possibleValues.size() == 3) {
                candidates.putIfAbsent(possibleValues, new ArrayList<>());
                candidates.get(possibleValues).add(cell);
            }
        }

        for (Map.Entry<Set<Integer>, List<int[]>> entry : candidates.entrySet()) {
            if (entry.getValue().size() == entry.getKey().size()) {
                Set<Integer> pairOrTriplet = entry.getKey();
                for (int[] cell : emptyCells) {
                    if (!entry.getValue().contains(cell)) {
                        Set<Integer> cellPossibleValues = getPossibleValues(grid, cell[0], cell[1]);
                        cellPossibleValues.removeAll(pairOrTriplet);
                    }
                }
            }
        }
    }

    private void uniqueCandidate(SudokuGrid grid) {

        for (int i = 0; i < 9; i++) {
            findUniqueCandidate(grid, getIndexedCells(i, "row"));
            findUniqueCandidate(grid, getIndexedCells(i, "column"));

            int startRow = (i / 3) * 3;
            int startCol = (i % 3) * 3;
            findUniqueCandidate(grid, getIndexedCells(startRow, startCol, "block"));
        }

    }

    private boolean findUniqueCandidate(SudokuGrid grid, List<int[]> cells) {
        boolean progressMade = false;
        Map<Integer, int[]> candidateMap = new HashMap<>();

        for (int[] cell : cells) {
            if (grid.getCellValue(cell[0], cell[1]) == -1) {
                for (int candidate : getPossibleValues(grid, cell[0], cell[1])) {
                    if (candidateMap.containsKey(candidate)) {
                        candidateMap.put(candidate, null);
                    } else {
                        candidateMap.put(candidate, cell);
                    }
                }
            }
        }

        for (Map.Entry<Integer, int[]> entry : candidateMap.entrySet()) {
            if (entry.getValue() != null) {
                grid.setCellValue(entry.getValue()[0] * 9 + entry.getValue()[1], entry.getKey());
                progressMade = true;
            }
        }

        return progressMade;
    }

    private void blockLineElimination(SudokuGrid grid) {
        for (int blockRow = 0; blockRow < 9; blockRow += 3) {
            for (int blockCol = 0; blockCol < 9; blockCol += 3) {
                eliminateBlockLineCandidates(grid, blockRow, blockCol);
            }
        }
    }

    private void eliminateBlockLineCandidates(SudokuGrid grid, int blockRow, int blockCol) {
        for (int candidate = 1; candidate <= 9; candidate++) {
            Set<Integer> rows = new HashSet<>();
            Set<Integer> cols = new HashSet<>();

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (grid.getCellValue(blockRow + row, blockCol + col) == -1
                            && getPossibleValues(grid, blockRow + row, blockCol + col).contains(candidate)) {
                        rows.add(blockRow + row);
                        cols.add(blockCol + col);
                    }
                }
            }

            if (rows.size() == 1) {
                int uniqueRow = rows.iterator().next();
                for (int col = 0; col < 9; col++) {
                    if (col < blockCol || col >= blockCol + 3) {
                        getPossibleValues(grid, uniqueRow, col).remove(candidate);
                    }
                }
            } else if (cols.size() == 1) {
                int uniqueCol = cols.iterator().next();
                for (int row = 0; row < 9; row++) {
                    if (row < blockRow || row >= blockRow + 3) {
                        getPossibleValues(grid, row, uniqueCol).remove(candidate);
                    }
                }
            }
        }
    }

    private List<int[]> getIndexedCells(int index, String type) {
        List<int[]> cells = new ArrayList<>();
        switch (type) {
            case "row":
                for (int col = 0; col < 9; col++) {
                    cells.add(new int[]{index, col});
                }
                break;
            case "column":
                for (int row = 0; row < 9; row++) {
                    cells.add(new int[]{row, index});
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid type specified");
        }
        return cells;
    }

    private List<int[]> getIndexedCells(int startRow, int startCol, String type) {
        List<int[]> cells = new ArrayList<>();
        if ("block".equals(type)) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    cells.add(new int[]{startRow + row, startCol + col});
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid type specified for block retrieval");
        }
        return cells;
    }

    protected List<int[]> getEmptyCellsInGroup(SudokuGrid grid, List<int[]> cells) {
        return cells.stream()
                .filter(cell -> grid.getCellValue(cell[0], cell[1]) == -1)
                .collect(Collectors.toList());
    }
}
