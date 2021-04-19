package qb.sudoku;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class LocalSudokuGridRepositoryTest {

    private static SudokuGrid testGrid;
    private static SudokuGrid testGrid2;
    private static SudokuGrid testGrid3;


    @BeforeClass
    public static void beforeClass(){
        testGrid = new TableBasedSudokuGrid(1);
        testGrid2 = new TableBasedSudokuGrid(1);
        testGrid2.addElement(1,1,2);
        testGrid3 = new TableBasedSudokuGrid(1);
        testGrid3.addElement(1,1,3);
    }

    @Test
    public void givenEmptyRepository_whenGetUnsolved_thenReturnsEmptyOptional(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        Optional<SudokuGrid> unsolved = repository.getUnsolved(0);
        assertEquals(Optional.empty(), unsolved);
    }

    @Test
    public void whenSudokuIsBeingSaved_thenSaveReturnValueIncrements(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long save = repository.save(testGrid, null);
        long save1 = repository.save(testGrid2, null);
        long save2 = repository.save(testGrid3, null);
        assertEquals(0, save);
        assertEquals(1, save1);
        assertEquals(2,save2);
    }

    @Test
    public void whenUnsolvedIsBeingSaved_thenUnsolvedCanBeRetrieved(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long save = repository.save(testGrid, null);
        Optional<SudokuGrid> unsolved = repository.getUnsolved(save);
        assertTrue("Repository returned empty Optional",unsolved.isPresent());
        assertEquals(testGrid, unsolved.get());
    }

    @Test
    public void whenSolvedIsBeingSaved_thenSolvedCanBeRetrieved(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long id = repository.save(testGrid, testGrid2);
        Optional<SudokuGrid> solved = repository.getSolved(id);
        assertTrue(solved.isPresent());
        assertEquals(testGrid2, solved.get());
    }

    @Test
    public void whenUpdated_thenSudokuChanges(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long id = repository.save(testGrid, null);
        boolean update = repository.update(id, testGrid2, null);
        assertTrue(update);
        Optional<SudokuGrid> unsolved = repository.getUnsolved(id);
        assertTrue(unsolved.isPresent());
        assertEquals(testGrid2, unsolved.get());
    }

    @Test
    public void givenNullUnsolved_whenUpdated_thenThrowsNPE(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long id = repository.save(testGrid, null);
        try {
            repository.update(id, null, null);
            fail();
        } catch (Exception e) {
            assertEquals(NullPointerException.class, e.getClass());
        }
    }

    @Test
    public void whenSudokuDeleted_thenItDoesNotExist(){
        SudokuGridRepository repository = new LocalSudokuGridRepository();
        long id = repository.save(testGrid, null);
        Optional<SudokuGrid> beforeDelete = repository.getUnsolved(id);
        assertTrue("Grid was not added, can't rely on this test", beforeDelete.isPresent());
        boolean delete = repository.delete(id);
        assertTrue("Delete returned false for existing item",delete);
        Optional<SudokuGrid> afterDelete = repository.getUnsolved(id);
        assertFalse("Grid was not removed",afterDelete.isPresent());
    }

}