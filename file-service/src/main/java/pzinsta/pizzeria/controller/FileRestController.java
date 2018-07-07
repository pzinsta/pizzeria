package pzinsta.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pzinsta.pizzeria.model.File;
import pzinsta.pizzeria.service.FileStorageService;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequestMapping("/files")
public class FileRestController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(consumes = APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public File saveFile(@RequestBody byte[] file, @RequestParam(value = "contentType", defaultValue = APPLICATION_OCTET_STREAM_VALUE) String contentType) throws IOException {
        return fileStorageService.saveFile(new ByteArrayInputStream(file), contentType);
    }

    @GetMapping("/{fileId}")
    public File getFileById(@PathVariable("fileId") Long fileId) throws FileNotFoundException {
        return fileStorageService.getFileById(fileId).orElseThrow(FileNotFoundException::new);
    }
}
