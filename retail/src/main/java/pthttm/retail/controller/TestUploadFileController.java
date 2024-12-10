package pthttm.retail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import pthttm.retail.service.FirebaseStorageService;

@Controller
public class TestUploadFileController {

    private final FirebaseStorageService firebaseService;

    @Autowired
    public TestUploadFileController(FirebaseStorageService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @GetMapping("/index2")
    public String getIndex3(Model model) throws IOException {
        InputStream imageStream = firebaseService.getObject("logo-web.png");

        if (imageStream != null) {
            byte[] imageBytes = imageStream.readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("base64Image", base64Image);
        } else {
            model.addAttribute("base64Image", "");
        }

        return "index2";
    }
}
