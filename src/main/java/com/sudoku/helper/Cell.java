package com.sudoku.helper;

public class Cell {
    private int value;

    public Cell() {
        this.value = -1;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

