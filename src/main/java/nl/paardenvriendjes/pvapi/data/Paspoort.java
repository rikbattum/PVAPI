package nl.paardenvriendjes.pvapi.data;

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

import lombok.Data;
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
@Data
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


	public void setCreatedOn() {
		// set-date in backend;
		this.createdOn = new Date();
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
