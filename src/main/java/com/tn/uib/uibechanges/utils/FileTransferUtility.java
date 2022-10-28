package com.tn.uib.uibechanges.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.tn.uib.uibechanges.model.Configuration;

public class FileTransferUtility {
	
	Configuration config;
	Session session;
	Channel channel;
	
	public FileTransferUtility() {
	}

	private void getSession(String host, int port, String sftpUser, String sftpPassword) throws JSchException{
		JSch jSch = new JSch();
		try {
			this.session = jSch.getSession(sftpUser, host ,port);
			this.session.setConfig("StrictHostKeyChecking", "no");
			this.session.setPassword(sftpPassword);
			System.out.println("Connecting to server..............");
			this.session.connect(10000);
			System.out.println("Connected.");
		} catch (JSchException e) {
			throw new JSchException("création de session échoué",e);
		}
	}
	
	private void killSession() {
		System.out.println("Disconnecting from server..............");
		this.session.disconnect();
		System.out.println("Disconnected.");
	}
		
	public void upload() throws  JSchException, SftpException, IOException {
		getSession(config.getDestinationServer().getAddress(),config.getDestinationServer().getPort(), config.getDestinationUser().getLogin(), config.getDestinationUser().getPassword());
		System.out.println("Opening upload channel..............");
		ChannelSftp channel = (ChannelSftp) this.session.openChannel("sftp");
		channel.connect(5000);
		System.out.println("Starting Upload..............");
		System.out.println("Uploading from: "+"src/main/resources/tmp/"+config.getFilter()+" to: "+ config.getDestinationPath()+config.getFilter());
		channel.put("src/main/resources/tmp/"+config.getFilter(), config.getDestinationPath()+config.getFilter());
		System.out.println("Uploaded successfully.");
		channel.disconnect();
		Path temp = Paths.get("src/main/resources/tmp/"+ config.getFilter());
		System.out.println("Deleting temporary files..............");
	    Files.delete(temp);
	    System.out.println("Temporary files deleted.");
	    if(config.getArchive()) {
			execute("cp "+config.getDestinationPath()+config.getFilter()+" "+config.getDestinationArchivingPath()+config.getFilter().replace(".", "_archive."));
	    }
		killSession();
	}
	
	public void download() throws JSchException, SftpException {
		getSession(config.getSourceServer().getAddress(), config.getSourceServer().getPort(), config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
		System.out.println("Opening download channel..............");
		ChannelSftp channel = (ChannelSftp) this.session.openChannel("sftp");
		channel.connect();
		System.out.println("Starting download..............");
		channel.get(config.getSourcePath()+config.getFilter(),"src/main/resources/tmp/"+config.getFilter());
		System.out.println("Downloaded successfully.");
		channel.disconnect();
		killSession();
	}
	
	public void transfer() throws JSchException, SftpException, IOException {
		System.out.println("initiating transfer..............");
		this.download();
		this.upload();
		System.out.println("File transfer completed.");
	}
	
	private void execute(String command) throws JSchException, IOException {
		/*server config needed to execute ssh commands for achiving
		nano /etc/ssh/sshd_config
		remove ForceCommand internal-sftp
		remove chroot directory*/
		System.out.println("Opening exec channel..............");
		ChannelExec channel = (ChannelExec) this.session.openChannel("exec");
		channel.setCommand(command) ;
		System.out.println("Executing command: "+command);
		ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
		ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();

		InputStream in = channel.getInputStream();
		InputStream err = channel.getExtInputStream();
		channel.connect();
		byte[] tmp = new byte[1024];
		while (true) {
		    while (in.available() > 0) {
		        int i = in.read(tmp, 0, 1024);
		        if (i < 0) break;
		        outputBuffer.write(tmp, 0, i);
		    }
		    while (err.available() > 0) {
		        int i = err.read(tmp, 0, 1024);
		        if (i < 0) break;
		        errorBuffer.write(tmp, 0, i);
		    }
		    if (channel.isClosed()) {
		        if ((in.available() > 0) || (err.available() > 0)) continue; 
		        System.out.println("exit-status: " + channel.getExitStatus());
		        break;
		    }
		    try { 
		      Thread.sleep(1000);
		    } catch (Exception ee) {
		    }
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
