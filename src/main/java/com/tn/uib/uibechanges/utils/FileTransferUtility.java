package com.tn.uib.uibechanges.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Transfer;

public class FileTransferUtility {
	
	private Configuration config;
	private Session session;
	private Transfer transfer;
	
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

	public FileTransferUtility() {
		this.transfer  = new Transfer();
		this.transfer.setDate(new Date());
		this.transfer.setType(0);
	}
	
	public FileTransferUtility(Configuration config, Transfer transfer) {
		this.config = config;
		this.transfer = transfer;
		this.transfer.setDate(new Date());
		this.transfer.setType(0);
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
			this.session.connect(10000);
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

	public boolean upload() throws SftpException, IOException, InterruptedException, JSchException {
		try {
			getSession(config.getDestinationServer().getAddress(), config.getDestinationServer().getPort(),
					config.getDestinationUser().getLogin(), config.getDestinationUser().getPassword());
		} catch (JSchException e2) {
			this.transfer.setError("échec de connexion au serveur / "+e2);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Opening upload channel...");
		ChannelSftp channel;
		try {
			channel = (ChannelSftp) this.session.openChannel("sftp");
		} catch (JSchException e1) {
			this.transfer.setError("échec de création de canal sftp avec le serveur destination / "+e1);
			this.transfer.setResult(false);
			return false;
		}
		try {
			channel.connect(5000);
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au canal sftp avec le serveur destination / "+e);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Starting Upload...");
		System.out.println("Uploading from: " + "src/main/resources/tmp/" + config.getFilter() + " to: "
				+ config.getDestinationPath() + config.getFilter());
		try {
			channel.put("src/main/resources/tmp/" + config.getFilter(),
					config.getDestinationPath() + config.getFilter());
		} catch (SftpException e) {
			this.transfer.setError("échec de téléchargement vers le serveur destination / "+e);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Uploaded successfully.");
		channel.disconnect();
		Path temp = Paths.get("src/main/resources/tmp/" + config.getFilter());
		System.out.println("Deleting temporary files...");
		try {
			Files.delete(temp);
		} catch (IOException e) {
			this.transfer.setError("échec de la suppression du fichiers temporaire / "+e);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Temporary files deleted.");
		if (config.getArchive()) {
			execute("cp " + config.getDestinationPath() + config.getFilter() + " "
					+ config.getDestinationArchivingPath() + config.getFilter().replace(".", "_archive."));
		}
		killSession();
		return true;
	}

	public boolean download() throws IOException, SftpException, InterruptedException, JSchException {
		try {
			getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(),
					config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
		} catch (JSchException e2) {
			this.transfer.setError("échec de connexion au serveur source / "+e2);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Opening download channel...");
		ChannelSftp channel;
		try {
			channel = (ChannelSftp) this.session.openChannel("sftp");
		} catch (JSchException e1) {
			this.transfer.setError("échec de création du canal sftp avec le serveur source / "+e1);
			this.transfer.setResult(false);
			return false;
		}
		try {
			channel.connect();
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au canal sftp avec le serveur source / "+e);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Starting download...");
		try {
			channel.get(config.getSourcePath() + config.getFilter(), "src/main/resources/tmp/" + config.getFilter());
		} catch (SftpException e) {
			this.transfer.setError("échec de téléchargement à partir du serveur source / "+e);
			this.transfer.setResult(false);
			return false;
		}
		System.out.println("Downloaded successfully.");
		channel.disconnect();
		//TODO: condition to not remove file unless uploaded
		if (config.getMove()) {
			execute("rm -f " + config.getSourcePath() + config.getFilter());
		}
		killSession();
		return true;
	}

	public Transfer transfer() throws JSchException, IOException, SftpException, InterruptedException {

		System.out.println("initiating transfer...");
		boolean download = this.download();
		boolean upload = this.upload();
		System.out.println("File transfer completed.");
		if (upload && download) {
			this.transfer.setError("");
			this.transfer.setResult(true);
			return this.transfer;
		}
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
			this.transfer.setError("échec de connexion au canal ssh / "+e);
			this.transfer.setResult(false);
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
		this.config = config;
	}

}
