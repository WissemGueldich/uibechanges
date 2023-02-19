package com.tn.uib.uibechanges.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.servlet.oauth2.resourceserver.OAuth2ResourceServerSecurityMarker;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tn.uib.uibechanges.model.Configuration;
import com.tn.uib.uibechanges.model.Email;
import com.tn.uib.uibechanges.model.Transfer;
import com.tn.uib.uibechanges.service.EmailService;
import com.tn.uib.uibechanges.service.TransferService;
import com.tn.uib.uibechanges.utils.FileTransferUtility;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

	@Autowired
	private TransferService transferService;
	
	@Autowired
	private EmailService emailService;
	
	///////////////////////////////////////////
	@PostMapping("/email")
	private ResponseEntity<?> mail (@RequestBody Email email) {
		return new ResponseEntity<>(emailService.sendSimpleMail(email),HttpStatus.OK);
	}
	///////////////////////////////////////////
	
	@PostMapping
	private ResponseEntity<?> transfer (@RequestBody Configuration config) {
		
		FileTransferUtility fileTransferUtility = new FileTransferUtility();
		fileTransferUtility.setConfig(config);
		System.out.println("hello controller");

		try {
			try {
				if (fileTransferUtility.transfer().isResult()) {
					System.out.println("hello is result");
					return new ResponseEntity<>(transferService.addTransfer(fileTransferUtility.getTransfer()),HttpStatus.OK);
				}
			} catch (InterruptedException e) {
				transferService.addTransfer(fileTransferUtility.getTransfer());
				return new ResponseEntity<>("l'éxecution de commande ssh a été interrompue, cause: "+fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
			}
		} catch (JSchException e) {
			transferService.addTransfer(fileTransferUtility.getTransfer());
			return new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
		} catch (IOException e) {
			transferService.addTransfer(fileTransferUtility.getTransfer());
			return new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
		} catch (SftpException e) {
			transferService.addTransfer(fileTransferUtility.getTransfer());
			return new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
		}
		transferService.addTransfer(fileTransferUtility.getTransfer());
		Email email = new Email();
		email.setSubject("Échec de transfert");
		email.setRecipient("wisseminfo0@gmail.com");
		email.setMsgBody("Ceci est un email automatique pour vous informer q'un transfert a échoué :\n"+fileTransferUtility.getTransfer().getError()+"\n ");
		emailService.sendSimpleMail(email);
		return  new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
	}
	
	@GetMapping
	private ResponseEntity<?> getTransfers() {
		return transferService.getTransfers();
	}
	
	@GetMapping(path = "{id}")
	public ResponseEntity<?> getTransfer(@PathVariable int id) {
		return transferService.getTransfer(id);
	};
	
	@PutMapping
	public ResponseEntity<?> updateTransfer(@RequestBody Transfer transfer) {
		return transferService.updateTransfer(transfer);
	};
	
	@DeleteMapping(path="{id}")
	public ResponseEntity<?> deleteTransfer(@PathVariable int id) {
		return transferService.deleteTransfer(id);
	};
	
}
