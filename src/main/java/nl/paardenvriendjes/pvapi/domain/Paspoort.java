package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Cacheable("paspoort")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Paspoort {

	// Properties of Paspoort

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@Size(min = 2, max = 20)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String paspoortName;
	@OneToOne
	@NotNull
	private Horse horse;
	private Boolean active;
	@Temporal(TemporalType.DATE)
	private Date createdOn;
	@Temporal(TemporalType.DATE)
	private Date deactivatedDate;

	// Collections of Member
	@OneToMany
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	private List<Event> events = new ArrayList<Event>();

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaspoortName() {
		return paspoortName;
	}

	public void setPaspoortName(String paspoortName) {
		this.paspoortName = paspoortName;
	}

	public Horse getHorse() {
		return horse;
	}

	public void setHorse(Horse horse) {
		this.horse = horse;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn() {
		// set-date in backend;
		this.createdOn = new Date();
	}

	public Date getDeactivatedDate() {
		return deactivatedDate;
	}

	public void setDeactivatedDate() {
		this.deactivatedDate = new Date();
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	// ToString

	@Override
	public String toString() {
		return "Paspoort [id=" + id + ", paspoortName=" + paspoortName + ", horse=" + horse + ", active=" + active
				+ ", createdOn=" + createdOn + ", deactivatedDate=" + deactivatedDate + ", events=" + events + "]";
	}

	// Hashcode and Equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((horse == null) ? 0 : horse.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paspoortName == null) ? 0 : paspoortName.hashCode());
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
		Paspoort other = (Paspoort) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
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
		if (horse == null) {
			if (other.horse != null)
				return false;
		} else if (!horse.equals(other.horse))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paspoortName == null) {
			if (other.paspoortName != null)
				return false;
		} else if (!paspoortName.equals(other.paspoortName))
			return false;
		return true;
	}

	// convenience methods for cardinality with events

	public void addOrUpdateEvent(Event event) {

		if (event == null) {
			throw new NullPointerException("add null event can not be possible");
		}
		if (event.getPaspoort() != null && event.getPaspoort() != this) {
			throw new IllegalArgumentException("Event is already assigned to an other Paspoort");
		}
		getEvents().add(event);
		event.setPaspoort(this);
	}

	public void removeLike(Event event) {

		if (event == null) {
			throw new NullPointerException("delete null event can not be possible");
		}
		if (event.getPaspoort() != null && event.getPaspoort() != this) {
			throw new IllegalArgumentException("Event is already assigned to an other Paspoort");
		}
		getEvents().remove(event);
	}
}
