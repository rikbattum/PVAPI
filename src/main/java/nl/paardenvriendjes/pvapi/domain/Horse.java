package nl.paardenvriendjes.pvapi.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Horse {

	//Properties
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	private String name; 
	private String horseimage1;
	private String horseimage2;
	private String horseimage3;
	private String afstamming;
	private Date geboortedatum;
	private String geslacht;
	private String karakter;
	private String overmijnpaard;
	private int waarde;
	@ManyToOne
	private Member member;
	

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
	public String getGeslacht() {
		return geslacht;
	}
	public void setGeslacht(String geslacht) {
		this.geslacht = geslacht;
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
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	//toString
	
	@Override
	public String toString() {
		return "Horse [id=" + id + ", name=" + name + ", afstamming=" + afstamming + ", geboortedatum=" + geboortedatum
				+ ", geslacht=" + geslacht + "]";
	}
	
	//Hashcode
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((afstamming == null) ? 0 : afstamming.hashCode());
		result = prime * result + ((geboortedatum == null) ? 0 : geboortedatum.hashCode());
		result = prime * result + ((geslacht == null) ? 0 : geslacht.hashCode());
		result = prime * result + ((horseimage1 == null) ? 0 : horseimage1.hashCode());
		result = prime * result + ((horseimage2 == null) ? 0 : horseimage2.hashCode());
		result = prime * result + ((horseimage3 == null) ? 0 : horseimage3.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((karakter == null) ? 0 : karakter.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((overmijnpaard == null) ? 0 : overmijnpaard.hashCode());
		result = prime * result + waarde;
		return result;
	}
	
	
	//  Equals
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horse other = (Horse) obj;
		if (afstamming == null) {
			if (other.afstamming != null)
				return false;
		} else if (!afstamming.equals(other.afstamming))
			return false;
		if (geboortedatum == null) {
			if (other.geboortedatum != null)
				return false;
		} else if (!geboortedatum.equals(other.geboortedatum))
			return false;
		if (geslacht == null) {
			if (other.geslacht != null)
				return false;
		} else if (!geslacht.equals(other.geslacht))
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
		if (overmijnpaard == null) {
			if (other.overmijnpaard != null)
				return false;
		} else if (!overmijnpaard.equals(other.overmijnpaard))
			return false;
		if (waarde != other.waarde)
			return false;
		return true;
	}
}
