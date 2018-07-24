package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.BakeStyleNotFoundException;
import pzinsta.model.BakeStyle;
import pzinsta.repository.BakeStyleRepository;

@RestController
@RequestMapping("/bakeStyles")
public class BakeStyleController {
    private BakeStyleRepository bakeStyleRepository;

    @Autowired
    public BakeStyleController(BakeStyleRepository bakeStyleRepository) {
        this.bakeStyleRepository = bakeStyleRepository;
    }

    @GetMapping
    public Iterable<BakeStyle> findAll() {
        return bakeStyleRepository.findAll();
    }

    @GetMapping("/{id}")
    public BakeStyle findById(@PathVariable("id") Long id) {
        return bakeStyleRepository.findById(id)
                .orElseThrow(() -> new BakeStyleNotFoundException(id));
    }

    @ExceptionHandler(BakeStyleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBakeStyleNotFoundException(BakeStyleNotFoundException exception) {
        return exception.getMessage();
    }
}
