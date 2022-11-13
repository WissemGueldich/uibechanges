package com.tn.uib.uibechanges.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.tn.uib.uibechanges.model.Transfer;
import com.tn.uib.uibechanges.service.TransferService;
import com.tn.uib.uibechanges.utils.FileTransferUtility;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

	@Autowired
	private TransferService transferService;
	
	@PostMapping
	private ResponseEntity<?> transfer (@RequestBody Configuration config) {
		FileTransferUtility fileTransferUtility = new FileTransferUtility();
		fileTransferUtility.setConfig(config);
		Transfer transfer = new Transfer();
		transfer.setConfiguration(config);
		try {
			try {
				if (fileTransferUtility.transfer()) {
					transfer.setResult(true);
					transferService.addTransfer(transfer);
					return new ResponseEntity<>(transferService.addTransfer(transfer),HttpStatus.OK);
				}
			} catch (InterruptedException e) {
				return new ResponseEntity<>("l'éxecution de commande ssh a été interrompue, cause: "+e.getMessage(),HttpStatus.EXPECTATION_FAILED);
			}
		} catch (JSchException e) {
			transfer.setResult(false);
			transfer.setError(e.getMessage());
			transferService.addTransfer(transfer);
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch (IOException e) {
			transfer.setResult(false);
			transfer.setError(e.getMessage());
			transferService.addTransfer(transfer);
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		} catch (SftpException e) {
			transfer.setResult(false);
			transfer.setError(e.getMessage());
			transferService.addTransfer(transfer);
			return new ResponseEntity<>(e.getMessage(),HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
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
