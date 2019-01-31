package nl.paardenvriendjes.pvapi.data;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Interesse {

	private Boolean lesgeven;
	private Boolean leskrijgen;
	private Boolean paardentrainendressuur;
	private Boolean paardentrainenspringen;
	private Boolean Paardrijvakantieskampen; 
	private Boolean samenbuitenrijden;
	private Boolean samenwedstrijdenrijden;
	private Boolean vervoerlenen;
	private Boolean vervoeruitlenen;
	private Boolean paardenwelzijn;
	private Boolean paardencoaching;
	private Boolean paardenfitness;
	private Boolean ruiterfitness;
	private Boolean paardenverzorging; 
	private Boolean keuringen; 
	private Boolean manageexploitatie;
	private Boolean stalbeheer;
	private Boolean ruiterenmenroutes; 
	private Boolean verenigingswezen;
	private Boolean paardenshow;
	private Boolean clinicsendemos; 
	private Boolean nationaleevenementen; 
	private Boolean internationaleevenementen; 
	private Boolean ruitersportartikelen; 
	private Boolean scheidsrechter;
	private Boolean verzekeringenenrechtsbijstand;

}


