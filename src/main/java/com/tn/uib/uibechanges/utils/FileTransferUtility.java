package com.tn.uib.uibechanges.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import com.jcraft.jsch.*;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Transfer;

import java.util.List;


public class FileTransferUtility {
	
	private Configuration config;
	private Session session;
	private Transfer transfer;
 	private boolean isWindowsSession;
	byte[] fileContent;
	List<String> files=new ArrayList<>();
	
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

	public boolean isWindowsSession() {
		return isWindowsSession;
	}

	public void setWindowsSession(boolean windowsSession) {
		isWindowsSession = windowsSession;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
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
			throw e;
		}
		this.session.setConfig("StrictHostKeyChecking", "no");
		this.session.setPassword(sftpPassword);
		System.out.println("Connecting to server...");
		try {
			this.session.connect(5000);
			this.isWindowsSession =  isWindows();
		} catch (JSchException e) {
			this.transfer.setError("échec de connexion au serveur "+e);
			this.transfer.setResult(false);
			throw e;
		}
		System.out.println("Connected.");

	}

	private void killSession() {
		System.out.println("Disconnecting from server...");
		this.session.disconnect();
		System.out.println("Disconnected.");
	}

	private void getFilesList(){
		try {
			getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(),
					config.getSourceUser().getLogin(), config.getSourceUser().getPassword());

			Channel channel = session.openChannel("exec");
			String command;
			if (isWindowsSession){
				command = "powershell.exe $files = Get-ChildItem "+formatPath(config.getSourcePath()).substring(1)+" -file -filter "+config.getFilter()+" ; foreach ($file in $files) { $file.Name }";
			}else{
				//NOT TESTED
				command = "find "+formatPath(config.getSourcePath()).substring(1)+" -type f -name \""+config.getFilter()+"\" -exec basename {} \\";
			}
			((ChannelExec) channel).setCommand(command);

			InputStream in = channel.getInputStream();
			channel.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = reader.readLine()) != null) {
				files.add(line);
			}

			channel.disconnect();
			killSession();

		} catch ( Exception e) {
			System.out.println(e);
		}
	}

	public Transfer transfer() {
		getFilesList();

		if (files != null && !files.isEmpty()) {
			for (String file : files) {
				System.out.println("Initiating transfer of file: " + file);

				if (!this.download(file)) {
					System.out.println("File transfer failed for :"+file);
					this.transfer.setError(this.transfer.getError() +". source :"+ file);
					this.transfer.setResult(false);
					return this.transfer;
				}

				if (!this.upload(file)) {
					System.out.println("File transfer failed for :"+file);
					this.transfer.setError(this.transfer.getError()+". destination :" + file);
					this.transfer.setResult(false);
					return this.transfer;
				}
				this.transfer.setTransferedFiles(this.transfer.getTransferedFiles()+file+"\n");
			}
		}

		this.transfer.setError("");
		this.transfer.setResult(true);
		System.out.println("File transfer completed.");
		return this.transfer;
	}
	public boolean download(String fileName) {
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
		String sourceFilePath = formatPath(config.getSourcePath())+fileName;
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
		} catch (SftpException | IOException e) {
			this.transfer.setError("échec de téléchargement à partir du serveur source / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		channel.disconnect();
		System.out.println("Downloaded successfully.");
		if (config.getArchive()) {
			if (isWindowsSession){
				execute("powershell.exe copy " + singleQuote(sourceFilePath.substring(1)) + " "
						+ singleQuote(formatPath(config.getSourceArchivingPath()).substring(1)+fileName.replace(".", "_archive.")));
			}else {
				//NOT TESTED !!
				execute("cp " + sourceFilePath + " "
						+ (formatPath(config.getSourceArchivingPath()).substring(1))+fileName.replace(".", "_archive."));
			}
		}
		killSession();
		return true;
	}

	public boolean upload(String fileName){
		try {
			getSession(config.getDestinationServer().getAddress(), config.getDestinationServer().getPort(),
					config.getDestinationUser().getLogin(), config.getDestinationUser().getPassword());
		} catch (JSchException e2) {
			this.transfer.setError("échec de connexion au serveur destination / "+e2);
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
		String destFilePath = formatPath(config.getDestinationPath())+fileName;
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
		} catch (SftpException | IOException e) {
			this.transfer.setError("échec de téléchargement vers le serveur destination / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return false;
		}
		channel.disconnect();
		System.out.println("Uploaded successfully.");
		if (config.getArchive()) {
			if (isWindowsSession){
				execute("powershell.exe copy " + singleQuote(destFilePath.substring(1)) + " "
						+ singleQuote(formatPath(config.getDestinationArchivingPath()).substring(1)+fileName.replace(".", "_archive.")));
			}else {
				//NOT TESTED !!
				execute("cp " + destFilePath + " "
						+ formatPath(config.getDestinationArchivingPath()).substring(1)+destFilePath.replace(".", "_archive."));
			}

		}
		killSession();
		if (config.getMove()) {
			try {
				getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(),
						config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
				if (isWindowsSession){
					execute("powershell.exe del " + singleQuote(formatPath(config.getSourcePath()).substring(1)+fileName));
				}else {
					//NOT TESTED !!
					execute("rm -f " + formatPath(config.getSourcePath()).substring(1)+fileName);
				}

			} catch (JSchException e2) {
				this.transfer.setError("Transfer éffectué. échec de suppression du fichier source/ "+e2);
				this.transfer.setResult(false);
				System.out.println("Failed");
			}
		}
		killSession();
		return true;
	}

	private void execute(String command) {
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

		try {
			InputStream in=channel.getInputStream();
			InputStream err=channel.getExtInputStream();
			channel.connect();
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
		} catch (JSchException | IOException | InterruptedException e) {
			this.transfer.setError("échec de connexion au canal ssh pour archiver / "+e);
			this.transfer.setResult(false);
			System.out.println("Failed.");
			return;
		}
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
		return path;
	}

	private String singleQuote(String str){
		return "'"+str+"'";
	}

    private boolean isWindows(){
		try {
			ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand("echo %OS%");
			channelExec.setInputStream(null);
			channelExec.setErrStream(System.err);

			InputStream inputStream = channelExec.getInputStream();
			channelExec.connect();

			int i = 10;
			while(channelExec.getExitStatus()!=0 && i>0){
				Thread.sleep(100);
				i--;
			}

			String output = new String(inputStream.readAllBytes()).trim();

			inputStream.close();
			channelExec.disconnect();
			return output.equalsIgnoreCase("Windows_NT");
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}
