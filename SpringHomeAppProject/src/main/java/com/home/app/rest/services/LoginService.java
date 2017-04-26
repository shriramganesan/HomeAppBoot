package com.home.app.rest.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.home.app.entities.LoginEntity;
import com.home.app.entities.LoginHistory;
import com.home.app.repository.LoginHistoryRepoImpl;
import com.home.app.repository.LoginRepoImpl;
import com.home.app.rest.request.LoginRequest;
import com.home.app.rest.request.ResponseMessage;
import com.home.app.utility.HomeAppUtility;
@Service
@Path("/loginService")
public class LoginService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	@Autowired
	LoginRepoImpl loginRepoImpl;
	@Autowired
	LoginHistoryRepoImpl loginHistoryRepoImpl;
	@GET
	@Path("testLogin")
	public void testLogin(){
		try{
			LoginEntity l=new LoginEntity();
			loginRepoImpl.save(l);
		}
		catch(javax.validation.ConstraintViolationException e){			
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseMessage loginHomeApp(@RequestParam LoginRequest loginRequest) throws Exception{
		ResponseMessage r = new ResponseMessage();	
		try {
			this.processLogin(loginRequest);				
			r.setSuccess(true);		
		} catch (Exception e) {
			LOGGER.error("Exception in Login Process",e);
			throw e;
		}
		return r;		
	}

	private LoginEntity saveLoginDetails(LoginRequest loginRequest) {
		LoginEntity l=new LoginEntity();
		BeanUtils.copyProperties(loginRequest, l);
		HomeAppUtility.setAuditInfo(l, "A", l.getEmail());
		loginRepoImpl.save(l);
		return l;
	}

	private void saveLoginHistory(LoginRequest loginRequest,LoginEntity le) {
		LoginHistory lh=new LoginHistory();
		BeanUtils.copyProperties(loginRequest, lh);
		lh.setLoginEntity(le);
		lh.setLastLoginDate(new Date());
		HomeAppUtility.setAuditInfo(lh, "A", loginRequest.getEmail());
		loginHistoryRepoImpl.save(lh);
	}
	@Transactional
	public void processLogin(LoginRequest loginRequest) throws Exception{
		GoogleIdToken token;
		try {
			token = checkTokenValidity(loginRequest.getIdToken());
			if(token != null){
				saveLoginHistory(loginRequest,checkExistingUser(loginRequest));				
			}
		} catch (Exception e) {
			throw e;
		}
	}	
	private LoginEntity checkExistingUser(LoginRequest loginRequest) {
		LoginEntity login=loginRepoImpl.findByEmail(loginRequest.getEmail());
		return login!=null?login:saveLoginDetails(loginRequest);		
	}
	private GoogleIdToken checkTokenValidity(String idToken) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			    .setAudience(Collections.singletonList("801690203554-a66s1qp579m2vbq4gcld660aphr69t6k.apps.googleusercontent.com"))
			    .build();
		
		return verifier.verify(idToken);
		
	}
	public static void main(String args[]) throws GeneralSecurityException, IOException{
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			    .setAudience(Collections.singletonList("801690203554-a66s1qp579m2vbq4gcld660aphr69t6k.apps.googleusercontent.com"))
			    .build();
		
		GoogleIdToken idToken = verifier.verify("eyJhbGciOiJSUzI1NiIsImtpZCI6ImJjOTE1NzZmYzkzZGYzYWRjNTk4OTZjNDk1Y2I2NzI5ZGQ1YmMwMjMifQ.eyJhenAiOiI4MDE2OTAyMDM1NTQtc2FrM3BqZG9la2Q5dTUzYTk5cXN1ZzZucTNnYmtnaDQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4MDE2OTAyMDM1NTQtYTY2czFxcDU3OW0ydmJxNGdjbGQ2NjBhcGhyNjl0NmsuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDMyNDc1MTEwNzc1MDU3ODg4NzUiLCJlbWFpbCI6InNocmlyYW0uZ2FuZXNhbkBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6Ly9hY2NvdW50cy5nb29nbGUuY29tIiwiaWF0IjoxNDkyMzcyMDc5LCJleHAiOjE0OTIzNzU2NzksIm5hbWUiOiJzaHJpcmFtIGdhbmVzYW4iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy01dFhkNU0zREE1Zy9BQUFBQUFBQUFBSS9BQUFBQUFBQUFCcy8yY1FLVUppREpxay9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoic2hyaXJhbSIsImZhbWlseV9uYW1lIjoiZ2FuZXNhbiIsImxvY2FsZSI6ImVuIn0.bfOHCMTY0Vdg8ue39la5-GXsPzXRPKB2Vw2EHjMIO9kFVCPcis2SF_uca9KLZus8RlF55T09ol1kC61KYQ0hW85eWR1Be08E4lZXbFA9hsIs5AX45kAsiVJIRLOThHxLAXV2ew8lQ2VbA66op7Evcz2A42d521iAwk4xZlUJSn2laJ9rGvKDCl7tsk_uKbI7T7LUTvnABk_cfCx8dxBBCutkHiZ1PXJ91KoNuNUc9kv3WSfD58EO5lxsUQhO5ygS_wk0ZY9W1PrhaZRx2ezHrQaQyUozaHQb5DbpQ_8b8aJJ8HsMVU4OVHtwNjc4f1ysYR9pHZT5jGduRSmZ49UR1A");
		
		if (idToken != null) {
			  Payload payload = idToken.getPayload();

			  // Print user identifier
			  String userId = payload.getSubject();
			  System.out.println("User ID: " + userId);

			  // Get profile information from payload
			  String email = payload.getEmail();
			  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			  String name = (String) payload.get("name");
			  String pictureUrl = (String) payload.get("picture");
			  String locale = (String) payload.get("locale");
			  String familyName = (String) payload.get("family_name");
			  String givenName = (String) payload.get("given_name");

			  // Use or store profile information
			  // ...

			} else {
			  System.out.println("Invalid ID token.");
			}
	}
}
