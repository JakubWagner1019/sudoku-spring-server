package qb.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SudokuServiceImpl implements SudokuService {

    Logger logger = LoggerFactory.getLogger(SudokuServiceImpl.class);

    private final SudokuGridRepository gridRepository;
    private final SudokuSignatureRepository signatureRepository;

    public SudokuServiceImpl(SudokuGridRepository gridRepository, SudokuSignatureRepository signatureRepository) {
        this.gridRepository = gridRepository;
        this.signatureRepository = signatureRepository;
    }

    @Override
    public List<SudokuSignature> getSudokuSignatures() {
        return signatureRepository.getAll();
    }

    @Override
    public SudokuGrid getUnsolvedSudokuById(long id) {
        Optional<SudokuGrid> unsolved = gridRepository.getUnsolved(id);
        return unsolved.orElseThrow(() -> new NoSuchElementException(String.format("No sudoku with id=%d", id)));
    }

    @Override
    public Optional<SudokuGrid> getSolvedSudokuById(long id) {
        Optional<SudokuGrid> solved = gridRepository.getSolved(id);
        logger.debug("getSolvedSudokuById{}={}", id, solved);
        return solved;
    }

    @Override
    public long addSudoku(String name, SudokuGrid unsolved, SudokuGrid solved) {
        logger.info("Adding sudoku name={}, unsolved={}, solved={}", name, unsolved, solved);
        if (unsolved == null) {
            throw new NullPointerException("Tried adding null unsolved");
        }
        if (solved != null && !solved.completed()) {
            throw new IllegalArgumentException("Tried adding incomplete solution");
        }
        long save = gridRepository.save(unsolved, solved);
        signatureRepository.create(save, name);
        return save;
    }

    @Override
    public void addSolution(long id, SudokuGrid solved) {
        Optional<SudokuGrid> unsolvedOptional = gridRepository.getUnsolved(id);
        if (!solved.completed()) {
            throw new IllegalArgumentException("Tried adding incomplete solution");
        }
        SudokuGrid unsolved = unsolvedOptional.orElseThrow(
                () -> new NoSuchElementException("Tried adding solution to nonexistent sudoku"));
        gridRepository.update(id, unsolved, solved);
    }

    @Override
    public void deleteSudoku(long id) {
        boolean success = gridRepository.delete(id);
        if (!success) {
            throw new NoSuchElementException("Tried deleting non-existent sudoku");
        }
        signatureRepository.delete(id);
    }

    @Override
    public void makeSudokuFavourite(long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public SudokuVerificationStatus verify(long id, SudokuGrid sudokuGrid) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
