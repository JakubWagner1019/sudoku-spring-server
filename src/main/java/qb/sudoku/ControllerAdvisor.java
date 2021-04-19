package qb.sudoku;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class,IllegalArgumentException.class})
    public ModelAndView handleException(Exception e){
        logger.error(e.getMessage());
        ModelAndView modelAndView = new ModelAndView(Views.ERROR);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
