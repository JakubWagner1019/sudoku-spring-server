package qb.sudoku.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final SudokuService sudokuService;

    public AdminController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
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
        return Views.CREATE_SUDOKU;
    }

    @PostMapping("/sudoku/create")
    public String doCreate(@RequestParam(name = "name") String name, @RequestParam(name = "param[]") String[] params) {
        logger.info("Creating");
        logger.info(name);
        logger.info("{}", Arrays.asList(params));
        SudokuGrid unsolved = convert(params);
        sudokuService.addSudoku(name, unsolved, null);
        return "redirect:/admin";
    }

    // TODO: 14/04/2021 move somewhere else
    private SudokuGrid convert(String[] params) throws IllegalArgumentException, NumberFormatException {
        if (params.length != 81) {
            throw new IllegalArgumentException("There needs to be 81 elements in the array creating a sudoku");
        }
        SudokuGrid grid = new TableBasedSudokuGrid();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String param = params[i * 9 + j];
                if (!param.equals("")) {
                    int parsedInt = Integer.parseInt(param);
                    grid.addElement(i + 1, j + 1, parsedInt);
                }
            }
        }
        return grid;
    }

    @GetMapping("/sudoku/edit/{id}")
    public String getEditPage(Model model, @PathVariable long id) {
        Optional<SudokuGrid> sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        if (sudokuGrid.isPresent()) {
            model.addAttribute("sudoku", sudokuGrid.get());
            return Views.EDIT_SUDOKU;
        }
        model.addAttribute("error", String.format("No sudoku with id: %d", id));
        return Views.ERROR;
    }

    @GetMapping("/sudoku/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        Optional<SudokuGrid> sudokuGrid = sudokuService.getUnsolvedSudokuById(id);
        if (sudokuGrid.isPresent()) {
            model.addAttribute("sudoku", sudokuGrid.get());
            return Views.DELETE_SUDOKU;
        }
        model.addAttribute("error", String.format("No sudoku with id: %d", id));
        return Views.ERROR;
    }

}
