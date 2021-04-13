package qb.sudoku.sudoku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/sudoku")
public class PlayerController {

    private final SudokuService sudokuService;

    public PlayerController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping("/play/{id}")
    public String getSudoku(Model model, @PathVariable int id){
        Optional<SudokuGrid> sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        if (sudokuGrid.isPresent()) {
            model.addAttribute("sudoku",sudokuGrid.get());
            return Views.PLAY;
        }
        model.addAttribute("error", String.format("No sudoku with id: %d",id));
        return Views.ERROR;
    }
}
