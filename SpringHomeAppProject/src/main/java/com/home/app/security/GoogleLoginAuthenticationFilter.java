package com.home.app.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.home.app.exception.UserNotFoundException;
import com.home.app.repository.LoginRepoImpl;
import com.home.app.rest.services.LoginService;


public class GoogleLoginAuthenticationFilter extends
							AbstractAuthenticationProcessingFilter{

	@Autowired
	LoginService loginService;
	
	@Autowired
	LoginRepoImpl loginRepoImpl;
	
	protected GoogleLoginAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String googleToken=request.getParameter("idToken");
		System.out.println(googleToken);
		GoogleIdToken token;
		try {
			System.out.println(loginRepoImpl);
			token = checkTokenValidity(googleToken);
			if(token != null){	
				Payload payload = token.getPayload();
				String email = payload.getEmail();
				User principal = new User(email, "", Collections.emptyList()); 
		        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principal,null);
		        return authToken;
			}
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		throw new AuthenticationServiceException("Invalid Token");
	}
	protected static GoogleIdToken checkTokenValidity(String idToken) throws GeneralSecurityException, IOException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
			    .setAudience(Collections.singletonList("801690203554-a66s1qp579m2vbq4gcld660aphr69t6k.apps.googleusercontent.com"))
			    .build();
		
		return verifier.verify(idToken);
		
	}
	 @Override
	  protected void successfulAuthentication(
	      HttpServletRequest req,
	      HttpServletResponse res, FilterChain chain,
	      Authentication auth) throws IOException, ServletException {
	    TokenAuthenticationService
	        .addAuthentication(res, auth.getName());
	  }
}
