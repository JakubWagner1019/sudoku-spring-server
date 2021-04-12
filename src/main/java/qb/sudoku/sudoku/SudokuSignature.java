package qb.sudoku.sudoku;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SudokuSignature {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm d/M/uuuu");

    private final String name;
    private final long id;
    private final float rating;
    private final LocalDateTime localDateTime;

    public SudokuSignature(String name, long id, float rating, LocalDateTime localDateTime) {
        this.name = name;
        this.id = id;
        this.rating = rating;
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public float getRating() {
        return rating;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getFormattedLocalDateTime(){
        return localDateTime.format(DATE_TIME_FORMATTER);
    }
}
