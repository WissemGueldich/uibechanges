package com.tn.uib.uibechanges.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.jcraft.jsch.*;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Transfer;
import java.io.ByteArrayInputStream;


public class FileTransferUtility {
	
	private Configuration config;
	private Session session;
	private Transfer transfer;

	byte[] fileContent;
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public FileTransferUtility(int type) {
		this.transfer  = new Transfer();
		this.transfer.setDate(new Date());
		this.transfer.setType(type);
	}
	
	public FileTransferUtility(Configuration config, Transfer transfer, int type) {
		this.config = config;
		this.transfer = transfer;
		this.transfer.setDate(new Date());
		this.transfer.setType(type);
	}

	private void getSession(String host, int port, String sftpUser, String sftpPassword) throws JSchException {
		JSch jSch = new JSch();

		try {
			this.session = jSch.getSession(sftpUser, host, port);
		} catch (JSchException e) {
			this.transfer.setError("échec de création de session "+e);
			this.transfer.setResult(false);
			return;
		}
		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.setPassword(sftpPassword);
		System.out.println("Connecting to server...");
		try {
			this.session.connect(5000);
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au serveur "+e);
			this.transfer.setResult(false);
			return;
		}
		System.out.println("Connected.");

	}

	private void killSession() {
		System.out.println("Disconnecting from server...");
		this.session.disconnect();
		System.out.println("Disconnected.");
	}

	public boolean download() throws IOException, SftpException, InterruptedException, JSchException {
		try {
			getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(),
					config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
		} catch (JSchException e2) {
			this.transfer.setError("échec de connexion au serveur source / "+e2);
			this.transfer.setResult(false);
			System.out.println("Failed");
			return false;
		}
		System.out.println("Opening download channel...");
		ChannelSftp channel;
		try {
			channel = (ChannelSftp) this.session.openChannel("sftp");
		} catch (JSchException e1) {
			this.transfer.setError("échec de création du canal sftp avec le serveur source / "+e1);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		try {
			channel.connect();
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au canal sftp avec le serveur source / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		String sourceFilePath = formatPath(config.getSourcePath());
		System.out.println("Starting download...");
		try {
			InputStream inputStream = channel.get(sourceFilePath);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, bytesRead);
			}
			fileContent = byteArrayOutputStream.toByteArray();
			byteArrayOutputStream.close();
			inputStream.close();
		} catch (SftpException e) {
			this.transfer.setError("échec de téléchargement à partir du serveur source / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		channel.disconnect();
		System.out.println("Downloaded successfully.");
		if (config.getArchive()) {
			execute("powershell.exe copy " + sourceFilePath.substring(1) + " "
					+ formatPath(config.getSourceArchivingPath()).replace(".", "_archive.").substring(1));
		}
		killSession();
		return true;
	}

	public boolean upload() throws SftpException, IOException, InterruptedException, JSchException {
		try {
			getSession(config.getDestinationServer().getAddress(), config.getDestinationServer().getPort(),
					config.getDestinationUser().getLogin(), config.getDestinationUser().getPassword());
		} catch (JSchException e2) {
			this.transfer.setError("échec de connexion au serveur / "+e2);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		System.out.println("Opening upload channel...");
		ChannelSftp channel;
		try {
			channel = (ChannelSftp) this.session.openChannel("sftp");
		} catch (JSchException e1) {
			this.transfer.setError("échec de création de canal sftp avec le serveur destination / "+e1);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		try {
			channel.connect(5000);
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au canal sftp avec le serveur destination / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		String destFilePath = formatPath(config.getDestinationPath());
		System.out.println("Starting Upload...");
		try {
			ByteArrayInputStream uploadInputStream = new ByteArrayInputStream(fileContent);
			if(config.getOverwrite()){
				channel.put(uploadInputStream, destFilePath,ChannelSftp.OVERWRITE);
			}else{
				SftpATTRS attrs = null;
				try {
					attrs = channel.stat(destFilePath);
				} catch (SftpException e) {
					System.out.println(e);
				}

				if (attrs != null) {
					this.transfer.setError("Fichier déjà existant dans la répertoire destination.");
					this.transfer.setResult(false);
					System.out.println("Failed.");
					return false;
				} else {
					channel.put(uploadInputStream, destFilePath,ChannelSftp.RESUME);
				}
			}
			uploadInputStream.close();
		} catch (SftpException e) {
			this.transfer.setError("échec de téléchargement vers le serveur destination / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		channel.disconnect();
		System.out.println("Uploaded successfully.");
		if (config.getArchive()) {
			execute("powershell.exe copy " + destFilePath.substring(1) + " "
					+ formatPath(config.getDestinationArchivingPath()).replace(".", "_archive.").substring(1));
		}
		killSession();
		if (config.getMove()) {
			try {
				getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(),
						config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
				execute("powershell.exe del " + formatPath(config.getSourcePath()).substring(1));
			} catch (JSchException e2) {
				this.transfer.setError("Transfer éffectué. échec de suppression du fichier source/ "+e2);
				this.transfer.setResult(false);
				System.out.println("Failed");
			}
		}
		killSession();
		return true;
	}


	public Transfer transfer() throws JSchException, IOException, SftpException, InterruptedException {
		System.out.println("initiating transfer...");
		if (!this.download()) {
			System.out.println("File transfer failed.");
			return this.transfer;
		}

		if (!this.upload()) {
			System.out.println("File transfer failed.");
			return this.transfer;
		}

		this.transfer.setError("");
		this.transfer.setResult(true);
		System.out.println("File transfer completed.");
		return this.transfer;
	}

	private void execute(String command) throws JSchException, IOException, InterruptedException {
		/*
		 * server config needed to execute ssh commands for achiving nano
		 * /etc/ssh/sshd_config remove ForceCommand internal-sftp remove chroot
		 * directory
		 */
		System.out.println("Opening exec channel...");
		ChannelExec channel;
		try {
			channel = (ChannelExec) this.session.openChannel("exec");
			channel.setCommand(command);
		} catch (JSchException e) {
			this.transfer.setError("échec de création du canal ssh / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return;
		}
		System.out.println("Executing command: " + command);
		ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
		ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
		InputStream in=channel.getInputStream();
		InputStream err=channel.getExtInputStream();
		try {
			channel.connect();
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au canal ssh pour archiver / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return;
		}
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				outputBuffer.write(tmp, 0, i);
			}
			while (err.available() > 0) {
				int i = err.read(tmp, 0, 1024);
				if (i < 0)
					break;
				errorBuffer.write(tmp, 0, i);
			}
			if (channel.isClosed()) {
				if ((in.available() > 0) || (err.available() > 0))
					continue;
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
			Thread.sleep(1000);
		}
		System.out.println("output: " + outputBuffer.toString("UTF-8"));
		System.out.println("error: " + errorBuffer.toString("UTF-8"));

		channel.disconnect();
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.transfer.setConfiguration(config);
		this.config = config;
	}

	private String formatPath(String path){
		if (!path.startsWith("/")){
			path="/"+path;
		}
		if (!path.endsWith("/")){
			path=path+"/";
		}
		return path+config.getFilter();
	}
}
