package com.home.app.rest.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.home.app.rest.services.BillsService;
import com.home.app.rest.services.SanityService;
import com.home.app.rest.services.StaticService;

@Configuration
@ApplicationPath("/homeapp")
public class HomeAppRestConfig extends ResourceConfig{
	public HomeAppRestConfig() {
        //packages("com.home.app.rest.services");
		register(BillsService.class);
		register(SanityService.class);
		register(StaticService.class);
        property(ServerProperties.TRACING, "ALL");
    }
}
