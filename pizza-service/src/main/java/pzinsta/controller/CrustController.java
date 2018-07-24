package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.exception.CrustNotFoundException;
import pzinsta.model.Crust;
import pzinsta.repository.CrustRepository;

@RestController
@RequestMapping("/crusts")
public class CrustController {
    private CrustRepository crustRepository;

    @Autowired
    public CrustController(CrustRepository crustRepository) {
        this.crustRepository = crustRepository;
    }

    @GetMapping
    public Iterable<Crust> findAll() {
        return crustRepository.findAll();
    }

    @GetMapping("/{id}")
    public Crust findById(@PathVariable("id") Long id) {
        return crustRepository.findById(id)
                .orElseThrow(() -> new CrustNotFoundException(id));
    }

    @ExceptionHandler(CrustNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCrustNotFoundException(CrustNotFoundException exception) {
        return exception.getMessage();
    }
}
