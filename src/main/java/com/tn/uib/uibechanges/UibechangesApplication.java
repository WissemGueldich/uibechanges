package com.tn.uib.uibechanges;

import java.util.Arrays;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Day;
import com.tn.uib.uibechanges.model.Job;
import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
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
						SystemUserService systemUserService, DayService dayService) {
		return args -> {
			
			UserPermission psr = new UserPermission("server:read");
			UserPermission psw = new UserPermission("server:write");
			UserPermission pur = new UserPermission("user:read");
			UserPermission puw = new UserPermission("user:write");
			UserPermission pcr = new UserPermission("config:read");
			UserPermission pcw = new UserPermission("config:write");
			UserPermission pjr = new UserPermission("job:read");
			UserPermission pjw = new UserPermission("job:write");
			UserPermission pje = new UserPermission("job:execute");
			UserPermission ppr = new UserPermission("profile:read");
			UserPermission ppw = new UserPermission("profile:write");
			UserPermission psur = new UserPermission("systemuser:read");
			UserPermission psuw = new UserPermission("systemuser:write");
			UserPermission ptr = new UserPermission("transfer:read");
			UserPermission ptw = new UserPermission("transfer:write");
			UserPermission pte = new UserPermission("transfer:execute");
			
			userRoleService.addPermission(psr);
			userRoleService.addPermission(psw);
			userRoleService.addPermission(pur);
			userRoleService.addPermission(puw);
			userRoleService.addPermission(pcr);
			userRoleService.addPermission(pcw);
			userRoleService.addPermission(pjr);
			userRoleService.addPermission(pjw);
			userRoleService.addPermission(pje);
			userRoleService.addPermission(ppr);
			userRoleService.addPermission(ppw);
			userRoleService.addPermission(psur);
			userRoleService.addPermission(psuw);
			userRoleService.addPermission(ptr);
			userRoleService.addPermission(ptw);
			userRoleService.addPermission(pte);


			UserRole roleAdmin = new UserRole("admin", Set.of(psr,psw,pur,puw,pcr,pcw,pjr,pjw,ppr,ppw,psur,psuw,ptr,ptw,pje,pte));
			UserRole rolehGDHB = new UserRole("gdhb", Set.of(pur,puw));
			UserRole rolesupervision = new UserRole("supervision", Set.of(ptr));
			UserRole roleTransfer = new UserRole("transfer", Set.of(pte,ptr));
			userRoleService.addRole(roleAdmin);
			userRoleService.addRole(rolehGDHB);
			userRoleService.addRole(rolesupervision);
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
			
			Profile profile1 = new Profile();
			profile1.setLibelle("profile1");
			profile1.setConfigurations(Set.of(config));
			profileService.addProfile(profile1);
			
			Profile profile2 = new Profile();
			profile2.setLibelle("profile2");
			profile2.setConfigurations(Set.of(config2));
			profileService.addProfile(profile2);
			
			Profile profile3 = new Profile();
			profile3.setLibelle("profile3");
			profile3.setConfigurations(Set.of(config,config2));
			profileService.addProfile(profile3);
			
			User admin = new User("admin", "adminpass", "admin@admin.com", "admin fname", "admin lname", true, Set.of(roleAdmin), Set.of(profile1,profile2,profile3));
			User gdhbUser = new User("gdhb", "gdhbpass", "gdhb@gdhb.com", "gdhb fname", "ghbh lname", true, Set.of(rolehGDHB), Set.of(profile1,profile2));
			User supervision = new User("supervision", "supervisionpass", "supervision@supervision.com", "supervision fname", "supervision lname", true, Set.of(rolesupervision), Set.of(profile2));
			User transfer = new User("transfer", "transferpass", "transfer@transfer.com", "transfer fname", "transfer lname", true, Set.of(roleTransfer), Set.of(profile1,profile2,profile3));

			userService.addUser(admin);
			userService.addUser(gdhbUser);
			userService.addUser(supervision);
			userService.addUser(transfer);

			
			
			Day day1 = new Day("MON");
			dayService.addDay(day1);
			Day day2 = new Day("TUE");
			dayService.addDay(day2);
			Day day3 = new Day("WED");
			dayService.addDay(day3);
			Day day4 = new Day("THU");
			dayService.addDay(day4);
			Day day5 = new Day("FRI");
			dayService.addDay(day5);
			Day day6 = new Day("SAT");
			dayService.addDay(day6);
			Day day7 = new Day("SUN");
			dayService.addDay(day7);
			
			
			Job job1 = new Job("job1", "20:57", "21:00", 5, true, Set.of(day1,day2,day3,day4,day5,day6,day7));
			jobService.addJob(job1);
			Job job2 = new Job("job2", "8:00", "9:30", 5, false, Set.of(day3,day4));
			jobService.addJob(job2);
			Job job3 = new Job("job3", "17:00", "17:30", 9, false, Set.of(day6,day1,day3));
			jobService.addJob(job3);
			Job job4 = new Job("job4", "18:00", "21:30", 7, true, Set.of(day5));
			jobService.addJob(job4);
			
			
			
//			FileTransferUtility fileTransferUtility = new FileTransferUtility();
//			fileTransferUtility.setConfig(config);
//			fileTransferUtility.transfer();
			
		};
	}

}
