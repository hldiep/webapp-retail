package pthttm.retail.service;

import pthttm.retail.repository.firebase.FirebaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseStorageService {
    private final FirebaseRepository firebaseRepository;

    @Autowired
    public FirebaseStorageService(FirebaseRepository firebaseRepository) {
        this.firebaseRepository = firebaseRepository;
    }

    public String getObjectUrl(String filename) throws IOException{
        return firebaseRepository.getObjectUrl(filename);
    }

    public InputStream getObject(String fileName) throws IOException {
        return firebaseRepository.getObject(fileName);
    }

    public void uploadObject(String fileName, InputStream inputStream,String contentType) throws IOException {
        firebaseRepository.uploadObject( fileName, inputStream,contentType);//contentType là type của file được lưu trong fb
    }

    public void deleteObject(String fileName) throws IOException {
        firebaseRepository.deleteObject(fileName);
    }
}
