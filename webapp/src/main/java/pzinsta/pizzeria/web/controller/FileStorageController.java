package pzinsta.pizzeria.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pzinsta.pizzeria.service.FileStorageService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Controller
@RequestMapping("/file/{name}")
public class FileStorageController {

    private FileStorageService fileStorageService;

    @Autowired
    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping
    public void getFile(@PathVariable("name") String name, HttpServletResponse httpServletResponse) {
        String contentType = fileStorageService.getContentTypeByName(name).orElse(APPLICATION_OCTET_STREAM_VALUE);
        httpServletResponse.setContentType(contentType);
        try {
            IOUtils.copy(fileStorageService.getFileAsInputStream(name), httpServletResponse.getOutputStream());
        } catch (IOException e) {
        }
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
}
