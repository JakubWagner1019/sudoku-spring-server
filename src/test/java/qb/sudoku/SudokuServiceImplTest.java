package qb.sudoku;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SudokuServiceImplTest {

    @Mock
    private SudokuGridRepository gridRepository;
    @Mock
    private SudokuSignatureRepository signatureRepository;

    private static final SudokuGrid smallSolved = new TableBasedSudokuGrid(1);
    private static final SudokuGrid smallUnsolved = new TableBasedSudokuGrid(1);

    @BeforeClass
    public static void beforeClass(){
        smallSolved.addElement(1,1,1);
    }

    @Test
    public void whenGetSudokuSignatures_thenReturnsValueFromRepository(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        List<SudokuSignature> signatureList = Collections.singletonList(new SudokuSignature("test", 0L));
        when(signatureRepository.getAll()).thenReturn(signatureList);
        List<SudokuSignature> sudokuSignatures = sudokuService.getSudokuSignatures();
        assertEquals(signatureList, sudokuSignatures);
    }

    @Test
    public void givenUnsolvedDoesntExist_whenGetUnsolvedSudokuById_thenThrowsNoSuchElement(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(0)).thenReturn(Optional.empty());
        try {
            sudokuService.getUnsolvedSudokuById(0);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(),e instanceof NoSuchElementException);
        }
    }


    @Test
    public void givenUnsolvedExists_whenGetUnsolvedSudokuById_thenReturnsValueFromRepository(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(0)).thenReturn(Optional.of(smallUnsolved));
        SudokuGrid unsolved = sudokuService.getUnsolvedSudokuById(0);
        assertEquals(smallUnsolved, unsolved);
    }

    @Test
    public void givenSolvedDoesntExist_whenGetSolvedSudokuById_thenReturnsEmptyOptional(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getSolved(0)).thenReturn(Optional.empty());
        Optional<SudokuGrid> solved = sudokuService.getSolvedSudokuById(0);
        assertFalse("Returned non-empty optional",solved.isPresent());
        assertEquals(Optional.empty(), solved);
    }


    @Test
    public void givenSolvedExists_whenGetSolvedSudokuById_thenReturnsValueFromRepository(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getSolved(0)).thenReturn(Optional.of(smallSolved));
        Optional<SudokuGrid> solved = sudokuService.getSolvedSudokuById(0);
        assertTrue("Empty optional was returned",solved.isPresent());
        assertEquals(smallSolved, solved.get());
    }


    @Test
    public void givenNonNullUnsolved_whenAddSudoku_repositoriesSaveMethodsAreInvoked(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.save(any(),any())).thenReturn(0L);
        sudokuService.addSudoku("test", smallUnsolved, null);
        verify(gridRepository, times(1)).save(smallUnsolved, null);
        verify(signatureRepository, times(1)).create(0L,"test");
    }

    @Test
    public void givenNonNullUnsolved_whenAddSudoku_thenReturnsIdFromRepository(){
        when(gridRepository.save(smallUnsolved,null)).thenReturn(5L);
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        long id = sudokuService.addSudoku("test", smallUnsolved, null);
        assertEquals(5L, id);
    }

    @Test
    public void givenNullUnsolved_whenAddSudoku_thenThrowsNPE(){
        SudokuService sudokuService = new SudokuServiceImpl(null, null);
        try {
            sudokuService.addSudoku("test", null, null);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(),e instanceof NullPointerException);
        }
    }

    @Test
    public void givenNullUnsolved_whenAddSudoku_thenNoInteractionsWithRepositories(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        try {
            sudokuService.addSudoku("test", null, null);
        } catch (Exception ignored) {
        }
        verifyNoInteractions(gridRepository);
        verifyNoInteractions(signatureRepository);
    }


    @Test
    public void givenIncompleteSolved_whenAddSudoku_thenThrowsIllegalArgumentException(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        try {
            sudokuService.addSudoku("test", smallUnsolved, smallUnsolved);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(), e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenCorrectArguments_whenAddSolution_thenRepositoryMethodIsCalled(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(0)).thenReturn(Optional.of(smallUnsolved));
        sudokuService.addSolution(0L, smallSolved);
        verify(gridRepository,times(1)).update(0L, smallUnsolved, smallSolved);
    }

    @Test
    public void givenWrongId_whenAddSolution_thenThrowsNoSuchElementException(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(5)).thenReturn(Optional.empty());
        try {
            sudokuService.addSolution(5, smallSolved);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(),e instanceof NoSuchElementException);
        }
    }

    @Test
    public void givenWrongId_whenAddSolution_thenRepositoryUpdateIsNotCalled(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(5)).thenReturn(Optional.empty());
        try {
            sudokuService.addSolution(5, smallSolved);
        } catch (Exception ignored) {
        }
        verify(gridRepository, times(0)).update(anyLong(),any(),any());
    }

    @Test
    public void givenIncompleteSolution_whenAddSolution_thenThrowsIllegalArgumentException(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(5)).thenReturn(Optional.of(smallUnsolved));
        try {
            sudokuService.addSolution(5, smallUnsolved);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(),e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void givenIncompleteSolution_whenAddSolution_thenRepositoryUpdateIsNotCalled(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.getUnsolved(5)).thenReturn(Optional.of(smallUnsolved));
        try {
            sudokuService.addSolution(5, smallUnsolved);
        } catch (Exception ignored) {
        }
        verify(gridRepository, times(0)).update(anyLong(),any(),any());
    }

    // TODO: 19/04/2021 Think of quick check before removing
    @Test
    public void givenRepositoryReturnsTrue_whenDeleteSudoku_thenNoExceptionIsThrown(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.delete(0L)).thenReturn(true);
        try {
            sudokuService.deleteSudoku(0L);
        } catch (Exception e) {
            fail();
        }
        verify(gridRepository,times(1)).delete(0L);
        verify(signatureRepository, times(1)).delete(0L);
    }

    @Test
    public void givenRepositoryReturnsFalse_whenDeleteSudoku_thenThrowsNoSuchElement(){
        SudokuService sudokuService = new SudokuServiceImpl(gridRepository, signatureRepository);
        when(gridRepository.delete(0L)).thenReturn(false);
        try {
            sudokuService.deleteSudoku(0L);
            fail();
        } catch (Exception e) {
            assertTrue(e.getClass().getSimpleName(),e instanceof NoSuchElementException);
        }
        verify(gridRepository,times(1)).delete(0L);
        verify(signatureRepository, times(0)).delete(0L);
    }


}