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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

import nl.paardenvriendjes.pvapi.enumerations.EventType;

@Entity
@Cacheable("event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Event {

	// Properties of Event
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String messageScore;
	@Min(0)
	@Max (1000)
	private int score; 
	@Min(0)
	@Max (1000)
	private int ranking;
	private Boolean active;
	@ManyToOne
	private Paspoort paspoort;
	private Boolean postEventOnTimeline;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 40)
	private String eventLocation; 
	
	// Collections of Event
	
	@OneToMany(mappedBy="event", orphanRemoval=true)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DELETE})
	//	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<EventComment> eventCommentList = new ArrayList<EventComment>();
	@OneToMany(mappedBy="event", orphanRemoval=true)
	@Cascade({CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DELETE})
	//	@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<EventLike> likelist = new ArrayList<EventLike>();
	
	// Getters and Setters 	
	
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
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
	public List<EventLike> getLikelist() {
		return likelist;
	}
	public void setLikelist(List<EventLike> likelist) {
		this.likelist = likelist;
	}	
	public List<EventComment> getEventCommentList() {
		return eventCommentList;
	}
	public void setEventCommentList(List<EventComment> eventCommentList) {
		this.eventCommentList = eventCommentList;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}	
	
	// ToString

	@Override
	public String toString() {
		return "Event [id=" + id + ", eventName=" + eventName + ", eventtype=" + eventtype + ", eventDate=" + eventDate
				+ ", createdOnDate=" + createdOnDate + ", deactivatedDate=" + deactivatedDate + ", Message=" + Message
				+ ", messageScore=" + messageScore + ", score=" + score + ", ranking=" + ranking + ", active=" + active
				+ ", paspoort=" + paspoort + ", postEventOnTimeline=" + postEventOnTimeline + ", eventLocation="
				+ eventLocation + ", eventCommentList=" + eventCommentList + ", likelist=" + likelist + "]";
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
		result = prime * result + ((eventCommentList == null) ? 0 : eventCommentList.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventLocation == null) ? 0 : eventLocation.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((eventtype == null) ? 0 : eventtype.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((likelist == null) ? 0 : likelist.hashCode());
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
		if (eventCommentList == null) {
			if (other.eventCommentList != null)
				return false;
		} else if (!eventCommentList.equals(other.eventCommentList))
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventLocation == null) {
			if (other.eventLocation != null)
				return false;
		} else if (!eventLocation.equals(other.eventLocation))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		if (eventtype != other.eventtype)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (likelist == null) {
			if (other.likelist != null)
				return false;
		} else if (!likelist.equals(other.likelist))
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

	// convenience methods for cardinality with EventComments

	public void addOrUpdateComment(EventComment eventComment) {

		if (eventComment == null) {
			throw new NullPointerException("add null comment can not be possible");
		}
		if (eventComment.getEvent() != null && eventComment.getEvent() != this) {
			throw new IllegalArgumentException("comment is already assigned to an other event");
		}
		getEventCommentList().add(eventComment);
		eventComment.setEvent(this);
	}

	public void removeMessageComment(EventComment eventComment) {

		if (eventComment == null) {
			throw new NullPointerException("delete null comment can not be possible");
		}
		if (eventComment.getEvent() != null && eventComment.getEvent() != this) {
			throw new IllegalArgumentException("comment is already assigned to an other event");
		}
		getEventCommentList().remove(eventComment);
	}

	// convenience methods for cardinality with EventLikes

	public void addOrUpdateEventLike(EventLike eventLike) {

		if (eventLike == null) {
			throw new NullPointerException("add null like can not be possible");
		}
		if (eventLike.getEvent() != null && eventLike.getEvent() != this) {
			throw new IllegalArgumentException("like is already assigned to an other event");
		}
		getLikelist().add(eventLike);
		eventLike.setEvent(this);
	}

	public void removeMessageLike(EventLike eventLike) {

		if (eventLike == null) {
			throw new NullPointerException("delete null like can not be possible");
		}
		if (eventLike.getEvent() != null && eventLike.getEvent() != this) {
			throw new IllegalArgumentException("like is already assigned to an other event");
		}
		getLikelist().remove(eventLike);
	}
	
	
	
	
	
	
}
	
	
	