package ru.mts.ilifeweb.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.ilifeweb.service.PushNotificationsService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class IlifeController {

    @Autowired
    PushNotificationsService androidPushNotificationsService;
    private final String TOPIC = "ilifeclient";

    @RequestMapping("/greeting")
    public String sayHello(){
        return "HELLO BABY";
    }

    @RequestMapping(value = "/pulseup", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> sendHighPulse() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Pulse high");
        notification.put("body", "PulsUp");

        JSONObject data = new JSONObject();
        data.put("Action", "HighPulse");

        //body.put("notification", notification);
        body.put("data", data);

        return sendMsg(body.toString());
    }

    @RequestMapping(value = "/pulselow", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> sendLowPulse() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Pulse low");
        notification.put("body", "PulseLow");

        JSONObject data = new JSONObject();
        data.put("Action", "PulseLow");

        //body.put("notification", notification);
        body.put("data", data);

        return sendMsg(body.toString());
    }
    
    @RequestMapping(value = "/pulsenormal", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> sendNormalPulse() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Pulse normal");
        notification.put("body", "PulseNormal");

        JSONObject data = new JSONObject();
        data.put("Action", "PulseNormal");
     //   data.put("Key-2", "JSA Data 2");
     //   body.put("notification", notification);
        body.put("data", data);

        return sendMsg(body.toString());
    }
    
    @RequestMapping(value = "/fell", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> sendFell() throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + TOPIC);
        body.put("priority", "high");

        JSONObject notification = new JSONObject();
        notification.put("title", "Grandmother fell");
        notification.put("body", "Whats happened?");

        JSONObject data = new JSONObject();
        data.put("Action", "Fell");

        body.put("notification", notification);
        body.put("data", data);
       
        return sendMsg(body.toString());
    }
    
    
    private ResponseEntity<String> sendMsg(String body){
        HttpEntity<String> request = new HttpEntity<>(body);

        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}
