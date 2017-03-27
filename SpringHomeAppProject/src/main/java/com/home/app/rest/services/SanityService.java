package com.home.app.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.app.entities.SmsEntity;
import com.home.app.repository.SmsRepositoryImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Path("/health")
public class SanityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SanityService.class);
	
	@Autowired
	SmsRepositoryImpl smsRepositoryImpl;
	
	@Autowired
	Sanity sanity;
	
	@GET
	@Path("sanity")
	@Produces(MediaType.APPLICATION_JSON)
	public Sanity checkSanityService(){	
		SmsEntity sms=new SmsEntity("1", "dummyaddress", "2016-11-11 11:20", "dummymessage");
		smsRepositoryImpl.saveObject(sms);
		LOGGER.debug("Inside the Sanity Service");
		sanity.setSuccess(true);
		;
		LOGGER.info("Size is {}",smsRepositoryImpl.getAllObjects().size());
		return sanity;
	}
	
}
