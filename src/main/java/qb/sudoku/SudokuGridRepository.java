package qb.sudoku;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface SudokuGridRepository {
    //CRUD for solved and unsolved
    long save(@NonNull SudokuGrid unsolved, SudokuGrid solved);
    Optional<SudokuGrid> getSolved(long id);
    Optional<SudokuGrid> getUnsolved(long id);
    boolean update(long id, @NonNull SudokuGrid unsolved, SudokuGrid solved);
    boolean delete(long id);
}
