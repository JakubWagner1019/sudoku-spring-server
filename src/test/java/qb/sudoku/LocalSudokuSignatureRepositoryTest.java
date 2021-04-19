package qb.sudoku;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class LocalSudokuSignatureRepositoryTest {

    @Test
    public void whenCreated_thenCanBeRetrieved(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id,"test");
        Optional<SudokuSignature> sudokuSignature = signatureRepository.get(id);
        assertTrue("Signature is empty optional",sudokuSignature.isPresent());
        assertEquals(id, sudokuSignature.get().getId());
        assertEquals("test", sudokuSignature.get().getName());
        assertEquals(0, sudokuSignature.get().getFavourites());
        assertEquals(LocalDateTime.now().toLocalDate(),sudokuSignature.get().getLocalDateTime().toLocalDate());
    }

    @Test
    public void givenIdAlreadyExists_whenCreate_thenThrowsIllegalArgumentException(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id,"test");
        try {
            signatureRepository.create(id, "test2");
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenNullName_whenCreate_thenThrowsIllegalArgumentException(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.create(0L, null);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenEmptyName_whenCreate_thenThrowsIllegalArgumentException(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.create(0L, "");
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenSignatureDoesNotExist_whenGetById_thenReturnsEmptyOptional(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        Optional<SudokuSignature> sudokuSignature = signatureRepository.get(0L);
        assertEquals(Optional.empty(), sudokuSignature);
    }

    @Test
    public void givenThreeElementsSaved_whenGetAll_thenThreeElementsAreReturned(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        signatureRepository.create(0L,"test");
        signatureRepository.create(1L, "test2");
        signatureRepository.create(2L, "test3");
        List<SudokuSignature> all = signatureRepository.getAll();
        assertEquals(3, all.size());
    }

    @Test
    public void whenDeleted_thenElementNoLongerCanBeRetrieved(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id,"test");
        //sanity check
        assertTrue("Element was not saved", signatureRepository.get(id).isPresent());
        signatureRepository.delete(id);
        Optional<SudokuSignature> sudokuSignature = signatureRepository.get(id);
        assertEquals(Optional.empty(), sudokuSignature);
    }

    @Test
    public void givenElementDidntExistBefore_whenDeleted_thenThrowsNoSuchElement(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.delete(0L);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof NoSuchElementException);
        }
    }

    @Test
    public void whenRenamed_thenNameChanges(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        signatureRepository.create(0L, "test");
        signatureRepository.rename(0L, "test2");
        Optional<SudokuSignature> sudokuSignature = signatureRepository.get(0L);
        assertTrue("After rename element is not present", sudokuSignature.isPresent());
        assertEquals("test2", sudokuSignature.get().getName());
    }

    @Test
    public void givenElementDidntExistBefore_whenRenamed_thenThrowsNoSuchElement(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.rename(0L, "test2");
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof NoSuchElementException);
        }
    }

    @Test
    public void givenNullName_whenRename_thenThrowsIllegalArgumentException(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id,"test");
        try {
            signatureRepository.rename(id, null);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenEmptyName_whenRename_thenThrowsIllegalArgumentException(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id,"test");
        try {
            signatureRepository.rename(id, "");
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void whenAddFavourite_thenFavouriteIncrements(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id, "test");
        assertEquals(0,signatureRepository.get(id).get().getFavourites());
        signatureRepository.addFavourite(id);
        assertEquals(1,signatureRepository.get(id).get().getFavourites());
        signatureRepository.addFavourite(id);
        assertEquals(2,signatureRepository.get(id).get().getFavourites());
    }

    @Test
    public void givenElementDoesntExist_whenAddFavourite_thenThrowsNoSuchElement(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.addFavourite(0L);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof NoSuchElementException);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void whenRemoveFavourite_thenFavouriteDecrements(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id, "test");
        assertEquals(0,signatureRepository.get(id).get().getFavourites());
        signatureRepository.addFavourite(id);
        assertEquals(1,signatureRepository.get(id).get().getFavourites());
        signatureRepository.removeFavourite(id);
        assertEquals(0,signatureRepository.get(id).get().getFavourites());
    }

    @Test
    public void givenElementDoesntExist_whenRemoveFavourite_thenThrowsNoSuchElement(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        try {
            signatureRepository.removeFavourite(0L);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof NoSuchElementException);
        }
    }

    @Test
    public void givenElementExistAndFavouritesIsZero_whenRemoveFavourite_thenThrowsIllegalState(){
        SudokuSignatureRepository signatureRepository = new LocalSudokuSignatureRepository();
        long id = 0L;
        signatureRepository.create(id, "test");
        try {
            signatureRepository.removeFavourite(0L);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalStateException);
        }
    }

}