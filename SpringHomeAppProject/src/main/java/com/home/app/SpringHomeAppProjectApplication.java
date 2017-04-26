package com.home.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
public class SpringHomeAppProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringHomeAppProjectApplication.class, args);
	}
	
	@Bean("pushNotificationTaskExecutor")
	public ThreadPoolTaskExecutor pushNotificationExector(){		
		ThreadPoolTaskExecutor t=new ThreadPoolTaskExecutor();
		t.setMaxPoolSize(10);
		t.setCorePoolSize(10);
		t.setQueueCapacity(25);
		return t;		
	}
	
	@Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }
 
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
	