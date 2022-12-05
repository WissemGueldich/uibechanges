package com.tn.uib.uibechanges.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tn.uib.uibechanges.model.Transfer;
import com.tn.uib.uibechanges.repository.TransferRepository;


@Service
@Transactional
public class TransferService {
	
	@Autowired
	private TransferRepository transferRepository;
	
	public Transfer addTransfer(Transfer transfer) {
		transfer.setDate(new Date());
		return transferRepository.save(transfer);
	}
	
	public ResponseEntity<?> getTransfers() {
		return new ResponseEntity<>(transferRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> getTransfer(int id) {
		return new ResponseEntity<Transfer>(transferRepository.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteTransfer(int id) {
		Transfer transfer = transferRepository.findById(id);
		transfer.getConfiguration().getTransfers().remove(transfer);
		transfer.setConfiguration(null);
		transferRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateTransfer(Transfer transfer) {
		Transfer oldTransfer = transferRepository.findById(transfer.getId());
		oldTransfer.setConfiguration(transfer.getConfiguration());
		oldTransfer.setType(transfer.getType());
		return new ResponseEntity<>(transferRepository.save(oldTransfer), HttpStatus.OK);
	}

}
