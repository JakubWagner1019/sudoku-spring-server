package qb.sudoku.sudoku;

import java.util.List;
import java.util.Optional;

public interface SudokuService {

    /**
     * @return list of sudoku signatures (id, name, rating and date of creation)
     */
    // TODO: 11/04/2021 add ordering and pagination
    List<SudokuSignature> getSudokuSignatures();

    /**
     * @param id Sudoku's id, usually provided by SudokuSignature.getId() or addSudoku(String,Sudoku)
     * @return Sudoku's grid (only given digits)
     */
    Optional<SudokuGrid> getUnsolvedSudokuById(long id);

    /**
     * @param id Sudoku's id, usually provided by SudokuSignature.getId() or addSudoku(String,Sudoku)
     * @return Sudoku's grid (all)
     */
    Optional<SudokuGrid> getSolvedSudokuById(long id);

    /**
     * @param name Name that will be sudoku's signature
     * @param unsolved Sudoku grid with given digits
     * @param solved Sudoku grid with all digits, null if unknown
     * @return ID of newly added sudoku
     */
    long addSudoku(String name, SudokuGrid unsolved, SudokuGrid solved);

    /**
     * @param id Id of sudoku for which the solution is submitted
     * @param solved Solution's sudoku grid
     * @return True if operation successful, false if not
     */
    boolean addSolution(long id, SudokuGrid solved);


    /**
     * @param id Id of sudoku to be deleted
     * @return True if operation successful, false if not
     */
    boolean deleteSudoku(long id);

    /**
     * @param id Id of sudoku to be added to favourites
     * @return True if operation successful, false if not
     */
    boolean makeSudokuFavourite(long id);


    /**
     * @param id Id of sudoku to be verified
     * @param sudokuGrid Sudoku's grid to be verified
     * @return Whether the sudoku is good
     */
    SudokuVerificationStatus verify(long id, SudokuGrid sudokuGrid);
}
