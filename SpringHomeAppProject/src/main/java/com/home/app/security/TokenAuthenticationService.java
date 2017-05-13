package com.home.app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

import java.io.IOException;
import java.security.GeneralSecurityException;

class TokenAuthenticationService {
  static final long EXPIRATIONTIME = 864_000_000; // 10 days
  static final String SECRET = "ThisIsASecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  static void addAuthentication(HttpServletResponse res, String username) {
    String JWT = Jwts.builder()
        .setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    String idToken = request.getHeader("idToken");
    try {
			GoogleIdToken tokenGoogle=GoogleLoginAuthenticationFilter.checkTokenValidity(idToken);
	
		    if (token != null && tokenGoogle!=null) {
		      // parse the token.
		      String user = Jwts.parser()
		          .setSigningKey(SECRET)
		          .parseClaimsJws(token.replace(TOKEN_PREFIX, "").replace(" ", ""))
		          .getBody()
		          .getSubject();
		
		      return user != null && user.equals(tokenGoogle.getPayload().getEmail())?
		          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
		          null;
		    }
    } catch (GeneralSecurityException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return null;
  }
}