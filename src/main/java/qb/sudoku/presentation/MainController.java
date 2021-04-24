package qb.sudoku.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qb.sudoku.service.SudokuService;
import qb.sudoku.models.SudokuSignature;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final SudokuService sudokuService;

    public MainController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @GetMapping
    public String doGet(Model model){
        List<SudokuSignature> sudokuSignatures = sudokuService.getSudokuSignatures();
        model.addAttribute("name","Sir");
        model.addAttribute("sudokuList",sudokuSignatures);
        return Views.INDEX;
    }
}
