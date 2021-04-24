package qb.sudoku.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import qb.sudoku.models.SudokuGrid;
import qb.sudoku.service.SudokuService;
import qb.sudoku.models.SudokuSignature;
import qb.sudoku.models.TableBasedSudokuGrid;

import java.util.Arrays;
import java.util.List;

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
    public String doCreate(@RequestParam(name = "name") String name, @RequestParam(name = "number[]") String[] number, @RequestParam(name = "given[]") String[] given) {
        logger.info("Creating");
        logger.info(name);
        logger.info("{}", Arrays.asList(number));
        logger.info("{}", Arrays.asList(given));
        SudokuGrid unsolved = convert(number);
        sudokuService.addSudoku(name, unsolved, null);
        return "redirect:/admin";
    }

    // TODO: 14/04/2021 move somewhere else
    private SudokuGrid convert(String[] params) throws IllegalArgumentException, NumberFormatException {
        if (params.length != 81) {
            throw new IllegalArgumentException("There needs to be 81 elements in the array creating a sudoku");
        }
        SudokuGrid grid = new TableBasedSudokuGrid(9);
        for (int i = 0; i < grid.getSideLength(); i++) {
            for (int j = 0; j < grid.getSideLength(); j++) {
                String param = params[i * grid.getSideLength() + j];
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
