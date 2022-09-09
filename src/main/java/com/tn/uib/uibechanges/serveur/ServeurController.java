package com.tn.uib.uibechanges.serveur;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/serveurs")
public class ServeurController {
	
	private static final List<Serveur> SERVEURS = Arrays.asList(
			new Serveur(1, "127.0.0.1", 25, "test libelle1", "localhost1", "127.0.0.1"),
			new Serveur(2, "127.0.0.2", 21, "test libelle2", "localhost2", "127.0.0.2"),
			new Serveur(3, "127.0.0.3", 22, "test libelle3", "localhost3", "127.0.0.3")
			
	);
			
	
	@GetMapping(path = "/get/{id}")
	public Serveur getServeur(@PathVariable("id") int id) {
		return SERVEURS.stream()
				.filter(serveur -> id==serveur.getId())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("serveur "+id+" does not exist"));

	};
	
	@PostMapping(value="/add")
	public Serveur addServeur(@RequestBody Serveur serv) {
		return serv;
		
	};
};