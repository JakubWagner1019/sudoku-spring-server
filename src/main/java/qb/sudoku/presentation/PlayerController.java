package qb.sudoku.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/verify/{id}")
    public String verifySudoku(@RequestParam(name = "number[]") String[] number, Model model, @PathVariable int id){
        throw new UnsupportedOperationException("Not implemented yet");
        //sudokuService.verify(id, )
    }

}
