package nl.paardenvriendjes.pvapi.data;

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
import javax.validation.constraints.Pattern;
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

import nl.paardenvriendjes.pvapi.data.enums.EventType;

@Entity
@Cacheable("event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Data
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
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String Message;
	@Size(min = 2, max = 20)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String messageScore;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String piclink;
	@Min(0)
	@Max (1000)
	private int score; 
	@Min(0)
	@Max (1000)
	private int ranking;
	private Boolean active;
	@NotNull
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
	

	public void setCreatedOnDate() {
		// set-date in backend;
		this.createdOnDate = new Date();
	}
	public void setDeactivatedDate() {
		// set-date in backend;
		this.deactivatedDate = new Date();
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
	
	
	