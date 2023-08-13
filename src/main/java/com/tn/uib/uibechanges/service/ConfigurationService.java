package com.tn.uib.uibechanges.service;

import java.util.HashSet;
import java.util.Set;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.tn.uib.uibechanges.model.SystemUser;
import com.tn.uib.uibechanges.utils.FileTransferUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Server;
import com.tn.uib.uibechanges.model.User;
import com.tn.uib.uibechanges.repository.ConfigurationJobRepository;
import com.tn.uib.uibechanges.repository.ConfigurationRepository;
import com.tn.uib.uibechanges.repository.ServerRepository;
import com.tn.uib.uibechanges.repository.SystemUserRepository;
import com.tn.uib.uibechanges.repository.UserRepository;

@Service
@Transactional
public class ConfigurationService {
	@Autowired
	private ConfigurationRepository configurationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ServerRepository serverRepository;

	@Autowired
	private SystemUserRepository systemUserRepository;

	@Autowired
	private ConfigurationJobRepository configurationJobRepository;

	public ResponseEntity<?> addConfiguration(Configuration configuration) {
		configuration.setSourceServer(serverRepository.findById(configuration.getSourceServer().getId()));
		configuration.setDestinationServer(serverRepository.findById(configuration.getDestinationServer().getId()));
		configuration.setSourceUser(systemUserRepository.findById(configuration.getSourceUser().getId()).get());
		configuration
				.setDestinationUser(systemUserRepository.findById(configuration.getDestinationUser().getId()).get());
		return new ResponseEntity<>(configurationRepository.save(configuration), HttpStatus.CREATED);
	}

