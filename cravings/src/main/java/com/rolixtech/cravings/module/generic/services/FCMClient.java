package com.rolixtech.cravings.module.generic.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FCMClient {
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String FCM_SERVER_KEY = "key=AAAAXNFv1GU:APA91bGJ2yd-annOv7Az9kbC7mck6Ek1Ow95RIj5kUuccX3pV8xD6-1dmJrEXijaE1Zl5YEPdB1Cp53NE162HtAEJH8pSeLWsgICJIq4IARvEQO-z-wlqSu4Qp8suwJj341epGwwjIqe";

    public void sendNotification(String deviceToken, String title, String body) throws IOException, ParseException {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(FCM_SERVER_KEY);
//
//        String json = String.format(
//            "{ \"to\": \"%s\", \"notification\": {\"title\": \"%s\", \"body\": \"%s\"} }",
//            deviceToken, title, body
//        );
//
//        HttpEntity<String> entity = new HttpEntity<>(json, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(FCM_URL, entity, String.class);
//System.out.println(response);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            System.out.println("Notification sent successfully");
//        } else {
//            System.out.println("Failed to send notification. Error code: " + response.getStatusCodeValue());
//        }
//        
        
        JSONObject httpResponse = new JSONObject();
    	
    	URL url = new URL(FCM_URL);
    	HttpURLConnection request = (HttpURLConnection) url.openConnection();
    	request.setRequestMethod("POST");
    	request.setRequestProperty("Authorization",FCM_SERVER_KEY);
    	request.setRequestProperty("Accept", "application/json");
    	request.setRequestProperty("Content-Type", "application/json");
    	
    	request.setDoOutput(true);
    	
    	OutputStream os = request.getOutputStream();
    	
    	JSONObject requestBody=new JSONObject();
    	JSONObject requestBodyNotification=new JSONObject();
    	requestBodyNotification.put("title", title);
    	requestBodyNotification.put("body", body);

    	requestBody.put("to", deviceToken);
    	requestBody.put("notification", requestBodyNotification);
    	
    
    	System.out.println(requestBody);
    	os.write(requestBody.toJSONString().getBytes());
    	os.flush();
    	os.close();
    	
    	int responseCode = request.getResponseCode();
    	
    	System.out.println(" Response Code :  " + responseCode);
    	System.out.println(" Response Message : " + request.getResponseMessage());
    	if (responseCode == 201) { // success
    		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
    		String inputLine;
    		StringBuffer response = new StringBuffer();
    		while ((inputLine = in.readLine()) != null) {
    			response.append(inputLine);
    		}
    		in.close();
    		// print result
    		// System.out.println(response.toString());
    		JSONParser parser = new JSONParser();
    		httpResponse = (JSONObject) parser.parse(response.toString());

    	}
    	
//    	return httpResponse;
        
        
    }
    
    
    
    
    
    
    
}

