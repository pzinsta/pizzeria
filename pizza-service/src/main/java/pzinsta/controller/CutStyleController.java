package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.CutStyleNotFoundException;
import pzinsta.model.CutStyle;
import pzinsta.repository.CutStyleRepository;

@RestController
@RequestMapping("/cutStyles")
public class CutStyleController {
    private CutStyleRepository cutStyleRepository;

    @Autowired
    public CutStyleController(CutStyleRepository cutStyleRepository) {
        this.cutStyleRepository = cutStyleRepository;
    }

    @GetMapping
    public Iterable<CutStyle> findAll() {
        return cutStyleRepository.findAll();
    }

    @GetMapping("/{id}")
    public CutStyle findById(@PathVariable("id") Long id) {
        return cutStyleRepository.findById(id)
                .orElseThrow(() -> new CutStyleNotFoundException(id));
    }

    @ExceptionHandler(CutStyleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCutStyleNotFoundException(CutStyleNotFoundException exception) {
        return exception.getMessage();
    }
}
