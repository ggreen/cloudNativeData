package io.pivotal.pde.demo.cloudNativeData.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gedi.solutions.geode.spring.security.SpringSecurityUserService;
import gedi.solutions.geode.spring.security.data.RegistrationDTO;

@RestController
public class RegistrationService
{
	@Autowired
	SpringSecurityUserService userService;
	
	String[] roles  = {"ROLE_WRITE","ROLE_READ"};
	
	@PostMapping("registerUser")
	public String registerUser(@RequestBody RegistrationDTO dto)
	{
		userService.registerUser(dto.toUserDetails(roles));
	
		return "true";
	}

}
