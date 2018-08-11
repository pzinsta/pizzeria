package pzinsta.pizzeria.web.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import pzinsta.pizzeria.web.client.dto.File;

@Component
@FeignClient(
        name = "file-service",
        path = "/files",
        decode404 = true
)
public interface FileServiceClient {

    @PostMapping
    public File saveFile(@RequestBody byte[] file, @RequestParam("contentType") String contentType);

    // todo: optional
    @GetMapping("/{id}")
    public File getFile(@PathVariable("id") Long id);
}
