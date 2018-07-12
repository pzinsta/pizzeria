package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.BakeStyle;
import pzinsta.repository.BakeStyleRepository;

import java.util.Optional;

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
    public Optional<BakeStyle> findById(@PathVariable("id") Long id) {
        return bakeStyleRepository.findById(id);
    }
}
