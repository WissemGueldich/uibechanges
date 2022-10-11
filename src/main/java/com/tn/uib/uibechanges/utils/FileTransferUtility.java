package com.tn.uib.uibechanges.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.tn.uib.uibechanges.model.Configuration;

public class FileTransferUtility {
	
	Configuration config;
	Session session;
	ChannelSftp channel;
	
	public FileTransferUtility() {
	}

	public void upload() throws  JSchException, SftpException, IOException {
		connect(config.getDestinationServer().getAddress(),config.getDestinationServer().getPort() , config.getDestinationUser().getLogin(), config.getDestinationUser().getPassword());
		System.out.println("Starting Upload..............");
		this.channel.put("src/main/resources/tmp/"+config.getFilter(), config.getDestinationPath()+config.getFilter());
		System.out.println("Uploaded successfully !");
		disconnect();
		Path temp = Paths.get("src/main/resources/tmp/"+ config.getFilter());
		System.out.println("Deleting temporary files..............");
	    Files.delete(temp);
	    System.out.println("Temporary files deleted");
	}
	
	public void download() throws JSchException, SftpException {
		connect(config.getSourceServer().getAddress(), config.getSourceServer().getPort(), config.getSourceUser().getLogin(), config.getSourceUser().getPassword());
		System.out.println("Starting download..............");
		this.channel.get(config.getSourcePath()+config.getFilter(),"src/main/resources/tmp/"+config.getFilter());
		System.out.println("Downloaded successfully !");
		disconnect();
	}
	
	public void transfer() throws JSchException, SftpException, IOException {
		System.out.println("initiating transfer..............");
		this.download();
		this.upload();
		System.out.println("File transfer completed successfully !");
	}
	private void connect(String host, int port, String sftpUser, String sftpPassword) throws JSchException{
		JSch jSch = new JSch();
		try {
			Session session = jSch.getSession(sftpUser, host ,Integer.valueOf(port));
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(sftpPassword);
			
			System.out.println("Connecting to server..............");
			session.connect(10000);
			this.session=session;
			System.out.println("Connected successfully !");
			Channel channel = session.openChannel("sftp");
			this.channel = (ChannelSftp) channel;
			System.out.println("Opening transfer channel");
			this.channel.connect(5000);
			System.out.println("Channel opened successfully !");
		} catch (JSchException e) {
			throw new JSchException("création de session échoué",e);
		}
		
	}
	
	private void disconnect() {
		System.out.println("Closing channel..............");
		this.channel.disconnect();
		System.out.println("Channel closed ");
		System.out.println("Disconnecting from server..............");
		this.session.disconnect();
		System.out.println("Disconnected ");
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}
	
}
