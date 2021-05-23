package qb.sudoku.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import qb.sudoku.dto.SudokuGridDto;
import qb.sudoku.models.SudokuGrid;
import qb.sudoku.models.SudokuGridFactory;
import qb.sudoku.service.SudokuService;
import qb.sudoku.models.SudokuSignature;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final SudokuService sudokuService;
    private final SudokuGridFactory sudokuGridFactory;

    public AdminController(SudokuService sudokuService, SudokuGridFactory sudokuGridFactory) {
        this.sudokuService = sudokuService;
        this.sudokuGridFactory = sudokuGridFactory;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        List<SudokuSignature> sudokuSignatures = sudokuService.getSudokuSignatures();
        model.addAttribute("name", "Admin");
        model.addAttribute("sudokuList", sudokuSignatures);
        return Views.ADMIN;
    }

    @GetMapping("/sudoku/create")
    public String getCreatePage(Model model) {
        SudokuGridDto gridDto = new SudokuGridDto(9);
        model.addAttribute("form", gridDto);
        return Views.CREATE_SUDOKU;
    }

    @PostMapping("/sudoku/create")
    public String doCreate(@ModelAttribute("form") SudokuGridDto sudokuGridDto) {
        logger.info("DTO {}", sudokuGridDto);
        SudokuGrid solved = sudokuGridFactory.getSolvedGrid(sudokuGridDto);
        SudokuGrid unsolved = sudokuGridFactory.getUnsolvedGrid(sudokuGridDto);
        logger.info("Unsolved {}", unsolved);
        logger.info("Solved {}", solved);
        sudokuService.addSudoku(sudokuGridDto.getName(), unsolved, solved);
        return "redirect:/admin";
    }

    @GetMapping("/sudoku/edit/{id}")
    public String getEditPage(Model model, @PathVariable long id) {
        SudokuGrid sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        model.addAttribute("sudoku", sudokuGrid);
        return Views.EDIT_SUDOKU;
    }

    @GetMapping("/sudoku/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        SudokuGrid sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        model.addAttribute("sudoku", sudokuGrid);
        return Views.DELETE_SUDOKU;
    }

}
