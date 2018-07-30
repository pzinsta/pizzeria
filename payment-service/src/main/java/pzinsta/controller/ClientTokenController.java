package pzinsta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.service.ClientTokenGenerationService;

@RestController("/clientToken")
public class ClientTokenController {

    private ClientTokenGenerationService clientTokenGenerationService;

    @Autowired
    public ClientTokenController(ClientTokenGenerationService clientTokenGenerationService) {
        this.clientTokenGenerationService = clientTokenGenerationService;
    }

    @GetMapping
    public String generateClientToken() {
        return clientTokenGenerationService.generateClientToken();
    }

}
