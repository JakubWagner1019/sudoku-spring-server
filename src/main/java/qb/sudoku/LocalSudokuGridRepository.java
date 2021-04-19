package qb.sudoku;

import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public class LocalSudokuGridRepository implements SudokuGridRepository {

    private final AtomicLong currentId = new AtomicLong();
    private final Map<Long, SudokuGrid> unsolvedMap = new HashMap<>();
    private final Map<Long, SudokuGrid> solvedMap = new HashMap<>();

    @Override
    public long save(@NonNull SudokuGrid unsolved, SudokuGrid solved) {
        Objects.requireNonNull(unsolved);
        long id = currentId.getAndIncrement();
        unsolvedMap.put(id, unsolved);
        if (solved != null) {
            if (!solved.completed()) {
                throw new IllegalArgumentException();
            }
            solvedMap.put(id, solved);
        }
        return id;
    }

    @Override
    public Optional<SudokuGrid> getSolved(long id) {
        return Optional.ofNullable(solvedMap.get(id));
    }

    @Override
    public Optional<SudokuGrid> getUnsolved(long id) {
        return Optional.ofNullable(unsolvedMap.get(id));
    }

    @Override
    public boolean update(long id, @NonNull SudokuGrid unsolved, SudokuGrid solved) {
        if (!unsolvedMap.containsKey(id)) {
            return false;
        }
        Objects.requireNonNull(unsolved);
        unsolvedMap.put(id, unsolved);
        if (solved != null) {
            if (!solved.completed()) {
                throw new IllegalArgumentException();
            }
            solvedMap.put(id, solved);
        }
        return true;
    }

    @Override
    public boolean delete(long id) {
        SudokuGrid removedItem = unsolvedMap.remove(id);
        solvedMap.remove(id);
        return removedItem != null;
    }
}
