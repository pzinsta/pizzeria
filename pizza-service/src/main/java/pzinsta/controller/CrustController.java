package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.model.Crust;
import pzinsta.repository.CrustRepository;

import java.util.Optional;

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
    public Optional<Crust> findById(@PathVariable("id") Long id) {
        return crustRepository.findById(id);
    }
}
