package pzinsta.pizzeria.web.client;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pzinsta.pizzeria.web.client.dto.File;

@Component
public class FileStorageServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${file.storage.service.url}")
    private String fileStorageServiceUrl;

    public File saveFile(byte[] file, String contentType) {
        return restTemplate.postForObject(fileStorageServiceUrl, file, File.class, ImmutableMap.of("contentType", contentType));
    }

    public File getFile(Long id) {
        return restTemplate.getForEntity(fileStorageServiceUrl + "/{id}", File.class, id).getBody();
    }
}
