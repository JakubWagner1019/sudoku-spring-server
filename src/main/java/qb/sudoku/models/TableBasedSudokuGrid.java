package qb.sudoku.models;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TableBasedSudokuGrid implements SudokuGrid {
    private final int sideLength;
    private final Table<Integer, Integer, Integer> grid = TreeBasedTable.create();

    public TableBasedSudokuGrid(int sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public void addElement(int row, int column, int value) {
        // TODO: 11/04/2021 add row, column, value check
        grid.put(row, column, value);
    }

    @Override
    public Integer getElement(int row, int column) {
        // TODO: 11/04/2021 add row, column check
        return grid.get(row, column);
    }

    @Override
    public List<Integer> getRow(int rowNumber) {
        // TODO: 11/04/2021 add row check
        List<Integer> intList = new LinkedList<>();
        Map<Integer, Integer> row = grid.row(rowNumber);
        for (int i = 1; i <= this.sideLength; i++) {
            intList.add(row.get(i));
        }
        return intList;
    }

    @Override
    public boolean completed() {
        for (int i = 1; i <= this.sideLength; i++) {
            for (int j = 1; j <= this.sideLength; j++) {
                Integer integer = grid.get(i, j);
                if (integer == null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int getSideLength() {
        return this.sideLength;
    }

    @Override
    public String toString() {
        return "TableBasedSudokuGrid{" +
                "grid=" + grid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableBasedSudokuGrid that = (TableBasedSudokuGrid) o;
        return grid.equals(that.grid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid);
    }
}
