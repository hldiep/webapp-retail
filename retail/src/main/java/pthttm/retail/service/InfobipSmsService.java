package pthttm.retail.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class InfobipSmsService {

    @Value("${infobip.api.base.url}")
    private String infobipApiBaseUrl;

    @Value("${infobip.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String sendSMS(String phoneNumber, String message){
        MediaType mediaType = MediaType.parse("application/json");

        String jsonBody ="{\"messages\":[" +
                "{\"destinations\":[" +
                    "{\"to\":\"" + phoneNumber +"\"}]," +
                "\"from\":\"447491163443\"," +
                "\"text\":\""+ message +"\"}]}";

        RequestBody body = RequestBody.create(mediaType, jsonBody);
        Request request = new Request.Builder()
                .url(infobipApiBaseUrl +"/sms/2/text/advanced")
                .post(body)
                .addHeader("Authorization", "App " +apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Message sent successfully: " + response.body().string());
            return response.isSuccessful() ? "Message sent successfully" : "Failed to send message";
        } catch (IOException e) {
            System.out.println("Failed to send message");
            throw new RuntimeException(e);
        }

    }

}
