package qb.sudoku.sudoku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sudoku")
public class SudokuController {

    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/play/{id}")
    public String getSudoku(Model model, @PathVariable int id){
        SudokuGrid sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        model.addAttribute("sudoku",sudokuGrid);
        return "play";
    }
}
