package ru.mts.ilifeweb.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationsService {
    private static final String FIREBASE_SERVER_KEY = "AAAAqGmd9rE:APA91bETTsUweDRTWt3-N1gGJX9zQ5Y5gy-Q16LGOgD6TWOy13wF-Y0OsvMq_hBQaeqsE3ddANrX_F9ZkJ73a4lxr9f-Q-_NtjAEPtyK93-166a4yG3npqpUvLmuGizvFhFil7r5F4yE";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
