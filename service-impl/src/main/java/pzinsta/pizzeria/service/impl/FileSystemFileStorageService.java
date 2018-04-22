package pzinsta.pizzeria.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.dao.FileDAO;
import pzinsta.pizzeria.model.File;
import pzinsta.pizzeria.service.FileStorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileSystemFileStorageService implements FileStorageService {

    @Value("${file.storage.directory}")
    private String directory;

    private FileDAO fileDAO;

    @Autowired
    public FileSystemFileStorageService(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }

    @Override
    @Transactional
    public File saveFile(InputStream inputStream, String contentType) throws IOException {
        return saveFile(inputStream, UUID.randomUUID().toString(), contentType);
    }

    @Override
    @Transactional
    public File saveFile(InputStream inputStream, String name, String contentType) throws IOException {
        Files.copy(inputStream, Paths.get(directory, name));
        File file = new File();
        file.setName(name);
        file.setContentType(contentType);
        return fileDAO.saveOrUpdate(file);
    }

    @Override
    public InputStream getFileAsInputStream(String name) throws IOException {
        return Files.newInputStream(Paths.get(directory, name));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getContentTypeByName(String name) {
        return fileDAO.getContentTypeByName(name);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public FileDAO getFileDAO() {
        return fileDAO;
    }

    public void setFileDAO(FileDAO fileDAO) {
        this.fileDAO = fileDAO;
    }
}
