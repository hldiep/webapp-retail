package pthttm.retail.controller;

import pthttm.retail.service.FirebaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final FirebaseStorageService firebaseService;

    @Autowired
    public GlobalControllerAdvice(FirebaseStorageService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @ModelAttribute("base64Image")
    public String fetchLogo() throws IOException {
        InputStream imageStream = firebaseService.getObject("logo-web.png");
        if (imageStream != null) {
            byte[] imageBytes = imageStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
        return "";
    }

}
