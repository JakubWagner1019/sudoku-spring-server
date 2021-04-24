package qb.sudoku.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import qb.sudoku.models.SudokuGrid;
import qb.sudoku.service.SudokuService;

@Controller
@RequestMapping("/sudoku")
public class PlayerController {

    private final SudokuService sudokuService;

    public PlayerController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/play/{id}")
    public String getSudoku(Model model, @PathVariable int id){
        SudokuGrid sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        model.addAttribute("sudoku",sudokuGrid);
        return Views.PLAY;
    }

}
