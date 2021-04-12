package qb.sudoku.sudoku;

import java.util.List;

public interface SudokuGrid {
    void addElement(int row, int column, int value);
    Integer getElement(int row, int column);
    List<Integer> getRow(int row);

}
