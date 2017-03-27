package com.home.app.rest.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.home.app.rest.services.SanityService;

@Configuration
@ApplicationPath("/homeapp")
public class HomeAppRestConfig extends ResourceConfig{
	public HomeAppRestConfig() {
        register(SanityService.class);
    }
}
