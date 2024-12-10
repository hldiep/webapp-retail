package pthttm.retail.repository.firebase;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;

@Repository
public interface FirebaseRepository {
    InputStream getObject( String fileName) throws IOException;
    String getObjectUrl( String fileName) throws IOException;
    void uploadObject( String fileName, InputStream inputStream,String contentType) throws IOException;
    void deleteObject( String fileName) throws IOException;
}
