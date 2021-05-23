package qb.sudoku.repositories;

import qb.sudoku.models.SudokuSignature;

import java.util.List;
import java.util.Optional;

public interface SudokuSignatureRepository {
    //CRUD
    void create(long id, String name);

    Optional<SudokuSignature> get(long id);

    List<SudokuSignature> getAll();

    void delete(long id);

    void rename(long id, String name);

    void addFavourite(long id);

    void removeFavourite(long id);
}
