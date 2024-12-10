package pthttm.retail.repository.firebase;

import com.google.cloud.storage.Blob;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Repository
public class FirebaseRepositoryImpl implements FirebaseRepository {

    @Value("${firebase.storage.bucket-name}")
    private String firebaseStorageBucketName;

    @Value("${firebase.storage.image-link}")
    private String firebaseStorageImageLink;

    private final StorageClient storageClient;

    @Autowired
    public FirebaseRepositoryImpl(FirebaseApp firebaseApp) {
        try {
            this.storageClient = StorageClient.getInstance(firebaseApp);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing Firebase StorageClient: " + e.getMessage(), e);
        }
    }

    @Override
    public String getObjectUrl( String fileName) throws IOException{
        Blob blob = storageClient.bucket().get(fileName);
        if (blob != null&& blob.exists()) {
            /*String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());*/
            return String.format(firebaseStorageImageLink,firebaseStorageBucketName, fileName);
//            return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
        }else {
            return null;
        }
    };

    @Override
    public InputStream getObject( String fileName) throws IOException {
        Blob blob = storageClient.bucket().get(fileName);
        if (blob != null) {
            return new ByteArrayInputStream(blob.getContent());
        }else {
            throw new IOException("File " + fileName + " not found in default bucket!");
        }
    }


    @Override
    public void uploadObject( String fileName, InputStream inputStream,String contentType) throws IOException {
        Blob blob = storageClient.bucket().create(fileName, inputStream, contentType);
        if (blob == null) {
            throw new IOException("Failed to upload file " + fileName + " to default bucket!");
        }
    }

    @Override
    public void deleteObject( String fileName) throws IOException {
        Blob blob = storageClient.bucket().get(fileName);
        if (blob != null) {
            blob.delete();
        } else {
            throw new IOException("File " + fileName + " not found in default bucket ");
        }
    }
}
