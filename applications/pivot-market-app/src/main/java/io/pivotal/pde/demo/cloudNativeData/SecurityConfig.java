package io.pivotal.pde.demo.cloudNativeData;

import gedi.solutions.geode.spring.security.GeodeUserDetailsService;
import gedi.solutions.geode.spring.security.SpringSecurityUserService;
import io.pivotal.services.dataTx.geode.client.GeodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig
{
    /**
     *
     * @param geodeClient the geode client
     * @return the user details service
     */
    @Bean
    public SpringSecurityUserService userDetailsService(@Autowired GeodeClient geodeClient)
    {
        return new GeodeUserDetailsService(geodeClient.getRegion("users"));
    }//------------------------------------------------
}
