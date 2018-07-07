package pzinsta.pizzeria.service;

import org.springframework.transaction.annotation.Transactional;
import pzinsta.pizzeria.model.File;
import pzinsta.pizzeria.repository.FileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractDaoFileStorageService implements FileStorageService {

    private FileRepository fileRepository;

    public AbstractDaoFileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public File saveFile(InputStream inputStream, String contentType) throws IOException {
        return saveFile(inputStream, UUID.randomUUID().toString(), contentType);
    }

    @Override
    @Transactional
    public File saveFile(InputStream inputStream, String name, String contentType) throws IOException {
        String url = saveFileContent(inputStream, name, contentType);
        File file = new File();
        file.setName(name);
        file.setContentType(contentType);
        file.setUrl(url);
        return fileRepository.save(file);
    }

    protected abstract String saveFileContent(InputStream inputStream, String name, String contentType) throws IOException;

    @Override
    public Optional<File> getFileById(Long id) {
        return fileRepository.findById(id);
    }
}
