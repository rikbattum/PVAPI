package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import nl.paardenvriendjes.pvapi.enumerations.EventType;

@Entity
@Cacheable("other")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Max(9999999)
	private Long id;
	@Size(min = 2, max = 25)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String eventName;
	@Enumerated(EnumType.STRING)
	private EventType eventtype; 
	@Temporal(TemporalType.DATE)
	private Date eventDate;
	@Temporal(TemporalType.DATE)
	private Date createdOnDate;
	@Temporal(TemporalType.DATE)
	private Date deactivatedDate;
	@Size(min = 2, max = 20)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String Message;
	@Size(min = 2, max = 20)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String messageScore;
	@Min(0)
	@Max (1000)
	private int score; 
	@Min(0)
	@Max (1000)
	private int ranking;
	private Boolean active;
	@ManyToMany
	@NotNull
	private List <Member> members = new ArrayList <Member>();
	@ManyToOne
	@NotNull
	private Paspoort paspoort;
	@ManyToMany (mappedBy="events")
	private List <Horse> horses = new ArrayList<Horse>();
	private Boolean postEventOnTimeline;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getCreatedOnDate() {
		return createdOnDate;
	}
	public void setCreatedOnDate() {
		// set-date in backend;
		this.createdOnDate = new Date();
	}
	public Date getDeactivatedDate() {
		return deactivatedDate;
	}
	public void setDeactivatedDate() {
		// set-date in backend;
		this.deactivatedDate = new Date();
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Paspoort getPaspoort() {
		return paspoort;
	}
	public void setPaspoort(Paspoort paspoort) {
		this.paspoort = paspoort;
	}
	public String getMessageScore() {
		return messageScore;
	}
	public void setMessageScore(String messageScore) {
		this.messageScore = messageScore;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}	
	public List<Horse> getHorses() {
		return horses;
	}
	public void setHorses(List<Horse> horses) {
		this.horses = horses;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}	
	public Boolean getPostEventOnTimeline() {
		return postEventOnTimeline;
	}
	public void setPostEventOnTimeline(Boolean postEventOnTimeline) {
		this.postEventOnTimeline = postEventOnTimeline;
	}
	public EventType getEventtype() {
		return eventtype;
	}
	public void setEventtype(EventType eventtype) {
		this.eventtype = eventtype;
	}

		
	// ToString
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", eventName=" + eventName + ", eventtype=" + eventtype + ", eventDate=" + eventDate
				+ ", createdOnDate=" + createdOnDate + ", deactivatedDate=" + deactivatedDate + ", Message=" + Message
				+ ", messageScore=" + messageScore + ", score=" + score + ", ranking=" + ranking + ", active=" + active
				+ ", members=" + members + ", paspoort=" + paspoort + ", horses=" + horses + ", postEventOnTimeline="
				+ postEventOnTimeline + "]";
	}
	
	
	// Hashcode and equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Message == null) ? 0 : Message.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((createdOnDate == null) ? 0 : createdOnDate.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((eventtype == null) ? 0 : eventtype.hashCode());
		result = prime * result + ((horses == null) ? 0 : horses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
		result = prime * result + ((messageScore == null) ? 0 : messageScore.hashCode());
		result = prime * result + ((paspoort == null) ? 0 : paspoort.hashCode());
		result = prime * result + ((postEventOnTimeline == null) ? 0 : postEventOnTimeline.hashCode());
		result = prime * result + ranking;
		result = prime * result + score;
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
		Event other = (Event) obj;
		if (Message == null) {
			if (other.Message != null)
				return false;
		} else if (!Message.equals(other.Message))
			return false;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (createdOnDate == null) {
			if (other.createdOnDate != null)
				return false;
		} else if (!createdOnDate.equals(other.createdOnDate))
			return false;
		if (deactivatedDate == null) {
			if (other.deactivatedDate != null)
				return false;
		} else if (!deactivatedDate.equals(other.deactivatedDate))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (eventtype != other.eventtype)
			return false;
		if (horses == null) {
			if (other.horses != null)
				return false;
		} else if (!horses.equals(other.horses))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (members == null) {
			if (other.members != null)
				return false;
		} else if (!members.equals(other.members))
			return false;
		if (messageScore == null) {
			if (other.messageScore != null)
				return false;
		} else if (!messageScore.equals(other.messageScore))
			return false;
		if (paspoort == null) {
			if (other.paspoort != null)
				return false;
		} else if (!paspoort.equals(other.paspoort))
			return false;
		if (postEventOnTimeline == null) {
			if (other.postEventOnTimeline != null)
				return false;
		} else if (!postEventOnTimeline.equals(other.postEventOnTimeline))
			return false;
		if (ranking != other.ranking)
			return false;
		if (score != other.score)
			return false;
		return true;
	}	

}
	
	
	