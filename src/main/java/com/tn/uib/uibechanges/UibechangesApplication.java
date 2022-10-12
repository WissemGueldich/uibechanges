package com.tn.uib.uibechanges;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.SystemUser;
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
import com.tn.uib.uibechanges.utils.FileTransferUtility;

@SpringBootApplication
public class UibechangesApplication {

	public static void main(String[] args) {
		SpringApplication.run(UibechangesApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(UserRoleService userRoleService , UserService userService, 
						ProfileService profileService, ServerService serverService, 
						ConfigurationService configurationService, ApplicationService applicationService, 
						JobService jobService, GlobalSettingService globalSettingService,
						DayService dayService, SystemUserService systemUserService) {
		return args -> {
			Server server = new Server("192.168.75.128", 22, "the server");
			SystemUser systemUser = new SystemUser("libelle Suser", "sftpuser", "sftppassword", true, Set.of(server));
			Configuration config = new Configuration("the_file.txt", false, "config libelle", false, false, false, "/sftpuser/", 
					"/sftpuser/archive/", "/home/sftpuser/", "/home/sftpuser/archive/", server, server, systemUser, systemUser);
			FileTransferUtility fileTransferUtility = new FileTransferUtility();
			fileTransferUtility.setConfig(config);
			fileTransferUtility.upload();
			
		};
	}

}
