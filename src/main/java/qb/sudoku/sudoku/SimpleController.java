package qb.sudoku.sudoku;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simple")
public class SimpleController {

    @GetMapping
    public String doGet(){
        return "index";
    }
}
