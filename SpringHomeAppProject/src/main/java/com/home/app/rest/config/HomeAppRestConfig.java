package com.home.app.rest.config;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.home.app.rest.services.BillsService;
import com.home.app.rest.services.LoginService;
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
		register(LoginService.class);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jersey.config.server.tracing.type","ALL");
        params.put("jersey.config.server.tracing.threshold","SUMMARY");
        addProperties(params);
        property(ServerProperties.TRACING, "ALL");
    }
}
