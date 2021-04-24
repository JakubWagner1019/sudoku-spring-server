package qb.sudoku.models;

public class SudokuUtils {
    private SudokuUtils(){
    }

    public static SudokuGrid getUnsolved(String[] number, String[] given) {
        if (number.length != given.length) {
            throw new IllegalArgumentException("Unequal lengths of number[] and given[]");
        }

        return null;
    }

    public static SudokuGrid getSolved(String[] number, String[] given){
        if (number.length != given.length) {
            throw new IllegalArgumentException("Unequal lengths of number[] and given[]");
        }

        return null;
    }


}
