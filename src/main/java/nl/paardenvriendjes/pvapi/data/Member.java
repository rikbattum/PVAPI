package nl.paardenvriendjes.pvapi.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import nl.paardenvriendjes.pvapi.data.enums.Geslacht;
import nl.paardenvriendjes.pvapi.data.enums.OtherSport;
import nl.paardenvriendjes.pvapi.data.enums.Place;
import nl.paardenvriendjes.pvapi.data.enums.SportLevel;
import nl.paardenvriendjes.pvapi.data.enums.Vervoer;

@Entity
@Cacheable("membercache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@NotNull
	@Email
	@Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 7, max = 60)
	private String email;
	@NotNull
	@Size(min = 2, max = 20)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String voornaam;
	@Size(min = 2, max = 20)
	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String achternaam;
	@Size(min = 2, max = 30)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String username;
	@Temporal(TemporalType.DATE)
	private Date createdonDate;
	@Temporal(TemporalType.DATE)
	private Date deactivatedDate;
	@NotNull
	@Past
	private Date geboortedatum;
	@Size(min = 2, max = 300)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String overmij;
	@Embedded
	@Basic(fetch = FetchType.EAGER) // probably not needed
	private Interesse interesse = new Interesse();
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String profileimage;
	@Transient
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 35)
	private String password;
	@Enumerated(EnumType.STRING)
	private Place place;
	@Enumerated(EnumType.STRING)
	private SportLevel sportLevel;
	private Boolean active;
	@Enumerated(EnumType.STRING)
	private Vervoer vervoer;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 20, max = 35)
	private String auth0user_id;
	@ElementCollection
	private Map<String, String> sports = new HashMap<String, String>();
	@ElementCollection
	private Set<OtherSport> othersports = new HashSet<OtherSport>();

	// Collections of Member

	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST })
	private List<Horse> horses = new ArrayList<Horse>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST})
	private List<Message> messages = new ArrayList<Message>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST })
	private List<MessageComment> messageComments = new ArrayList<MessageComment>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST })
	private List<EventComment> eventComments = new ArrayList<EventComment>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST })
	private List<MessageLike> messageLikes = new ArrayList<MessageLike>();
	@OneToMany(mappedBy = "member")
	private List<EventLike> eventLikes = new ArrayList<EventLike>();
	@ManyToMany
	@Cascade({ CascadeType.SAVE_UPDATE})
	@JoinTable(name = "member_vrienden", joinColumns = {
			@JoinColumn(name = "membervrienden_id") }, inverseJoinColumns = { @JoinColumn(name = "vrienden_id") })
	private List<Member> vrienden = new ArrayList<Member>();
	@ManyToMany
	@Cascade({ CascadeType.ALL })
	@JoinTable(name = "member_blokkades", joinColumns = {
			@JoinColumn(name = "memberblocks_id") }, inverseJoinColumns = { @JoinColumn(name = "blokkades_id") })
	private List<Member> blokkades = new ArrayList<Member>();

		public void setCreatedonDate() {
		// set-date in backend;
		this.createdonDate = new Date();
	}
	public void setDeactivatedDate() {
		// set-date in backend;
		this.deactivatedDate = new Date();
	}

	// convenience methods for cardinality with Messages

	public void addOrUpdateMessage(Message message) {

		if (message == null) {
			throw new NullPointerException("add null message can not be possible");
		}
		if (message.getMember() != null && message.getMember() != this) {
			throw new IllegalArgumentException("message is already assigned to an other member");
		}
		getMessages().add(message);
		message.setMember(this);
	}

	public void removeMessage(Message message) {

		if (message == null) {
			throw new NullPointerException("delete null message can not be possible");
		}
		if (message.getMember() != null && message.getMember() != this) {
			throw new IllegalArgumentException("message is already assigned to an other member");
		}
		getMessages().remove(message);
	}

	// convenience methods for cardinality with Horses

	public void addOrUpdateHorse(Horse horse) {

		if (horse == null) {
			throw new NullPointerException("add null horse can not be possible");
		}
		if (horse.getMember() != null && horse.getMember() != this) {
			throw new IllegalArgumentException("horse is already assigned to an other member");
		}
		getHorses().add(horse);
		horse.setMember(this);
	}

	public void removeHorse(Horse horse) {

		if (horse == null) {
			throw new NullPointerException("delete null horse can not be possible");
		}
		if (horse.getMember() != null && horse.getMember() != this) {
			throw new IllegalArgumentException("horse is already assigned to an other member");
		}
		getHorses().remove(horse);
	}

	// convenience methods for cardinality with Friends

	public void addOrUpdateFriend(Member member) {

		if (member == null) {
			throw new NullPointerException("add null member can not be possible");
		}
		getVrienden().add(member);
	}

	public void removeFriend(Member member) {

		if (member == null) {
			throw new NullPointerException("remove null member can not be possible");
		}
		getVrienden().remove(member);
	}

	// convenience methods for cardinality with Blokkades

	public void addOrUpdateBlokkade(Member member) {

		if (member == null) {
			throw new NullPointerException("add null member blokkade can not be possible");
		}
		getBlokkades().add(member);
	}

	public void removeBlokkade(Member member) {

		if (member == null) {
			throw new NullPointerException("remove null member blokkade can not be possible");
		}
		getBlokkades().remove(member);
	}

	// convenience methods for cardinality with MessageComments

	public void addOrUpdateMessageComment(MessageComment messagecomment) {

		if (messagecomment == null) {
			throw new NullPointerException("add null messagecomment can not be possible");
		}
		if (messagecomment.getMember() != null && messagecomment.getMember() != this) {
			throw new IllegalArgumentException("messagecommentis already assigned to an other member");
		}
		getMessageComments().add(messagecomment);
		messagecomment.setMember(this);
	}

	public void removeMessageComment(MessageComment messagecomment) {

		if (messagecomment == null) {
			throw new NullPointerException("delete null messagecommentcan not be possible");
		}
		if (messagecomment.getMember() != null && messagecomment.getMember() != this) {
			throw new IllegalArgumentException("messagecomment is already assigned to an other member");
		}
		getMessageComments().remove(messagecomment);
	}

	// convenience methods for cardinality with MessageLikes

	public void addOrUpdateMessageLike(MessageLike messagelike) {

		if (messagelike == null) {
			throw new NullPointerException("add null messagelike can not be possible");
		}
		if (messagelike.getMember() != null && messagelike.getMember() != this) {
			throw new IllegalArgumentException("messagelike is already assigned to an other member");
		}
		getMessageLikes().add(messagelike);
		messagelike.setMember(this);
	}

	public void removeMessageLike (MessageLike messagelike) {

		if (messagelike== null) {
			throw new NullPointerException("delete messagelike horse can not be possible");
		}
		if (messagelike.getMember() != null && messagelike.getMember() != this) {
			throw new IllegalArgumentException("messagelike is already assigned to an other member");
		}
		getMessageLikes().remove(messagelike);
	}
	
	// convenience methods for cardinality with EventComments

	public void addOrUpdateMessageComment(EventComment eventcomment) {

		if (eventcomment== null) {
			throw new NullPointerException("add null eventcomment can not be possible");
		}
		if (eventcomment.getMember() != null && eventcomment.getMember() != this) {
			throw new IllegalArgumentException("eventcomment is already assigned to an other member");
		}
		getEventComments().add(eventcomment);
		eventcomment.setMember(this);
	}

	public void removeEventComment(EventComment eventcomment) {

		if (eventcomment== null) {
			throw new NullPointerException("delete null eventcomment not be possible");
		}
		if (eventcomment.getMember() != null && eventcomment.getMember() != this) {
			throw new IllegalArgumentException("eventcomment is already assigned to an other member");
		}
		getEventComments().remove(eventcomment);
	}
	
	
	// convenience methods for cardinality with EventLikes

		public void addOrUpdateEventLike(EventLike eventlike) {

			if (eventlike== null) {
				throw new NullPointerException("add null eventlike can not be possible");
			}
			if (eventlike.getMember() != null && eventlike.getMember() != this) {
				throw new IllegalArgumentException("eventlikeis already assigned to an other member");
			}
			getEventLikes().add(eventlike);
			eventlike.setMember(this);
		}

		public void removeEventLike (EventLike eventlike) {

			if (eventlike== null) {
				throw new NullPointerException("delete eventlike can not be possible");
			}
			if (eventlike.getMember() != null && eventlike.getMember() != this) {
				throw new IllegalArgumentException("eventlike is already assigned to an other member");
			}
			getEventLikes().remove(eventlike);
		}
}
