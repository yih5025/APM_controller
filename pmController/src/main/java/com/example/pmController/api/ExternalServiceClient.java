package com.example.pmController.api;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExternalServiceClient {

    private final OkHttpClient client = new OkHttpClient();

    public String sendDegreeToExternalService(String degree) throws IOException {
        MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json; ty=4");
        RequestBody body = RequestBody.create(mediaType, "{\"m2m:cin\": {\"con\": \"" + degree + "\"}}");

        Request request = new Request.Builder()
                .url("http://203.253.128.177:7579/Mobius/sch20191546/motorBottom")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("X-M2M-RI", "12345")
                .addHeader("X-M2M-Origin", "Ssch20191546") // aei = change to YOUR aei
                .addHeader("Content-Type", "application/vnd.onem2m-res+json; ty=4")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.code() != 201) {
                System.out.println("There was a problem. Status Code: " + response.code());
                return null;
            }

            String responseBody = response.body().string();
            return responseBody;
        }
    }
}
