package com.tn.uib.uibechanges.security;

import static com.tn.uib.uibechanges.security.UserPermission.SERVEUR_WRITE;
import static com.tn.uib.uibechanges.security.UserRole.ADMIN;
import static com.tn.uib.uibechanges.security.UserRole.NWADMIN;
import static com.tn.uib.uibechanges.security.UserRole.USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfig(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
				.csrf().disable()
				//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				//.and()
				.authorizeRequests()
				.antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                    .permitAll()
                    
                .antMatchers("/api/auth/**")
                    .permitAll()
                /*.antMatchers(HttpMethod.POST,"/api/v1/serveurs/**")
                	.hasAuthority(SERVEUR_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v1/serveurs/**")
                	.hasAuthority(SERVEUR_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE,"/api/v1/serveurs/**")
                	.hasAuthority(SERVEUR_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/api/v1/serveurs/**")
                	.hasAnyRole(NWADMIN.name(),ADMIN.name())*/
                
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
			UserDetails testUser = User.builder()
					.username("user")
					.password(passwordEncoder.encode("user123"))
					//.roles(USER.name())
					.authorities(USER.getGrantedAuthorities())
					.build();
			
			UserDetails testAdmin = User.builder()
					.username("admin")
					.password(passwordEncoder.encode("admin123"))
					//.roles(ADMIN.name())
					.authorities(ADMIN.getGrantedAuthorities())
					.build();
			
			UserDetails noWriteAdmin = User.builder()
					.username("nwadmin")
					.password(passwordEncoder.encode("nwadmin123"))
					//.roles(NWADMIN.name())
					.authorities(NWADMIN.getGrantedAuthorities())
					.build();
			
			return new InMemoryUserDetailsManager(
					testUser,
					testAdmin,
					noWriteAdmin
					);
		}
	
}
