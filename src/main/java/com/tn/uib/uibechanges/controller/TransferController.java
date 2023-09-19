package com.tn.uib.uibechanges.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tn.uib.uibechanges.model.Configuration;
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
	
	@PostMapping
	@PreAuthorize("hasAuthority('transfer:execute')")
	public ResponseEntity<?> transfer (@RequestBody Configuration config) {
		
		FileTransferUtility fileTransferUtility = new FileTransferUtility(0);
		fileTransferUtility.getTransfer().setInitiator(SecurityContextHolder.getContext().getAuthentication().getName());

		fileTransferUtility.setConfig(config);

		if (fileTransferUtility.transfer().isResult()) {
			return new ResponseEntity<>(transferService.addTransfer(fileTransferUtility.getTransfer()),HttpStatus.OK);
		}else{
			transferService.addTransfer(fileTransferUtility.getTransfer());
			return new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.ACCEPTED);
		}
		/*Email email = new Email();
		email.setSubject("Transfert échoué");
		email.setRecipient("wisseminfo0@gmail.com");
		email.setMsgBody("Ceci est un email automatique pour vous informer que le transfert '" + fileTransferUtility.getTransfer().getConfiguration().getLibelle() + "' a échoué : \n"+fileTransferUtility.getTransfer().getError()+".\n(" + new Date() + ").");
		emailService.sendSimpleMail(email);
		return new ResponseEntity<>(fileTransferUtility.getTransfer().getError(),HttpStatus.OK);*/
	}
	
	
	//non-paginated version
	/*@GetMapping
	@PreAuthorize("hasAuthority('transfer:read')")
	public ResponseEntity<?> getTransfers() {
		return transferService.getTransfers();
	}*/
	
	//paginated version
	@GetMapping
	@PreAuthorize("hasAuthority('transfer:read')")
    public Page<Transfer> getPaginatedTransfers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return transferService.getPaginatedTransfers(pageNumber, pageSize);
    }
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAuthority('transfer:read')")
	public ResponseEntity<?> getTransfer(@PathVariable int id) {
		return transferService.getTransfer(id);
	}
	
	@PutMapping
	@PreAuthorize("hasAuthority('transfer:write')")
	public ResponseEntity<?> updateTransfer(@RequestBody Transfer transfer) {
		return transferService.updateTransfer(transfer);
	}
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('transfer:write')")
	public ResponseEntity<?> deleteTransfer(@PathVariable int id) {
		return transferService.deleteTransfer(id);
	}
	
	@DeleteMapping
	@PreAuthorize("hasAuthority('transfer:write')")
	public ResponseEntity<?> deleteTransfersBetween(@RequestBody DateBetween dateBetween) throws ParseException {
		return transferService.deleteByDateBetween(dateBetween.getStartDate(), dateBetween.getEndDate());
	}
	
	
	
}

