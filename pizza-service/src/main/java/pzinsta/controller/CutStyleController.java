package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.CutStyle;
import pzinsta.repository.CutStyleRepository;

import java.util.Optional;

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
    public Optional<CutStyle> findById(@PathVariable("id") Long id) {
        return cutStyleRepository.findById(id);
    }
}
