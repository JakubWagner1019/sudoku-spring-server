package qb.sudoku.models;

import org.springframework.stereotype.Component;
import qb.sudoku.dto.SudokuGridDto;

@Component
public class TableBasedSudokuGridFactory implements SudokuGridFactory {

    @Override
    public SudokuGrid getUnsolvedGrid(SudokuGridDto gridDto) {
        int sideLength = gridDto.getGrid().length;
        SudokuGrid grid = getNewSudokuGrid(sideLength);
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                SudokuGridDto.SudokuCellDto cell = gridDto.getGrid()[i][j];
                if (cell.getValue() != 0 && cell.getGiven()) {
                    grid.addElement(i + 1, j + 1, cell.getValue());
                }
            }
        }
        return grid;
    }

    @Override
    public SudokuGrid getSolvedGrid(SudokuGridDto gridDto) {
        int sideLength = gridDto.getGrid().length;
        SudokuGrid grid = getNewSudokuGrid(sideLength);
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                SudokuGridDto.SudokuCellDto cell = gridDto.getGrid()[i][j];
                if (cell.getValue() != 0) {
                    grid.addElement(i + 1, j + 1, cell.getValue());
                } else {
                    return null;
                }
            }
        }
        return grid;
    }

    private static SudokuGrid getNewSudokuGrid(int sideLength) {
        return new TableBasedSudokuGrid(sideLength);
    }

}
