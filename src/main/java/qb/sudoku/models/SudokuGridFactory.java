package qb.sudoku.models;

import qb.sudoku.dto.SudokuGridDto;

public interface SudokuGridFactory {
    SudokuGrid getUnsolvedGrid(SudokuGridDto gridDto);

    SudokuGrid getSolvedGrid(SudokuGridDto gridDto);
}

