package pzinsta.pizzeria.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pzinsta.pizzeria.repository.FileRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Profile("aws")
public class AwsS3FileStorageService extends AbstractDaoFileStorageService {
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    public AwsS3FileStorageService(FileRepository fileRepository) {
        super(fileRepository);
    }

    @Override
    protected String saveFileContent(InputStream inputStream, String name, String contentType) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(bytes.length);

        amazonS3.putObject(bucketName, name, new ByteArrayInputStream(bytes), objectMetadata);

        AccessControlList accessControlList = amazonS3.getObjectAcl(bucketName, name);
        accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        amazonS3.setObjectAcl(bucketName, name, accessControlList);

        return amazonS3.getUrl(bucketName, name).toString();
    }
}
