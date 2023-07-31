package com.tn.uib.uibechanges.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.Set;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		return transferRepository.save(transfer);
	}
	
	public ResponseEntity<?> getTransfers() {
		return new ResponseEntity<>(transferRepository.findAll(), HttpStatus.OK);
	}
	
	public Page<Transfer> getPaginatedTransfers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return transferRepository.findAll(pageable);
    }

	public ResponseEntity<?> getTransfer(int id) {
		return new ResponseEntity<Transfer>(transferRepository.findById(id), HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteTransfer(int id) {
		Transfer transfer = transferRepository.findById(id);
		if (calculateDifferenceInDays(new java.sql.Date(transfer.getDate().getTime()),new java.sql.Date(new java.util.Date().getTime()))<60){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		transfer.getConfiguration().getTransfers().remove(transfer);
		transfer.setConfiguration(null);
		transferRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public ResponseEntity<?> updateTransfer(Transfer transfer) {
		Transfer oldTransfer = transferRepository.findById(transfer.getId()).get();
		oldTransfer.setConfiguration(transfer.getConfiguration());
		oldTransfer.setType(transfer.getType());
		return new ResponseEntity<>(transferRepository.save(oldTransfer), HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteByDateBetween(Date startDate, Date endDate) throws ParseException {
		if (startDate.before(endDate) || calculateDifferenceInDays(startDate,endDate)<30){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		Set<Transfer> tranfersToDelete = transferRepository.findAllByDateBetween(startDate,endDate);
		tranfersToDelete.forEach(transfer->{
			transfer.getConfiguration().getTransfers().remove(transfer);
			transfer.setConfiguration(null);
			transferRepository.deleteById(transfer.getId());
		});
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private static long calculateDifferenceInDays(Date date1, Date date2) {
		LocalDate localDate1 = date1.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		LocalDate localDate2 = date2.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

		return ChronoUnit.DAYS.between(localDate1, localDate2);
	}
}
