package com.tn.uib.uibechanges;

import java.util.Arrays;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.ConfigurationJob;
import com.tn.uib.uibechanges.model.Day;
import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.security.PermissionType;
import com.tn.uib.uibechanges.service.ApplicationService;
import com.tn.uib.uibechanges.service.ConfigurationService;
import com.tn.uib.uibechanges.service.DayService;
import com.tn.uib.uibechanges.service.GlobalSettingService;
import com.tn.uib.uibechanges.service.JobService;
import com.tn.uib.uibechanges.service.ProfileService;
import com.tn.uib.uibechanges.service.ServerService;
import com.tn.uib.uibechanges.service.SystemUserService;
import com.tn.uib.uibechanges.service.UserRoleService;
import com.tn.uib.uibechanges.service.UserService;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan
//@org.springframework.context.annotation.Configuration
public class UibechangesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UibechangesApplication.class, args);
	}
	
	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
	
	@Bean
	CommandLineRunner run(UserRoleService userRoleService , UserService userService, 
						ProfileService profileService, ServerService serverService, 
						ConfigurationService configurationService, ApplicationService applicationService, 
						JobService jobService, GlobalSettingService globalSettingService,
						DayService dayService, SystemUserService systemUserService) {
		return args -> {
			
			UserPermission p1 = new UserPermission("serveur:lire");
			UserPermission p2 = new UserPermission("serveur:ecrire");
			UserPermission p3 = new UserPermission("utilisateur:lire");
			UserPermission p4 = new UserPermission("utilisateur:ecrire");
			userRoleService.addPermission(p1);
			userRoleService.addPermission(p2);
			userRoleService.addPermission(p3);
			userRoleService.addPermission(p4);
			
			UserRole roleAdmin = new UserRole("admin", Set.of(p1,p2,p3,p4));
			UserRole rolehGDHB = new UserRole("gdhb", Set.of(p3,p4));
			UserRole roleSupervisor = new UserRole("supervision", Set.of(p1,p3));
			UserRole roleTransfer = new UserRole("transfert", Set.of(p2));
			userRoleService.addRole(roleAdmin);
			userRoleService.addRole(rolehGDHB);
			userRoleService.addRole(roleSupervisor);
			userRoleService.addRole(roleTransfer);
			
			Server server = new Server("192.168.75.129", 22, "the server");
			Server server1 = new Server("192.168.75.130", 22, "the server 2");
			serverService.addServer(server);
			serverService.addServer(server1);
			
			SystemUser systemUser = new SystemUser("libelle SftpUser", "sftpuser", "sftppassword", true, Set.of(server));
			SystemUser systemUser1 = new SystemUser("libelle SshUser", "sshuser", "sshpassword", true, Set.of(server1));
			systemUserService.addSystemUser(systemUser);
			systemUserService.addSystemUser(systemUser1);
			
			Configuration config = new Configuration("the_file.txt", "config libelle", false, false, false, true, "/home/sftpuser/", 
					"/home/sftpuser/archive/", "/home/sftpuser/", "/home/sftpuser/archive/", server1, systemUser1, server, systemUser);
			Configuration config2 = new Configuration("the_file2.txt", "config libelle2", true, true, false, false, "/home/sftpuser/", 
					"/home/sftpuser/archive/", "/home/sftpuser/", "/home/sftpuser/archive/", server, systemUser, server1, systemUser1);
			configurationService.addConfiguration(config);
			configurationService.addConfiguration(config2);
			
			Profile profile = new Profile();
			profile.setLibelle("profile");
			profile.setConfigurations(Set.of(config,config2));
			profileService.addProfile(profile);
			
			User admin = new User("admin", "password", "admin@admin.com", "admin fname", "admin lname", true, Set.of(roleAdmin), Set.of(profile));
			userService.addUser(admin);
						
			Job job1 = new Job("job1", "20:31", "21:00", 5, true);
			jobService.addJob(job1);
			Job job2 = new Job("job2", "8:00", "9:30", 5, false);
			jobService.addJob(job2);
			Job job3 = new Job("job3", "17:00", "17:30", 9, false);
			jobService.addJob(job3);
			Job job4 = new Job("job4", "18:00", "21:30", 7, true);
			jobService.addJob(job4);
			
			
			

			

//			FileTransferUtility fileTransferUtility = new FileTransferUtility();
//			fileTransferUtility.setConfig(config);
//			fileTransferUtility.transfer();
			
		};
	}

}
