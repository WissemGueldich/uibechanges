package com.tn.uib.uibechanges;

import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.model.UserPermission;
import com.tn.uib.uibechanges.model.UserRole;
import com.tn.uib.uibechanges.security.PermissionType;
import com.tn.uib.uibechanges.service.UserRoleService;
import com.tn.uib.uibechanges.service.UserService;

@SpringBootApplication
public class UibechangesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UibechangesApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(UserRoleService userRoleService , UserService userService) {
		return args -> {
			userRoleService.addPermission(new UserPermission("user",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("user",PermissionType.WRITE));
			userRoleService.addPermission(new UserPermission("config",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("config",PermissionType.WRITE));
			userRoleService.addPermission(new UserPermission("transfert",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("transfert",PermissionType.WRITE));
			userRoleService.addPermission(new UserPermission("job",PermissionType.READ));
			userRoleService.addPermission(new UserPermission("job",PermissionType.WRITE));
			userRoleService.addRole(new UserRole("user",new HashSet<>()));
			userRoleService.addRole(new UserRole("admin",new HashSet<>()));
			userRoleService.addRole(new UserRole("nwadmin",new HashSet<>()));
			userRoleService.addRole(new UserRole("default",new HashSet<>()));
			userRoleService.addRole(new UserRole("test",new HashSet<>()));
			userRoleService.addPermissionToRole(2, 1);
			userRoleService.addPermissionToRole(2, 2);
			userRoleService.addPermissionToRole(2, 3);
			userRoleService.addPermissionToRole(2, 4);
			userRoleService.addPermissionToRole(2, 5);
			userRoleService.addPermissionToRole(2, 6);
			userRoleService.addPermissionToRole(2, 7);
			userRoleService.addPermissionToRole(2, 8);
			userRoleService.addPermissionToRole(3, 1);
			userRoleService.addPermissionToRole(3, 3);
			userRoleService.addPermissionToRole(3, 5);
			userRoleService.addPermissionToRole(3, 7);
			userRoleService.addPermissionToRole(4, 1);
			userRoleService.addPermissionToRole(4, 2);
			userRoleService.addPermissionToRole(4, 3);
			userRoleService.addPermissionToRole(4, 4);
			userRoleService.addPermissionToRole(5, 5);
			userRoleService.addPermissionToRole(5, 6);
			userRoleService.addPermissionToRole(5, 7);
			userRoleService.addPermissionToRole(5, 8);
			userService.addUser(new User( "user", "password", true,"user@user.com", "M123", "userfname", "userlastName", new HashSet<>()));
			userService.addUser(new User( "admin", "password", true,"admin@admin.com", "M1234", "adminfname", "adminlastName", new HashSet<>()));
			userService.addUser(new User( "nwadmin", "password", true,"nwadmin@nwadmin.com", "M12345", "nwadminfname", "nwadminlastName", new HashSet<>()));
			userService.addUser(new User( "default", "password", true,"default@default.com", "M1234567", "defaultfname", "defaultlastName", new HashSet<>()));
			userService.addUser(new User( "test", "password", true,"test@test.com", "M123456", "testfname", "testlastName", new HashSet<>()));
			userService.addUser(new User( "god", "password", true,"god@god.com", "M12", "godfname", "godlastName", new HashSet<>()));
			userService.addRoleToUser(1, 1);
			userService.addRoleToUser(2, 2);
			userService.addRoleToUser(3, 3);
			userService.addRoleToUser(4, 4);
			userService.addRoleToUser(5, 5);
			userService.addRoleToUser(6, 1);
			userService.addRoleToUser(6, 2);
			userService.addRoleToUser(6, 3);
			userService.addRoleToUser(6, 4);
			userService.addRoleToUser(6, 5);

			


		};
	}

}