	public ResponseEntity<?> getConfigurations() {
		return new ResponseEntity<>(configurationRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getConfiguration(int id) {
		return new ResponseEntity<>(configurationRepository.findById(id), HttpStatus.OK);
	}

	public ResponseEntity<?> getConfigurationsByServers(Server sourceServer, Server destinationServer) {
		Set<Configuration> configs = new HashSet<>();
		Set<Configuration> sConfigs = configurationRepository.findBySourceServer(sourceServer);
		sConfigs.forEach(config -> {
			if (config.getDestinationServer() != null
					&& config.getDestinationServer().getId() == destinationServer.getId()) {
				configs.add(config);
			}
		});
		return new ResponseEntity<>(configs, HttpStatus.OK);
	}

	public ResponseEntity<?> updateConfiguration(Configuration configuration) {

		Configuration oldConfiguration = configurationRepository.findById(configuration.getId()).get();
		oldConfiguration.setLibelle(configuration.getLibelle());
		oldConfiguration.setSourceServer(configuration.getSourceServer());
		oldConfiguration.setSourceUser(configuration.getSourceUser());
		oldConfiguration.setSourcePath(configuration.getSourcePath());
		oldConfiguration.setSourceArchivingPath(configuration.getSourceArchivingPath());
		oldConfiguration.setDestinationServer(configuration.getDestinationServer());
		oldConfiguration.setDestinationUser(configuration.getDestinationUser());
		oldConfiguration.setDestinationPath(configuration.getDestinationPath());
		oldConfiguration.setDestinationArchivingPath(configuration.getDestinationArchivingPath());
		oldConfiguration.setOverwrite(configuration.getOverwrite());
		oldConfiguration.setMove(configuration.getMove());
		oldConfiguration.setArchive(configuration.getArchive());
		oldConfiguration.setFilter(configuration.getFilter());

		return new ResponseEntity<>(configurationRepository.save(oldConfiguration), HttpStatus.OK);
	}

	public ResponseEntity<?> deleteConfiguration(int id) {
		Configuration configuration = configurationRepository.findById(id);
		if (configuration.getProfiles() != null && configuration.getProfiles().size() > 0) {
			configuration.getProfiles().forEach(prof -> {
				prof.getConfigurations().remove(configuration);
			});
			configuration.getProfiles().clear();
		}
		if (configuration.getDestinationUser() != null && configuration.getSourceUser() != null) {
			configuration.getDestinationUser().getConfigurationsAsDestination().remove(configuration);
			configuration.getSourceUser().getConfigurationsAsSource().remove(configuration);
		}
		if (configuration.getDestinationServer() != null && configuration.getSourceServer() != null) {
			configuration.getDestinationServer().getDestionationConfigurations().remove(configuration);
			configuration.getSourceServer().getSourceConfigurations().remove(configuration);
		}
		configuration.getJobs().forEach(confJob -> {
			confJob.getJob().getConfigurations().remove(confJob);
			confJob.setConfiguration(null);
			confJob.setJob(null);
			configurationJobRepository.deleteById(confJob.getConfigurationJobPK());
		});
		configurationRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	public ResponseEntity<?> getUserConfigurations(String matricule) {
		User user = userRepository.findByMatricule(matricule);
		Set<Configuration> configs = new HashSet<>();
		user.getProfiles().forEach(profile -> {
			configs.addAll(profile.getConfigurations());
		});
		return new ResponseEntity<>(configs, HttpStatus.OK);
	}

	public ResponseEntity<?> checkConfig(int id) {
		Configuration config ;
		Report report = new Report();

		if (configurationRepository.existsById(id)){
			config = configurationRepository.findById(id);
		}else{
			report.setMessage("Configuration inéxistante");
			return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
		}

		if (config.getSourceServer()!=null && serverRepository.existsById(config.getSourceServer().getId())){
			if (config.getSourceUser()!=null && systemUserRepository.findById(config.getSourceUser().getId()).isPresent()){
				Server sSource = serverRepository.findById(config.getSourceServer().getId());
				SystemUser uSource = systemUserRepository.findById(config.getSourceUser().getId()).get();
				report.setSourceConnected(checkSshUser(sSource.getAddress(), sSource.getPort(), uSource.getLogin(), uSource.getPassword()));
			}else{
				report.setMessage("Utilisateur source inéxistant");
				return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
			}
		}else{
			report.setMessage("Serveur source inéxistant");
			return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
		}

		if (config.getDestinationServer()!=null && serverRepository.existsById(config.getDestinationServer().getId())){
			if (config.getDestinationUser()!=null && systemUserRepository.findById(config.getDestinationUser().getId()).isPresent()){
				Server sDestination = serverRepository.findById(config.getDestinationServer().getId());
				SystemUser uDestination = systemUserRepository.findById(config.getDestinationUser().getId()).get();
				report.setDestinationConnected(checkSshUser(sDestination.getAddress(), sDestination.getPort(), uDestination.getLogin(), uDestination.getPassword()));
			}else{
				report.setMessage("Utilisateur destination inéxistant");
				return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
			}
		}else{
			report.setMessage("Serveur destination inéxistant");
			return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
		}

		if (config.getSourcePath()!=null && !config.getSourcePath().isEmpty()){
			if (report.isSourceConnected()){
				report.setSourcePathValid(doesPathExist(config.getSourceServer().getAddress(),config.getSourceServer().getPort(),config.getSourceUser().getLogin(),config.getSourceUser().getPassword(),config.getSourcePath()));
			}
		}else{
			report.setMessage("Chemin source invalide");
			return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
		}

		if (config.getDestinationPath()!=null && !config.getDestinationPath().isEmpty()){
			if (report.isDestinationConnected()){
				report.setDestinationPathValid(doesPathExist(config.getDestinationServer().getAddress(),config.getDestinationServer().getPort(),config.getDestinationUser().getLogin(),config.getDestinationUser().getPassword(),config.getDestinationPath()));
			}
		}else{
			report.setMessage("Chemin destination invalide");
			return  new ResponseEntity<>(report,HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(report,HttpStatus.OK);
	}

	public boolean checkSshUser(String host, int port, String username, String password) {
		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(username, host, port);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);

			session.connect();

			boolean isConnected = session.isConnected();

			session.disconnect();

			return isConnected;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public static boolean doesPathExist(String host, int port, String username, String password, String pathToCheck) {
		try {
			JSch jSch = new JSch();
			Session session = jSch.getSession(username, host, port);
			session.setPassword(password);
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			String command = "[ -e " + pathToCheck + " ] && echo \"Exists\" || echo \"NotExists\"";
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);

			channel.connect();

			StringBuilder outputBuffer = new StringBuilder();
			byte[] tmp = new byte[1024];
			while (true) {
				while (channel.getInputStream().available() > 0) {
					int i = channel.getInputStream().read(tmp, 0, 1024);
					if (i < 0) {
						break;
					}
					outputBuffer.append(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (channel.getInputStream().available() > 0) {
						continue;
					}
					break;
				}
				Thread.sleep(1000);
			}

			channel.disconnect();
			session.disconnect();

			String result = outputBuffer.toString().trim();
			return result.equals("Exists");

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
