package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;


	
	@Entity

public class Paspoort {

		//Properties
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@NotNull
		private Long id;
		private String paspoortName;
		@OneToOne
		private Horse horse;
		private Boolean active; 	
		@OneToMany
		private List <Event> events = new ArrayList<Event>();
	
}
