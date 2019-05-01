package com.company.itinerary.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.company.itinerary.management.properties.RoleProperties;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private RoleProperties rolePropetiies;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().csrf().disable().authorizeRequests()
				.antMatchers("/v2/api-docs", "/swagger-resources", "/csrf", "/swagger-resources/configuration/security",
						"/swagger-resources/configuration/ui", "/swagger-ui.html", "/webjars/**","/actuator/hystrix.stream")
				.permitAll().anyRequest().authenticated();
		http.logout().clearAuthentication(true);

	}

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {


		auth.inMemoryAuthentication().withUser(rolePropetiies.getAdminUser().trim())
				.password(passwordEncoder().encode(rolePropetiies.getAdminUserPassword().trim())).roles("ADMIN_USER").and()
				.withUser(rolePropetiies.getTraveluser()).password(passwordEncoder().encode(rolePropetiies.getTraveluserPassword()))
				.roles("TRAVEL_USER");

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}