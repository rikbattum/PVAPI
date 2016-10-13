package nl.paardenvriendjes.pvapi.domain;

import javax.persistence.Embeddable;

@Embeddable
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
	
	public Boolean getLesgeven() {
		return lesgeven;
	}
	public void setLesgeven(Boolean lesgeven) {
		this.lesgeven = lesgeven;
	}
	public Boolean getLeskrijgen() {
		return leskrijgen;
	}
	public void setLeskrijgen(Boolean leskrijgen) {
		this.leskrijgen = leskrijgen;
	}
	public Boolean getPaardentrainendressuur() {
		return paardentrainendressuur;
	}
	public void setPaardentrainendressuur(Boolean paardentrainendressuur) {
		this.paardentrainendressuur = paardentrainendressuur;
	}
	public Boolean getPaardentrainenspringen() {
		return paardentrainenspringen;
	}
	public void setPaardentrainenspringen(Boolean paardentrainenspringen) {
		this.paardentrainenspringen = paardentrainenspringen;
	}
	public Boolean getPaardrijvakantieskampen() {
		return Paardrijvakantieskampen;
	}
	public void setPaardrijvakantieskampen(Boolean paardrijvakantieskampen) {
		Paardrijvakantieskampen = paardrijvakantieskampen;
	}
	public Boolean getSamenbuitenrijden() {
		return samenbuitenrijden;
	}
	public void setSamenbuitenrijden(Boolean samenbuitenrijden) {
		this.samenbuitenrijden = samenbuitenrijden;
	}
	public Boolean getSamenwedstrijdenrijden() {
		return samenwedstrijdenrijden;
	}
	public void setSamenwedstrijdenrijden(Boolean samenwedstrijdenrijden) {
		this.samenwedstrijdenrijden = samenwedstrijdenrijden;
	}
	public Boolean getVervoerlenen() {
		return vervoerlenen;
	}
	public void setVervoerlenen(Boolean vervoerlenen) {
		this.vervoerlenen = vervoerlenen;
	}
	public Boolean getVervoeruitlenen() {
		return vervoeruitlenen;
	}
	public void setVervoeruitlenen(Boolean vervoeruitlenen) {
		this.vervoeruitlenen = vervoeruitlenen;
	}
	public Boolean getPaardenwelzijn() {
		return paardenwelzijn;
	}
	public void setPaardenwelzijn(Boolean paardenwelzijn) {
		this.paardenwelzijn = paardenwelzijn;
	}
	public Boolean getPaardencoaching() {
		return paardencoaching;
	}
	public void setPaardencoaching(Boolean paardencoaching) {
		this.paardencoaching = paardencoaching;
	}
	public Boolean getPaardenfitness() {
		return paardenfitness;
	}
	public void setPaardenfitness(Boolean paardenfitness) {
		this.paardenfitness = paardenfitness;
	}
	public Boolean getRuiterfitness() {
		return ruiterfitness;
	}
	public void setRuiterfitness(Boolean ruiterfitness) {
		this.ruiterfitness = ruiterfitness;
	}
	public Boolean getPaardenverzorging() {
		return paardenverzorging;
	}
	public void setPaardenverzorging(Boolean paardenverzorging) {
		this.paardenverzorging = paardenverzorging;
	}
	public Boolean getKeuringen() {
		return keuringen;
	}
	public void setKeuringen(Boolean keuringen) {
		this.keuringen = keuringen;
	}
	public Boolean getManageexploitatie() {
		return manageexploitatie;
	}
	public void setManageexploitatie(Boolean manageexploitatie) {
		this.manageexploitatie = manageexploitatie;
	}
	public Boolean getStalbeheer() {
		return stalbeheer;
	}
	public void setStalbeheer(Boolean stalbeheer) {
		this.stalbeheer = stalbeheer;
	}
	public Boolean getRuiterenmenroutes() {
		return ruiterenmenroutes;
	}
	public void setRuiterenmenroutes(Boolean ruiterenmenroutes) {
		this.ruiterenmenroutes = ruiterenmenroutes;
	}
	public Boolean getVerenigingswezen() {
		return verenigingswezen;
	}
	public void setVerenigingswezen(Boolean verenigingswezen) {
		this.verenigingswezen = verenigingswezen;
	}
	public Boolean getPaardenshow() {
		return paardenshow;
	}
	public void setPaardenshow(Boolean paardenshow) {
		this.paardenshow = paardenshow;
	}
	public Boolean getClinicsendemos() {
		return clinicsendemos;
	}
	public void setClinicsendemos(Boolean clinicsendemos) {
		this.clinicsendemos = clinicsendemos;
	}
	public Boolean getNationaleevenementen() {
		return nationaleevenementen;
	}
	public void setNationaleevenementen(Boolean nationaleevenementen) {
		this.nationaleevenementen = nationaleevenementen;
	}
	public Boolean getInternationaleevenementen() {
		return internationaleevenementen;
	}
	public void setInternationaleevenementen(Boolean internationaleevenementen) {
		this.internationaleevenementen = internationaleevenementen;
	}
	public Boolean getRuitersportartikelen() {
		return ruitersportartikelen;
	}
	public void setRuitersportartikelen(Boolean ruitersportartikelen) {
		this.ruitersportartikelen = ruitersportartikelen;
	}
	public Boolean getScheidsrechter() {
		return scheidsrechter;
	}
	public void setScheidsrechter(Boolean scheidsrechter) {
		this.scheidsrechter = scheidsrechter;
	}
	public Boolean getVerzekeringenenrechtsbijstand() {
		return verzekeringenenrechtsbijstand;
	}
	public void setVerzekeringenenrechtsbijstand(Boolean verzekeringenenrechtsbijstand) {
		this.verzekeringenenrechtsbijstand = verzekeringenenrechtsbijstand;
	}
	
	// toString 
	
	@Override
	public String toString() {
		return "Interesse [lesgeven=" + lesgeven + ", leskrijgen=" + leskrijgen + ", paardentrainendressuur="
				+ paardentrainendressuur + ", paardentrainenspringen=" + paardentrainenspringen
				+ ", Paardrijvakantieskampen=" + Paardrijvakantieskampen + ", samenbuitenrijden=" + samenbuitenrijden
				+ ", samenwedstrijdenrijden=" + samenwedstrijdenrijden + ", vervoerlenen=" + vervoerlenen
				+ ", vervoeruitlenen=" + vervoeruitlenen + ", paardenwelzijn=" + paardenwelzijn + ", paardencoaching="
				+ paardencoaching + ", paardenfitness=" + paardenfitness + ", ruiterfitness=" + ruiterfitness
				+ ", paardenverzorging=" + paardenverzorging + ", keuringen=" + keuringen + ", manageexploitatie="
				+ manageexploitatie + ", stalbeheer=" + stalbeheer + ", ruiterenmenroutes=" + ruiterenmenroutes
				+ ", verenigingswezen=" + verenigingswezen + ", paardenshow=" + paardenshow + ", clinicsendemos="
				+ clinicsendemos + ", nationaleevenementen=" + nationaleevenementen + ", internationaleevenementen="
				+ internationaleevenementen + ", ruitersportartikelen=" + ruitersportartikelen + ", scheidsrechter="
				+ scheidsrechter + ", verzekeringenenrechtsbijstand=" + verzekeringenenrechtsbijstand + "]";
	}
	
	// Hashcode and equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Paardrijvakantieskampen == null) ? 0 : Paardrijvakantieskampen.hashCode());
		result = prime * result + ((clinicsendemos == null) ? 0 : clinicsendemos.hashCode());
		result = prime * result + ((internationaleevenementen == null) ? 0 : internationaleevenementen.hashCode());
		result = prime * result + ((keuringen == null) ? 0 : keuringen.hashCode());
		result = prime * result + ((lesgeven == null) ? 0 : lesgeven.hashCode());
		result = prime * result + ((leskrijgen == null) ? 0 : leskrijgen.hashCode());
		result = prime * result + ((manageexploitatie == null) ? 0 : manageexploitatie.hashCode());
		result = prime * result + ((nationaleevenementen == null) ? 0 : nationaleevenementen.hashCode());
		result = prime * result + ((paardencoaching == null) ? 0 : paardencoaching.hashCode());
		result = prime * result + ((paardenfitness == null) ? 0 : paardenfitness.hashCode());
		result = prime * result + ((paardenshow == null) ? 0 : paardenshow.hashCode());
		result = prime * result + ((paardentrainendressuur == null) ? 0 : paardentrainendressuur.hashCode());
		result = prime * result + ((paardentrainenspringen == null) ? 0 : paardentrainenspringen.hashCode());
		result = prime * result + ((paardenverzorging == null) ? 0 : paardenverzorging.hashCode());
		result = prime * result + ((paardenwelzijn == null) ? 0 : paardenwelzijn.hashCode());
		result = prime * result + ((ruiterenmenroutes == null) ? 0 : ruiterenmenroutes.hashCode());
		result = prime * result + ((ruiterfitness == null) ? 0 : ruiterfitness.hashCode());
		result = prime * result + ((ruitersportartikelen == null) ? 0 : ruitersportartikelen.hashCode());
		result = prime * result + ((samenbuitenrijden == null) ? 0 : samenbuitenrijden.hashCode());
		result = prime * result + ((samenwedstrijdenrijden == null) ? 0 : samenwedstrijdenrijden.hashCode());
		result = prime * result + ((scheidsrechter == null) ? 0 : scheidsrechter.hashCode());
		result = prime * result + ((stalbeheer == null) ? 0 : stalbeheer.hashCode());
		result = prime * result + ((verenigingswezen == null) ? 0 : verenigingswezen.hashCode());
		result = prime * result + ((vervoerlenen == null) ? 0 : vervoerlenen.hashCode());
		result = prime * result + ((vervoeruitlenen == null) ? 0 : vervoeruitlenen.hashCode());
		result = prime * result
				+ ((verzekeringenenrechtsbijstand == null) ? 0 : verzekeringenenrechtsbijstand.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interesse other = (Interesse) obj;
		if (Paardrijvakantieskampen == null) {
			if (other.Paardrijvakantieskampen != null)
				return false;
		} else if (!Paardrijvakantieskampen.equals(other.Paardrijvakantieskampen))
			return false;
		if (clinicsendemos == null) {
			if (other.clinicsendemos != null)
				return false;
		} else if (!clinicsendemos.equals(other.clinicsendemos))
			return false;
		if (internationaleevenementen == null) {
			if (other.internationaleevenementen != null)
				return false;
		} else if (!internationaleevenementen.equals(other.internationaleevenementen))
			return false;
		if (keuringen == null) {
			if (other.keuringen != null)
				return false;
		} else if (!keuringen.equals(other.keuringen))
			return false;
		if (lesgeven == null) {
			if (other.lesgeven != null)
				return false;
		} else if (!lesgeven.equals(other.lesgeven))
			return false;
		if (leskrijgen == null) {
			if (other.leskrijgen != null)
				return false;
		} else if (!leskrijgen.equals(other.leskrijgen))
			return false;
		if (manageexploitatie == null) {
			if (other.manageexploitatie != null)
				return false;
		} else if (!manageexploitatie.equals(other.manageexploitatie))
			return false;
		if (nationaleevenementen == null) {
			if (other.nationaleevenementen != null)
				return false;
		} else if (!nationaleevenementen.equals(other.nationaleevenementen))
			return false;
		if (paardencoaching == null) {
			if (other.paardencoaching != null)
				return false;
		} else if (!paardencoaching.equals(other.paardencoaching))
			return false;
		if (paardenfitness == null) {
			if (other.paardenfitness != null)
				return false;
		} else if (!paardenfitness.equals(other.paardenfitness))
			return false;
		if (paardenshow == null) {
			if (other.paardenshow != null)
				return false;
		} else if (!paardenshow.equals(other.paardenshow))
			return false;
		if (paardentrainendressuur == null) {
			if (other.paardentrainendressuur != null)
				return false;
		} else if (!paardentrainendressuur.equals(other.paardentrainendressuur))
			return false;
		if (paardentrainenspringen == null) {
			if (other.paardentrainenspringen != null)
				return false;
		} else if (!paardentrainenspringen.equals(other.paardentrainenspringen))
			return false;
		if (paardenverzorging == null) {
			if (other.paardenverzorging != null)
				return false;
		} else if (!paardenverzorging.equals(other.paardenverzorging))
			return false;
		if (paardenwelzijn == null) {
			if (other.paardenwelzijn != null)
				return false;
		} else if (!paardenwelzijn.equals(other.paardenwelzijn))
			return false;
		if (ruiterenmenroutes == null) {
			if (other.ruiterenmenroutes != null)
				return false;
		} else if (!ruiterenmenroutes.equals(other.ruiterenmenroutes))
			return false;
		if (ruiterfitness == null) {
			if (other.ruiterfitness != null)
				return false;
		} else if (!ruiterfitness.equals(other.ruiterfitness))
			return false;
		if (ruitersportartikelen == null) {
			if (other.ruitersportartikelen != null)
				return false;
		} else if (!ruitersportartikelen.equals(other.ruitersportartikelen))
			return false;
		if (samenbuitenrijden == null) {
			if (other.samenbuitenrijden != null)
				return false;
		} else if (!samenbuitenrijden.equals(other.samenbuitenrijden))
			return false;
		if (samenwedstrijdenrijden == null) {
			if (other.samenwedstrijdenrijden != null)
				return false;
		} else if (!samenwedstrijdenrijden.equals(other.samenwedstrijdenrijden))
			return false;
		if (scheidsrechter == null) {
			if (other.scheidsrechter != null)
				return false;
		} else if (!scheidsrechter.equals(other.scheidsrechter))
			return false;
		if (stalbeheer == null) {
			if (other.stalbeheer != null)
				return false;
		} else if (!stalbeheer.equals(other.stalbeheer))
			return false;
		if (verenigingswezen == null) {
			if (other.verenigingswezen != null)
				return false;
		} else if (!verenigingswezen.equals(other.verenigingswezen))
			return false;
		if (vervoerlenen == null) {
			if (other.vervoerlenen != null)
				return false;
		} else if (!vervoerlenen.equals(other.vervoerlenen))
			return false;
		if (vervoeruitlenen == null) {
			if (other.vervoeruitlenen != null)
				return false;
		} else if (!vervoeruitlenen.equals(other.vervoeruitlenen))
			return false;
		if (verzekeringenenrechtsbijstand == null) {
			if (other.verzekeringenenrechtsbijstand != null)
				return false;
		} else if (!verzekeringenenrechtsbijstand.equals(other.verzekeringenenrechtsbijstand))
			return false;
		return true;
	}
}


