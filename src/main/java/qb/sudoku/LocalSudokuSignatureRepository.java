package qb.sudoku;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Profile("dev")
public class LocalSudokuSignatureRepository implements SudokuSignatureRepository {

    Map<Long, SudokuSignature> signatureStorage = new HashMap<>();

    @Override
    public void create(long id, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tried creating signature with null/empty name");
        }
        if (signatureStorage.containsKey(id)){
            throw new IllegalArgumentException("Id already exists");
        }
        SudokuSignature sudokuSignature = new SudokuSignature(name, id);
        signatureStorage.put(id, sudokuSignature);
    }

    @Override
    public Optional<SudokuSignature> get(long id) {
        return Optional.ofNullable(signatureStorage.get(id));
    }

    @Override
    public List<SudokuSignature> getAll() {
        return new ArrayList<>(signatureStorage.values());
    }

    @Override
    public void delete(long id) {
        if (!signatureStorage.containsKey(id)){
            throw new NoSuchElementException("Tried deleting nonexistent signature");
        }
        signatureStorage.remove(id);
    }

    @Override
    public void rename(long id, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Tried renaming to null/empty name");
        }
        Optional<SudokuSignature> sudokuSignature = get(id);
        if (!sudokuSignature.isPresent()) {
            throw new NoSuchElementException("Tried renaming nonexistent signature");
        }
        SudokuSignature oldSignature = sudokuSignature.get();
        SudokuSignature newSignature = new SudokuSignature(
                name,
                id,
                oldSignature.getFavourites(),
                oldSignature.getLocalDateTime());
        signatureStorage.put(id, newSignature);
    }

    @Override
    public void addFavourite(long id) {
        Optional<SudokuSignature> sudokuSignature = get(id);
        if (!sudokuSignature.isPresent()) {
            throw new NoSuchElementException("Tried adding favourite to nonexistent signature");
        }
        SudokuSignature oldSignature = sudokuSignature.get();
        SudokuSignature newSignature = new SudokuSignature(
                oldSignature.getName(),
                id,
                oldSignature.getFavourites() + 1,
                oldSignature.getLocalDateTime());
        signatureStorage.put(id, newSignature);
    }

    @Override
    public void removeFavourite(long id) {
        Optional<SudokuSignature> sudokuSignature = get(id);
        if (!sudokuSignature.isPresent()) {
            throw new NoSuchElementException("Tried removing favourite from nonexistent signature");
        }
        SudokuSignature oldSignature = sudokuSignature.get();
        if (oldSignature.getFavourites() == 0) {
            throw new IllegalStateException("Tried removing favourite when it was already 0");
        }
        SudokuSignature newSignature = new SudokuSignature(
                oldSignature.getName(),
                id,
                oldSignature.getFavourites() - 1,
                oldSignature.getLocalDateTime());
        signatureStorage.put(id, newSignature);
    }
}
