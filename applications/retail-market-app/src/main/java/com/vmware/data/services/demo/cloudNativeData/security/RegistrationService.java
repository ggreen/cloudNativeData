package com.vmware.data.services.demo.cloudNativeData.security;

import com.vmware.dataTx.geode.spring.security.SpringSecurityUserService;
import com.vmware.dataTx.geode.spring.security.data.RegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
