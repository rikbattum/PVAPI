package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import nl.paardenvriendjes.enumerations.Geslacht;
import nl.paardenvriendjes.enumerations.PaardType;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Horse {

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	private String name;
	private String horseimage1;
	private String horseimage2;
	private String horseimage3;
	private String afstamming;
	@Temporal(TemporalType.DATE)
	private Date geboortedatum;
	private Geslacht geslacht;
	private int stokmaat; 
	private String karakter;
	private String overmijnpaard;
	private Boolean overleden;
	private int waarde;
	@Temporal(TemporalType.DATE)
	private Date createdonDate;
	@Temporal(TemporalType.DATE)
	private Date deactivatedDate;
	@ManyToMany
	private List<Event> events = new ArrayList<Event>();
	@OneToOne
	private Paspoort paspoort;
	@ManyToOne
	@JsonBackReference
	private Member member;
	@ElementCollection
	private Map<String, String> sports = new HashMap<String, String>();
	@Enumerated(EnumType.STRING)
	private PaardType paardType; 
	private Boolean active;

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHorseimage1() {
		return horseimage1;
	}

	public void setHorseimage1(String horseimage1) {
		this.horseimage1 = horseimage1;
	}

	public String getHorseimage2() {
		return horseimage2;
	}

	public void setHorseimage2(String horseimage2) {
		this.horseimage2 = horseimage2;
	}

	public String getHorseimage3() {
		return horseimage3;
	}

	public void setHorseimage3(String horseimage3) {
		this.horseimage3 = horseimage3;
	}

	public String getAfstamming() {
		return afstamming;
	}

	public void setAfstamming(String afstamming) {
		this.afstamming = afstamming;
	}

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}

	public int getStokmaat() {
		return stokmaat;
	}

	public void setStokmaat(int stokmaat) {
		this.stokmaat = stokmaat;
	}

	public String getKarakter() {
		return karakter;
	}

	public void setKarakter(String karakter) {
		this.karakter = karakter;
	}

	public String getOvermijnpaard() {
		return overmijnpaard;
	}

	public void setOvermijnpaard(String overmijnpaard) {
		this.overmijnpaard = overmijnpaard;
	}

	public int getWaarde() {
		return waarde;
	}

	public void setWaarde(int waarde) {
		this.waarde = waarde;
	}

	public Boolean getOverleden() {
		return overleden;
	}

	public void setOverleden(Boolean overleden) {
		this.overleden = overleden;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public Paspoort getPaspoort() {
		return paspoort;
	}

	public void setPaspoort(Paspoort paspoort) {
		this.paspoort = paspoort;
	}
	
	public Map<String, String> getSports() {
		return sports;
	}

	public void setSports(Map<String, String> sports) {
		this.sports = sports;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	public PaardType getPaardType() {
		return paardType;
	}

	public void setPaardType(PaardType paardType) {
		this.paardType = paardType;
	}
	public Date getCreatedonDate() {
		return createdonDate;
	}

	public void setCreatedonDate() {
		// set date from backend
		this.createdonDate = new Date();
	}
	
	public Date getDeactivatedDate() {
		return deactivatedDate;
	}

	public void setDeactivatedDate() {
		// set date from backend
		this.deactivatedDate = new Date();
	} 	
	
	// toString

	@Override
	public String toString() {
		return "Horse [id=" + id + ", name=" + name + ", horseimage1=" + horseimage1 + ", horseimage2=" + horseimage2
				+ ", horseimage3=" + horseimage3 + ", afstamming=" + afstamming + ", geboortedatum=" + geboortedatum
				+ ", geslacht=" + geslacht + ", stokmaat=" + stokmaat + ", karakter=" + karakter + ", overmijnpaard="
				+ overmijnpaard + ", overleden=" + overleden + ", waarde=" + waarde + ", createdonDate=" + createdonDate
				+ ", deactivatedDate=" + deactivatedDate + ", events=" + events + ", paspoort=" + paspoort + ", member="
				+ member + ", sports=" + sports + ", paardType=" + paardType + ", active=" + active + "]";
	}
	
	// Hashcode and equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((afstamming == null) ? 0 : afstamming.hashCode());
		result = prime * result + ((createdonDate == null) ? 0 : createdonDate.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((geboortedatum == null) ? 0 : geboortedatum.hashCode());
		result = prime * result + ((geslacht == null) ? 0 : geslacht.hashCode());
		result = prime * result + ((horseimage1 == null) ? 0 : horseimage1.hashCode());
		result = prime * result + ((horseimage2 == null) ? 0 : horseimage2.hashCode());
		result = prime * result + ((horseimage3 == null) ? 0 : horseimage3.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((karakter == null) ? 0 : karakter.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((overleden == null) ? 0 : overleden.hashCode());
		result = prime * result + ((overmijnpaard == null) ? 0 : overmijnpaard.hashCode());
		result = prime * result + ((paardType == null) ? 0 : paardType.hashCode());
		result = prime * result + ((paspoort == null) ? 0 : paspoort.hashCode());
		result = prime * result + ((sports == null) ? 0 : sports.hashCode());
		result = prime * result + stokmaat;
		result = prime * result + waarde;
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
		Horse other = (Horse) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (afstamming == null) {
			if (other.afstamming != null)
				return false;
		} else if (!afstamming.equals(other.afstamming))
			return false;
		if (createdonDate == null) {
			if (other.createdonDate != null)
				return false;
		} else if (!createdonDate.equals(other.createdonDate))
			return false;
		if (deactivatedDate == null) {
			if (other.deactivatedDate != null)
				return false;
		} else if (!deactivatedDate.equals(other.deactivatedDate))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (geboortedatum == null) {
			if (other.geboortedatum != null)
				return false;
		} else if (!geboortedatum.equals(other.geboortedatum))
			return false;
		if (geslacht != other.geslacht)
			return false;
		if (horseimage1 == null) {
			if (other.horseimage1 != null)
				return false;
		} else if (!horseimage1.equals(other.horseimage1))
			return false;
		if (horseimage2 == null) {
			if (other.horseimage2 != null)
				return false;
		} else if (!horseimage2.equals(other.horseimage2))
			return false;
		if (horseimage3 == null) {
			if (other.horseimage3 != null)
				return false;
		} else if (!horseimage3.equals(other.horseimage3))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (karakter == null) {
			if (other.karakter != null)
				return false;
		} else if (!karakter.equals(other.karakter))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (overleden == null) {
			if (other.overleden != null)
				return false;
		} else if (!overleden.equals(other.overleden))
			return false;
		if (overmijnpaard == null) {
			if (other.overmijnpaard != null)
				return false;
		} else if (!overmijnpaard.equals(other.overmijnpaard))
			return false;
		if (paardType != other.paardType)
			return false;
		if (paspoort == null) {
			if (other.paspoort != null)
				return false;
		} else if (!paspoort.equals(other.paspoort))
			return false;
		if (sports == null) {
			if (other.sports != null)
				return false;
		} else if (!sports.equals(other.sports))
			return false;
		if (stokmaat != other.stokmaat)
			return false;
		if (waarde != other.waarde)
			return false;
		return true;
	}

	// convenience methods for working with SportsMap
	
	public void addSportToMap(String sporttype, String sportlevel) { 

		if (sporttype == null || sportlevel == null) { 
			throw new NullPointerException("add null to sportsmap can not be possible");
		}
		else { 
		sports.put(sporttype, sportlevel);
		}
	}
	
	public void removeSportFromMap(String sporttype) { 


		if (sporttype == null) { 
			throw new NullPointerException("delete null from sportsmap can not be possible");
		}
		else { 
		this.sports.remove(sporttype);
		} 
	}
}
