package com.vmware.data.demo.retail.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter 
{	
	@Autowired
	Environment env;
	
	@Autowired
	UserDetailsService userDetailsService;

	
	/**
	 * Configuration for the READ, WRITE, READWRITE and SUPER roles
	 * 
	 * @param http the HTTP security
	 * @throws the Exception details
	 * 
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		AccessDeniedHandler h = (request,response,access) -> 
		{
			System.out.println("ACCESS DENIED!!! "+access);
			
			response.getWriter().write("ACCESS DENIED!!! ");
		};
		
         http
         	.csrf().disable();
         
         
        http.httpBasic()
                .realmName("basic")
                .authenticationEntryPoint(authenticationEntryPoint()).
                and()
                .userDetailsService(userDetailsService)
                .formLogin().loginPage("/login");
        
        http.exceptionHandling()
        .accessDeniedHandler(h);
        
        

        
    }//-------------------------------------------------------------
	/**
	 * Sets the basic realm name
	 * @return Authentication Entry Point
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint(){
	        BasicAuthenticationEntryPoint entryPoint = 
	          new BasicAuthenticationEntryPoint();
	        entryPoint.setRealmName("basic");
	        return entryPoint;
	 }//-------------------------------------------------------------
}
