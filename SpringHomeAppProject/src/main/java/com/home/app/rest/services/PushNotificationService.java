package com.home.app.rest.services;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.home.app.task.PushNotificationTask;


@Service
public class PushNotificationService implements ApplicationContextAware{
	/**
     * Replace SERVER_KEY with your SERVER_KEY generated from FCM
     * Replace DEVICE_TOKEN with your DEVICE_TOKEN
     */
    private static final String MESSAGE_FORMAT = "Bill saved on server for Amount %s on %s";
    private String deviceToken;
    
    @Autowired
	@Qualifier("pushNotificationTaskExecutor")
	ThreadPoolTaskExecutor taskExector;
	private ApplicationContext applicationContext;

    public String getDeviceToken() {
		return deviceToken;
	}


	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}


	/**
     * USE THIS METHOD to send push notification
     */
    public static void main(String[] args) throws Exception {
    	System.out.println(String.format(MESSAGE_FORMAT,"s","s"));
    	String title = "My Second Notification";
        String message = "Hello, I'm push notification";
        //sendPushNotification(title, message);
    }

    
    public void submitTask(String message){    	
    	PushNotificationTask task=(PushNotificationTask)applicationContext.getBean("pushNotificationTask");
    	task.setMessage(message);
    	taskExector.submit(task);
    }


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}

    /**
     * Sends notification to mobile, YOU DON'T NEED TO UNDERSTAND THIS METHOD
     */
    
}
