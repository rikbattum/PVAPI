package nl.paardenvriendjes.pvapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Interesse {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne (mappedBy = "interesse")
	private Member member;
	private Boolean buitenritten;
	private Boolean draftsport;
	private Boolean dressuur;
	private Boolean endurance;
	private Boolean eventing;
	private Boolean fokken;
	private Boolean inenverkoop;
	private Boolean mennen;
	private Boolean naturalhorsemanship;
	private Boolean paardentrainen;
	private Boolean paardenverzorgen;
	private Boolean polo;
	private Boolean rensport;
	private Boolean rijdenlocatie;
	private Boolean ringsteken;
	private Boolean rodeo;
	private Boolean showrijden;
	private Boolean springen;
	private Boolean voedinggezondheid;
	private Boolean voltige;
	private Boolean voorlichting;
	private Boolean vossenjacht;
	private Boolean wedstrijdsport;
	private Boolean western;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getBuitenritten() {
		return buitenritten;
	}
	public void setBuitenritten(Boolean buitenritten) {
		this.buitenritten = buitenritten;
	}
	public Boolean getDraftsport() {
		return draftsport;
	}
	public void setDraftsport(Boolean draftsport) {
		this.draftsport = draftsport;
	}
	public Boolean getDressuur() {
		return dressuur;
	}
	public void setDressuur(Boolean dressuur) {
		this.dressuur = dressuur;
	}
	public Boolean getEndurance() {
		return endurance;
	}
	public void setEndurance(Boolean endurance) {
		this.endurance = endurance;
	}
	public Boolean getEventing() {
		return eventing;
	}
	public void setEventing(Boolean eventing) {
		this.eventing = eventing;
	}
	public Boolean getFokken() {
		return fokken;
	}
	public void setFokken(Boolean fokken) {
		this.fokken = fokken;
	}
	public Boolean getInenverkoop() {
		return inenverkoop;
	}
	public void setInenverkoop(Boolean inenverkoop) {
		this.inenverkoop = inenverkoop;
	}
	public Boolean getMennen() {
		return mennen;
	}
	public void setMennen(Boolean mennen) {
		this.mennen = mennen;
	}
	public Boolean getNaturalhorsemanship() {
		return naturalhorsemanship;
	}
	public void setNaturalhorsemanship(Boolean naturalhorsemanship) {
		this.naturalhorsemanship = naturalhorsemanship;
	}
	public Boolean getPaardentrainen() {
		return paardentrainen;
	}
	public void setPaardentrainen(Boolean paardentrainen) {
		this.paardentrainen = paardentrainen;
	}
	public Boolean getPaardenverzorgen() {
		return paardenverzorgen;
	}
	public void setPaardenverzorgen(Boolean paardenverzorgen) {
		this.paardenverzorgen = paardenverzorgen;
	}
	public Boolean getPolo() {
		return polo;
	}
	public void setPolo(Boolean polo) {
		this.polo = polo;
	}
	public Boolean getRensport() {
		return rensport;
	}
	public void setRensport(Boolean rensport) {
		this.rensport = rensport;
	}
	public Boolean getRijdenlocatie() {
		return rijdenlocatie;
	}
	public void setRijdenlocatie(Boolean rijdenlocatie) {
		this.rijdenlocatie = rijdenlocatie;
	}
	public Boolean getRingsteken() {
		return ringsteken;
	}
	public void setRingsteken(Boolean ringsteken) {
		this.ringsteken = ringsteken;
	}
	public Boolean getRodeo() {
		return rodeo;
	}
	public void setRodeo(Boolean rodeo) {
		this.rodeo = rodeo;
	}
	public Boolean getShowrijden() {
		return showrijden;
	}
	public void setShowrijden(Boolean showrijden) {
		this.showrijden = showrijden;
	}
	public Boolean getSpringen() {
		return springen;
	}
	public void setSpringen(Boolean springen) {
		this.springen = springen;
	}
	public Boolean getVoedinggezondheid() {
		return voedinggezondheid;
	}
	public void setVoedinggezondheid(Boolean voedinggezondheid) {
		this.voedinggezondheid = voedinggezondheid;
	}
	public Boolean getVoltige() {
		return voltige;
	}
	public void setVoltige(Boolean voltige) {
		this.voltige = voltige;
	}
	public Boolean getVoorlichting() {
		return voorlichting;
	}
	public void setVoorlichting(Boolean voorlichting) {
		this.voorlichting = voorlichting;
	}
	public Boolean getVossenjacht() {
		return vossenjacht;
	}
	public void setVossenjacht(Boolean vossenjacht) {
		this.vossenjacht = vossenjacht;
	}
	public Boolean getWedstrijdsport() {
		return wedstrijdsport;
	}
	public void setWedstrijdsport(Boolean wedstrijdsport) {
		this.wedstrijdsport = wedstrijdsport;
	}
	public Boolean getWestern() {
		return western;
	}
	public void setWestern(Boolean western) {
		this.western = western;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buitenritten == null) ? 0 : buitenritten.hashCode());
		result = prime * result + ((draftsport == null) ? 0 : draftsport.hashCode());
		result = prime * result + ((dressuur == null) ? 0 : dressuur.hashCode());
		result = prime * result + ((endurance == null) ? 0 : endurance.hashCode());
		result = prime * result + ((eventing == null) ? 0 : eventing.hashCode());
		result = prime * result + ((fokken == null) ? 0 : fokken.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inenverkoop == null) ? 0 : inenverkoop.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((mennen == null) ? 0 : mennen.hashCode());
		result = prime * result + ((naturalhorsemanship == null) ? 0 : naturalhorsemanship.hashCode());
		result = prime * result + ((paardentrainen == null) ? 0 : paardentrainen.hashCode());
		result = prime * result + ((paardenverzorgen == null) ? 0 : paardenverzorgen.hashCode());
		result = prime * result + ((polo == null) ? 0 : polo.hashCode());
		result = prime * result + ((rensport == null) ? 0 : rensport.hashCode());
		result = prime * result + ((rijdenlocatie == null) ? 0 : rijdenlocatie.hashCode());
		result = prime * result + ((ringsteken == null) ? 0 : ringsteken.hashCode());
		result = prime * result + ((rodeo == null) ? 0 : rodeo.hashCode());
		result = prime * result + ((showrijden == null) ? 0 : showrijden.hashCode());
		result = prime * result + ((springen == null) ? 0 : springen.hashCode());
		result = prime * result + ((voedinggezondheid == null) ? 0 : voedinggezondheid.hashCode());
		result = prime * result + ((voltige == null) ? 0 : voltige.hashCode());
		result = prime * result + ((voorlichting == null) ? 0 : voorlichting.hashCode());
		result = prime * result + ((vossenjacht == null) ? 0 : vossenjacht.hashCode());
		result = prime * result + ((wedstrijdsport == null) ? 0 : wedstrijdsport.hashCode());
		result = prime * result + ((western == null) ? 0 : western.hashCode());
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
		if (buitenritten == null) {
			if (other.buitenritten != null)
				return false;
		} else if (!buitenritten.equals(other.buitenritten))
			return false;
		if (draftsport == null) {
			if (other.draftsport != null)
				return false;
		} else if (!draftsport.equals(other.draftsport))
			return false;
		if (dressuur == null) {
			if (other.dressuur != null)
				return false;
		} else if (!dressuur.equals(other.dressuur))
			return false;
		if (endurance == null) {
			if (other.endurance != null)
				return false;
		} else if (!endurance.equals(other.endurance))
			return false;
		if (eventing == null) {
			if (other.eventing != null)
				return false;
		} else if (!eventing.equals(other.eventing))
			return false;
		if (fokken == null) {
			if (other.fokken != null)
				return false;
		} else if (!fokken.equals(other.fokken))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inenverkoop == null) {
			if (other.inenverkoop != null)
				return false;
		} else if (!inenverkoop.equals(other.inenverkoop))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (mennen == null) {
			if (other.mennen != null)
				return false;
		} else if (!mennen.equals(other.mennen))
			return false;
		if (naturalhorsemanship == null) {
			if (other.naturalhorsemanship != null)
				return false;
		} else if (!naturalhorsemanship.equals(other.naturalhorsemanship))
			return false;
		if (paardentrainen == null) {
			if (other.paardentrainen != null)
				return false;
		} else if (!paardentrainen.equals(other.paardentrainen))
			return false;
		if (paardenverzorgen == null) {
			if (other.paardenverzorgen != null)
				return false;
		} else if (!paardenverzorgen.equals(other.paardenverzorgen))
			return false;
		if (polo == null) {
			if (other.polo != null)
				return false;
		} else if (!polo.equals(other.polo))
			return false;
		if (rensport == null) {
			if (other.rensport != null)
				return false;
		} else if (!rensport.equals(other.rensport))
			return false;
		if (rijdenlocatie == null) {
			if (other.rijdenlocatie != null)
				return false;
		} else if (!rijdenlocatie.equals(other.rijdenlocatie))
			return false;
		if (ringsteken == null) {
			if (other.ringsteken != null)
				return false;
		} else if (!ringsteken.equals(other.ringsteken))
			return false;
		if (rodeo == null) {
			if (other.rodeo != null)
				return false;
		} else if (!rodeo.equals(other.rodeo))
			return false;
		if (showrijden == null) {
			if (other.showrijden != null)
				return false;
		} else if (!showrijden.equals(other.showrijden))
			return false;
		if (springen == null) {
			if (other.springen != null)
				return false;
		} else if (!springen.equals(other.springen))
			return false;
		if (voedinggezondheid == null) {
			if (other.voedinggezondheid != null)
				return false;
		} else if (!voedinggezondheid.equals(other.voedinggezondheid))
			return false;
		if (voltige == null) {
			if (other.voltige != null)
				return false;
		} else if (!voltige.equals(other.voltige))
			return false;
		if (voorlichting == null) {
			if (other.voorlichting != null)
				return false;
		} else if (!voorlichting.equals(other.voorlichting))
			return false;
		if (vossenjacht == null) {
			if (other.vossenjacht != null)
				return false;
		} else if (!vossenjacht.equals(other.vossenjacht))
			return false;
		if (wedstrijdsport == null) {
			if (other.wedstrijdsport != null)
				return false;
		} else if (!wedstrijdsport.equals(other.wedstrijdsport))
			return false;
		if (western == null) {
			if (other.western != null)
				return false;
		} else if (!western.equals(other.western))
			return false;
		return true;
	}
}
