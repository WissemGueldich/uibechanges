package com.tn.uib.uibechanges;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Profile;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.security.PermissionType;
import com.tn.uib.uibechanges.service.ConfigurationService;
import com.tn.uib.uibechanges.service.ProfileService;
import com.tn.uib.uibechanges.service.ServerService;
import com.tn.uib.uibechanges.service.UserRoleService;
import com.tn.uib.uibechanges.service.UserService;

@SpringBootApplication
public class UibechangesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UibechangesApplication.class, args);
	}
	
	@Bean
	
	CommandLineRunner run(UserRoleService userRoleService , UserService userService, 
						ProfileService profileService, ServerService serverService, 
						ConfigurationService configurationService) {
		return args -> {
			Profile profDefault = new Profile("default profile");
			Profile profTest = new Profile("test profile");
			Profile profAdmin = new Profile("admin profile");
			profileService.addProfile(profDefault);
			profileService.addProfile(profTest);
			profileService.addProfile(profAdmin);
			
			Server serv1 = new Server("192.168.14.22",81,"test libelle","155.114.23.6","164.244.3.24");
			Server serv2 = new Server("156.66.126.23",25,"sdfg gf","123.45.67.89","189.63.57.14.28");
			Server serv3 = new Server("sfsdf",81,"test libelle","155.114.23.6","164.244.3.24");
			Server serv4 = new Server("aaaa",25,"sdfg gf","123.45.67.89","189.63.57.14.28");
			serverService.addServer(serv1);
			serverService.addServer(serv2);
			serverService.addServer(serv3);
			serverService.addServer(serv4);

			
			Configuration config1 = new Configuration("filet test", false, "libelle source ", true, true, false, "/path/test", "/archive/test",
					"/path/dest","/archive/dest",serv1,serv2);
			Configuration config2 = new Configuration("filet test", false, "tlibelle destination", true, true, false, "/path/test", "/archive/test", 
					"/path/dest","/archive/dest",serv3,serv4);
			Configuration config3 = new Configuration("filet test", false, "tlibelle hkhhjk", true, true, false, "/path/test", "/archive/test", 
					"/path/dest","/archive/dest",serv2,serv1);
			Configuration config4 = new Configuration("filet false", false, "tlibelle destinghfdgation", true, false, false, "/path/test", "/archive/test", 
					"/path/dest","/archive/dest");
			configurationService.addConfiguration(config1);
			configurationService.addConfiguration(config2);
			configurationService.addConfiguration(config3);
			configurationService.addConfiguration(config4);

			userRoleService.addPermission(new UserPermission("user",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("user",PermissionType.WRITE));
			userRoleService.addPermission(new UserPermission("config",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("config",PermissionType.WRITE));
			
			UserRole roleUser = new UserRole("user",new HashSet<>());
			UserRole roleAdmin = new UserRole("admin",new HashSet<>());
			userRoleService.addRole(roleUser);
			userRoleService.addRole(roleAdmin);
			
			userRoleService.addPermissionToRole(2, 1);
			userRoleService.addPermissionToRole(2, 2);
			userRoleService.addPermissionToRole(2, 3);
			userRoleService.addPermissionToRole(2, 4);
			
			userService.addUser(new User( "user", "password", "user@user.com", "userfname", "userlastName",true, new HashSet<>(), new HashSet<>()));
			userService.addUser(new User( "admin", "password", "admin@admin.com", "adminfname", "adminlastName",true, new HashSet<>(), new HashSet<>()));
			
			userService.addRoleToUser(1, Set.of(roleUser));
			userService.addRoleToUser(2, Set.of(roleAdmin,roleUser));
			
			userService.addProfileToUser(1, Set.of(profDefault));
			userService.addProfileToUser(2, Set.of(profAdmin));
			userService.addProfileToUser(2, Set.of(profTest));
			
			profileService.addConfigurationToProfile(1, Set.of(config1));
			profileService.addConfigurationToProfile(2, Set.of(config1,config2));
			profileService.addConfigurationToProfile(3, Set.of(config2));
			profileService.addConfigurationToProfile(3, Set.of(config3,config4));
			
			System.out.println(configurationService.getUserConfigurations(true, "admin")); 


			
			
			
			
			


		};
	}

}
