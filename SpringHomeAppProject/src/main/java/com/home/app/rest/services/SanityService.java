package com.home.app.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.app.repository.BillsRepoImpl;
import com.home.app.repository.CategoryRepoImpl;
import com.home.app.repository.ModeRepoImpl;
import com.home.app.repository.SmsRepositoryImpl;
import com.home.app.rest.request.Sanity;

@Service
@Path("/health")
public class SanityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SanityService.class);
	
	@Autowired
	SmsRepositoryImpl smsRepositoryImpl;	
	@Autowired
	ModeRepoImpl modeRepoImpl;	
	@Autowired
	CategoryRepoImpl categoryRepoImpl;
	@Autowired
	BillsRepoImpl billsRepoImpl;
	
	//@Autowired
	
	
	@GET
	@Path("sanity")
	@Produces(MediaType.APPLICATION_JSON)
	public Sanity checkSanityService(){	
		Sanity sanity =new Sanity(true);
		return sanity;
	}
	
}
