package qb.sudoku.sudoku;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class MockSudokuService implements SudokuService {

    @Override
    public List<SudokuSignature> getSudokuSignatures() {
        List<SudokuSignature> sudokuSignatures = new LinkedList<>();
        sudokuSignatures.add(new SudokuSignature("An easy sudoku", 1, 5.0f, LocalDateTime.now().minusDays(4)));
        sudokuSignatures.add(new SudokuSignature("A medium sudoku", 2, 4.0f, LocalDateTime.now().minusDays(3)));
        sudokuSignatures.add(new SudokuSignature("A hard sudoku", 3, 3.0f, LocalDateTime.now().minusDays(2)));
        return sudokuSignatures;
    }

    @Override
    public SudokuGrid getUnsolvedSudokuById(long id) {
        if(id > 5) return null;
        SudokuGrid grid = new TableBasedSudokuGrid();
        grid.addElement(1,1,9);
        grid.addElement(1, 2, 8);
        grid.addElement(1,3,7);
        return grid;
    }

    @Override
    public SudokuGrid getSolvedSudokuById(long id) {
        return null;
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
    public boolean rateSudoku(long id, short rating) {
        return false;
    }

    @Override
    public SudokuVerificationStatus verify(long id, SudokuGrid sudokuGrid) {
        return null;
    }
}
