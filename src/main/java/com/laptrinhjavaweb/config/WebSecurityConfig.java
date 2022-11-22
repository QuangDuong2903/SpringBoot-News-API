package com.laptrinhjavaweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.laptrinhjavaweb.jwt.JwtAuthenticationFilter;
import com.laptrinhjavaweb.service.impl.CustomUserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic()
        .and()
        .headers().frameOptions().sameOrigin()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/auth/signin").permitAll()
        .antMatchers(HttpMethod.GET, "/auth/google").permitAll()
        .antMatchers(HttpMethod.POST, "/auth/refreshtoken").permitAll()
		.antMatchers(HttpMethod.GET, "/new/**").permitAll()
		.antMatchers(HttpMethod.GET, "/category/**").permitAll()
		.antMatchers(HttpMethod.GET, "/user/**").permitAll()
		.antMatchers(HttpMethod.POST, "/signup").permitAll()
		.antMatchers(HttpMethod.GET, "/role/**").hasAuthority("ROLE_ADMIN")
		//.antMatchers(HttpMethod.POST, "/role").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.GET, "/category/**").hasAuthority("ROLE_ADMIN")
		//.antMatchers(HttpMethod.POST, "/category").hasAuthority("ROLE_ADMIN")
		.antMatchers(HttpMethod.POST, "/category").permitAll()
		.antMatchers(HttpMethod.POST, "/role").permitAll()
		.anyRequest().authenticated()
		.and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
        .csrf().disable();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
}
