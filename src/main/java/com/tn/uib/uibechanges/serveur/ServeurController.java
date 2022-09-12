package com.tn.uib.uibechanges.serveur;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			
	
	
	@GetMapping(path = "{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NWADMIN')")
	public Serveur getServeur(@PathVariable("id") int id) {
		return SERVEURS.stream()
				.filter(serveur -> id==serveur.getId())
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("serveur "+id+" does not exist"));

	};
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_NWADMIN')")
	public List<Serveur> getServeurs() {
		return SERVEURS;

	};
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('serveur:write')")
	public Serveur addServeur(@RequestBody Serveur serv) {
		return serv;
		
	};
	
	@DeleteMapping(path="{id}")
	@PreAuthorize("hasAuthority('serveur:write')")
	public void deleteServeur(@PathVariable("id") int id) {
		System.out.println("delete serveur with id: "+id);
		System.out.println(SERVEURS.stream()
				.filter(serveur -> id==serveur.getId())
				.findFirst()
		);
	};
	
	@PutMapping(path = "{id}")
	@PreAuthorize("hasAuthority('serveur:write')")
	public void updateServeur(@PathVariable("id") int id) {
		System.out.println("update serveur with id: "+id);
		System.out.println(SERVEURS.stream()
				.filter(serveur -> id==serveur.getId())
				.findFirst()
		);
		
	};
};