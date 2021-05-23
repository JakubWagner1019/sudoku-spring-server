package qb.sudoku.dto;

import java.util.Arrays;
import java.util.Objects;

// TODO: 22/05/2021 Make immutable
public final class SudokuGridDto {

    private String name;
    private SudokuCellDto[][] grid;

    public SudokuGridDto() {
    }

    public SudokuGridDto(int size) {
        grid = new SudokuCellDto[size][];
        for (int i = 0; i < size; i++) {
            grid[i] = new SudokuCellDto[size];
            for (int j = 0; j < size; j++) {
                grid[i][j] = new SudokuCellDto();
            }
        }
    }

    public SudokuCellDto[][] getGrid() {
        return grid;
    }

    public void setGrid(SudokuCellDto[][] grid) {
        this.grid = grid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuGridDto gridDto = (SudokuGridDto) o;
        return Objects.equals(name, gridDto.name) && Arrays.equals(grid, gridDto.grid);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(grid);
        return result;
    }

    @Override
    public String toString() {
        return "SudokuGridDto{" +
                "name='" + name + '\'' +
                ", grid=" + Arrays.toString(grid) +
                '}';
    }

    public static final class SudokuCellDto {
        private int value;
        private boolean given;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean getGiven() {
            return given;
        }

        public void setGiven(boolean given) {
            this.given = given;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SudokuCellDto that = (SudokuCellDto) o;
            return value == that.value && given == that.given;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, given);
        }

        @Override
        public String toString() {
            return "SudokuCellDto{" +
                    "value=" + value +
                    ", given=" + given +
                    '}';
        }
    }
}
