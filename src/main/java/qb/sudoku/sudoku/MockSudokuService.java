package qb.sudoku.sudoku;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MockSudokuService implements SudokuService {

    @Override
    public List<SudokuSignature> getSudokuSignatures() {
        return Arrays.asList(
                new SudokuSignature("An easy sudoku", 1, 12, LocalDateTime.now().minusDays(4)),
                new SudokuSignature("A medium sudoku", 2, 30, LocalDateTime.now().minusDays(3)),
                new SudokuSignature("A hard sudoku", 3, 40, LocalDateTime.now().minusDays(2))
        );
    }

    @Override
    public Optional<SudokuGrid> getUnsolvedSudokuById(long id) {
        if(id > 5) return Optional.empty();
        SudokuGrid grid = new TableBasedSudokuGrid();
        grid.addElement(1,1,9);
        grid.addElement(1, 2, 8);
        grid.addElement(1,3,7);
        return Optional.of(grid);
    }

    @Override
    public Optional<SudokuGrid> getSolvedSudokuById(long id) {
        return Optional.empty();
    }

    @Override
    public long addSudoku(String name, SudokuGrid unsolved, SudokuGrid solved) {
        return 0;
    }

    @Override
    public boolean addSolution(long id, SudokuGrid solved) {
        return false;
    }

    @Override
    public boolean deleteSudoku(long id) {
        return false;
    }

    @Override
    public boolean makeSudokuFavourite(long id) {
        return false;
    }


    @Override
    public SudokuVerificationStatus verify(long id, SudokuGrid sudokuGrid) {
        return null;
    }
}
