package com.home.app.task;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.home.app.rest.services.PushNotificationService;

@Service
@Scope("prototype")
public class PushNotificationTask implements Callable<String> {
	private static String SERVER_KEY = "AAAAuqh1taI:APA91bFGzI1ORTyAErX1jPYIPqpcUBeVci87V2juFyz7t-IVedFcPsRrfUQWAR4yDTzyokIARuiy9tfMgzYTtD2sPBT9Ieuk47JydcQAFEQHePC4x-gFJ7nsA_NdCnV611AAA6hYb4eR";
    private static String DEVICE_TOKEN = "ds5dWMiMD-g:APA91bGS3KhtZAuDlW4CNCcD33_svZMpH7e72JPtrIu-WdUOBcummxo5MAzQPCf8xS_qZlEtIdoMX_NAojcr7AZCwP2itds0Xs_zVfnk56nQeV8HU-a_mNF79j_mFmIT7qeYc14VDCqQ";
    
	private static final String TITLE ="Home App Notification";
	
	@Autowired
	PushNotificationService pushNotificationService;
	
	private String message;
	
	@Override
	public String call() throws Exception {		
		return sendPushNotification(TITLE, message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public static String sendPushNotification(String title, String message) throws Exception {
        //System.out.println(pushNotificationService.getDeviceToken());
		String pushMessage = "{\"data\":{\"title\":\"" +
                title +
                "\",\"message\":\"" +
                message +
                "\"},\"to\":\"" +
                DEVICE_TOKEN +
                "\"}";
        // Create connection to send FCM Message request.
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Send FCM message content.
      /*  OutputStream outputStream = conn.getOutputStream();
        outputStream.write(pushMessage.getBytes());
        System.out.println(conn.getResponseCode());
        System.out.println(conn.getResponseMessage());*/
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(pushMessage.toString());
        wr.flush();
        return conn.getResponseMessage();
    }
	public static void main(String[] args) throws Exception {
    	String title = "My Second Notification";
        String message = "Hello, I'm push notification";
        sendPushNotification(title, message);
    }
}
