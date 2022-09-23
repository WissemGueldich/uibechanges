package com.tn.uib.uibechanges.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tn.uib.uibechanges.security.jwt.AuthEntryPointJwt;
import com.tn.uib.uibechanges.security.jwt.AuthTokenFilter;
import com.tn.uib.uibechanges.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}

//package com.tn.uib.uibechanges.security;
//
//import static com.tn.uib.uibechanges.security.PermissionType.READ;
//import static com.tn.uib.uibechanges.security.PermissionType.WRITE;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import org.aspectj.weaver.ast.And;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.tn.uib.uibechanges.model.UserPermission;
//import com.tn.uib.uibechanges.model.UserRole;
//import com.tn.uib.uibechanges.security.jwt.AuthTokenFilter;
//import com.tn.uib.uibechanges.service.UserService;
//
////import com.tn.uib.uibechanges.security.jwt.JwtUsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	@Autowired
//	UserService userDetailsService;
//
//	@Autowired
//	private AuthEntryPointJwt unauthorizedHandler;
//
//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}
//	@Autowired
//	private final PasswordEncoder passwordEncoder;
//
//	@Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//	}
//
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
////	@Bean
////	public PasswordEncoder passwordEncoder() {
////		return new BCryptPasswordEncoder();
////	}
//	////////////////////////////////////////////////////////////////////////
//	
//
//	@Autowired
//	public SecurityConfig(PasswordEncoder passwordEncoder) {
//		this.passwordEncoder = passwordEncoder;
//	}
////
////	@Override
////	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
////	}
////	
////	@Bean
////	@Override
////	public AuthenticationManager authenticationManagerBean() throws Exception {
////		return super.authenticationManagerBean();
////	}
////	
////	@Autowired
////	private AuthEntryPointJwt unauthorizedHandler;
////	
////	@Bean
////	public AuthTokenFilter authenticationJwtTokenFilter() {
////		return new AuthTokenFilter();
////	}
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception{
//		http
//				.cors().and().csrf().disable()
////				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////				.and()
//				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
////				.addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager()))
////				.addFilterBefore(new AuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//				.authorizeRequests()
////				.antMatchers("/",
////                        "/favicon.ico",
////                        "/**/*.png",
////                        "/**/*.gif",
////                        "/**/*.svg",
////                        "/**/*.jpg",
////                        "/**/*.html",
////                        "/**/*.css",
////                        "/**/*.js")
////                    .permitAll()
////                    
//                .antMatchers("/api/auth/**")
//                    .permitAll()
////                .antMatchers("/users")
////                	.permitAll()
////                .antMatchers(HttpMethod.POST,"/api/v1/serveurs/**")
////                	.hasAuthority(SERVEUR_WRITE.getPermission())
////                .antMatchers(HttpMethod.PUT,"/api/v1/serveurs/**")
////                	.hasAuthority(SERVEUR_WRITE.getPermission())
////                .antMatchers(HttpMethod.DELETE,"/api/v1/serveurs/**")
////                	.hasAuthority(SERVEUR_WRITE.getPermission())
////                .antMatchers(HttpMethod.GET,"/api/v1/serveurs/**")
////                	.hasAnyAuthority("transfert:read")
//				.anyRequest()
//				.authenticated();
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
//	
//	
//}
