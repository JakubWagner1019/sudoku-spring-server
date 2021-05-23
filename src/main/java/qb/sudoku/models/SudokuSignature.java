package qb.sudoku.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SudokuSignature {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm d/M/uuuu");

    private final String name;
    private final long id;
    private final int favourites;
    private final LocalDateTime localDateTime;

    public SudokuSignature(String name, long id) {
        this(name, id, 0, LocalDateTime.now());
    }

    public SudokuSignature(String name, long id, int favourites, LocalDateTime localDateTime) {
        this.name = name;
        this.id = id;
        this.favourites = favourites;
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public int getFavourites() {
        return favourites;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getFormattedLocalDateTime() {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }
}
