package com.home.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable().authorizeRequests()
	        .antMatchers("/").permitAll()
	       // .antMatchers(HttpMethod.POST, "/homeapp/login").permitAll()
	        .antMatchers(HttpMethod.POST, "/app/login").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        // We filter the api/login requests
	        .addFilterBefore(new GoogleLoginAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class)
	        // And filter other requests to check the presence of JWT in header
	        .addFilterBefore(new JWTAuthenticationFilter(),
	                UsernamePasswordAuthenticationFilter.class);
		 
	 }
	 
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication()
	        .withUser("admin")
	        .password("password")
	        .roles("ADMIN");
	 }
}
