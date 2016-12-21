package nl.paardenvriendjes.pvapi.domain;

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

import nl.paardenvriendjes.pvapi.enumerations.Geslacht;
import nl.paardenvriendjes.pvapi.enumerations.OtherSport;
import nl.paardenvriendjes.pvapi.enumerations.Place;
import nl.paardenvriendjes.pvapi.enumerations.SportLevel;
import nl.paardenvriendjes.pvapi.enumerations.Vervoer;

@Entity
@Cacheable("membercache")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member {

	// Properties of Member

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
	@Size(max = 80)
	private String profileimage;
	@Transient
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 80)
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
	@Cascade({ CascadeType.ALL })
	private List<Horse> horses = new ArrayList<Horse>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	private List<Message> messages = new ArrayList<Message>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	private List<MessageComment> messageComments = new ArrayList<MessageComment>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	private List<EventComment> eventComments = new ArrayList<EventComment>();
	@OneToMany(mappedBy = "member")
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	private List<MessageLike> messageLikes = new ArrayList<MessageLike>();
	@OneToMany(mappedBy = "member")
	private List<EventLike> eventLikes = new ArrayList<EventLike>();
	@ManyToMany
	@Cascade({ CascadeType.ALL })
	@JoinTable(name = "member_vrienden", joinColumns = {
			@JoinColumn(name = "membervrienden_id") }, inverseJoinColumns = { @JoinColumn(name = "vrienden_id") })
	private List<Member> vrienden = new ArrayList<Member>();
	@ManyToMany
	@Cascade({ CascadeType.ALL })
	@JoinTable(name = "member_blokkades", joinColumns = {
			@JoinColumn(name = "memberblocks_id") }, inverseJoinColumns = { @JoinColumn(name = "blokkades_id") })
	private List<Member> blokkades = new ArrayList<Member>();

	// Getters and Setters

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreatedonDate() {
		return createdonDate;
	}

	public void setCreatedonDate() {
		// set-date in backend;
		this.createdonDate = new Date();
	}

	public Date getDeactivatedDate() {
		return deactivatedDate;
	}

	public void setDeactivatedDate() {
		// set-date in backend;
		this.deactivatedDate = new Date();
	}

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOvermij() {
		return overmij;
	}

	public void setOvermij(String overmij) {
		this.overmij = overmij;
	}

	public List<Horse> getHorses() {
		return horses;
	}

	public void setHorses(List<Horse> horses) {
		this.horses = horses;
	}

	public Interesse getInteresse() {
		return interesse;
	}

	public void setInteresse(Interesse interesse) {
		this.interesse = interesse;
	}

	public String getProfileimage() {
		return profileimage;
	}

	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public SportLevel getSportLevel() {
		return sportLevel;
	}

	public void setSportLevel(SportLevel sportLevel) {
		this.sportLevel = sportLevel;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<Member> getVrienden() {
		return vrienden;
	}

	public void setVrienden(List<Member> vrienden) {
		this.vrienden = vrienden;
	}

	public List<Member> getBlokkades() {
		return blokkades;
	}

	public void setBlokkades(List<Member> blokkades) {
		this.blokkades = blokkades;
	}

	public Map<String, String> getSports() {
		return sports;
	}

	public void setSports(Map<String, String> sports) {
		this.sports = sports;
	}

	public Vervoer getVervoer() {
		return vervoer;
	}

	public void setVervoer(Vervoer vervoer) {
		this.vervoer = vervoer;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}

	public String getAuth0user_id() {
		return auth0user_id;
	}

	public void setAuth0user_id(String auth0user_id) {
		this.auth0user_id = auth0user_id;
	}

	public Set<OtherSport> getOthersports() {
		return othersports;
	}

	public void setOthersports(Set<OtherSport> othersports) {
		this.othersports = othersports;
	}

	public List<MessageComment> getMessageComments() {
		return messageComments;
	}

	public void setMessageComments(List<MessageComment> messageComments) {
		this.messageComments = messageComments;
	}

	public List<EventComment> getEventComments() {
		return eventComments;
	}

	public void setEventComments(List<EventComment> eventComments) {
		this.eventComments = eventComments;
	}

	public List<MessageLike> getMessageLikes() {
		return messageLikes;
	}

	public void setMessageLikes(List<MessageLike> messageLikes) {
		this.messageLikes = messageLikes;
	}

	public List<EventLike> getEventLikes() {
		return eventLikes;
	}

	public void setEventLikes(List<EventLike> eventLikes) {
		this.eventLikes = eventLikes;
	}

	// ToString

	@Override
	public String toString() {
		return "Member [id=" + id + ", email=" + email + ", voornaam=" + voornaam + ", achternaam=" + achternaam
				+ ", username=" + username + ", createdonDate=" + createdonDate + ", deactivatedDate=" + deactivatedDate
				+ ", geboortedatum=" + geboortedatum + ", overmij=" + overmij + ", interesse=" + interesse
				+ ", profileimage=" + profileimage + ", password=" + password + ", place=" + place + ", sportLevel="
				+ sportLevel + ", active=" + active + ", vervoer=" + vervoer + ", geslacht=" + geslacht
				+ ", auth0user_id=" + auth0user_id + ", sports=" + sports + ", othersports=" + othersports + ", horses="
				+ horses + ", messages=" + messages + ", messageComments=" + messageComments + ", eventComments="
				+ eventComments + ", messageLikes=" + messageLikes + ", eventLikes=" + eventLikes + ", vrienden="
				+ vrienden + ", blokkades=" + blokkades + "]";
	}

	// Hashcode and Equals

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achternaam == null) ? 0 : achternaam.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((auth0user_id == null) ? 0 : auth0user_id.hashCode());
		result = prime * result + ((blokkades == null) ? 0 : blokkades.hashCode());
		result = prime * result + ((createdonDate == null) ? 0 : createdonDate.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((eventComments == null) ? 0 : eventComments.hashCode());
		result = prime * result + ((eventLikes == null) ? 0 : eventLikes.hashCode());
		result = prime * result + ((geboortedatum == null) ? 0 : geboortedatum.hashCode());
		result = prime * result + ((geslacht == null) ? 0 : geslacht.hashCode());
		result = prime * result + ((horses == null) ? 0 : horses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((interesse == null) ? 0 : interesse.hashCode());
		result = prime * result + ((messageComments == null) ? 0 : messageComments.hashCode());
		result = prime * result + ((messageLikes == null) ? 0 : messageLikes.hashCode());
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((othersports == null) ? 0 : othersports.hashCode());
		result = prime * result + ((overmij == null) ? 0 : overmij.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
		result = prime * result + ((profileimage == null) ? 0 : profileimage.hashCode());
		result = prime * result + ((sportLevel == null) ? 0 : sportLevel.hashCode());
		result = prime * result + ((sports == null) ? 0 : sports.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((vervoer == null) ? 0 : vervoer.hashCode());
		result = prime * result + ((voornaam == null) ? 0 : voornaam.hashCode());
		result = prime * result + ((vrienden == null) ? 0 : vrienden.hashCode());
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
		Member other = (Member) obj;
		if (achternaam == null) {
			if (other.achternaam != null)
				return false;
		} else if (!achternaam.equals(other.achternaam))
			return false;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (auth0user_id == null) {
			if (other.auth0user_id != null)
				return false;
		} else if (!auth0user_id.equals(other.auth0user_id))
			return false;
		if (blokkades == null) {
			if (other.blokkades != null)
				return false;
		} else if (!blokkades.equals(other.blokkades))
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (eventComments == null) {
			if (other.eventComments != null)
				return false;
		} else if (!eventComments.equals(other.eventComments))
			return false;
		if (eventLikes == null) {
			if (other.eventLikes != null)
				return false;
		} else if (!eventLikes.equals(other.eventLikes))
			return false;
		if (geboortedatum == null) {
			if (other.geboortedatum != null)
				return false;
		} else if (!geboortedatum.equals(other.geboortedatum))
			return false;
		if (geslacht != other.geslacht)
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
		if (interesse == null) {
			if (other.interesse != null)
				return false;
		} else if (!interesse.equals(other.interesse))
			return false;
		if (messageComments == null) {
			if (other.messageComments != null)
				return false;
		} else if (!messageComments.equals(other.messageComments))
			return false;
		if (messageLikes == null) {
			if (other.messageLikes != null)
				return false;
		} else if (!messageLikes.equals(other.messageLikes))
			return false;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (othersports == null) {
			if (other.othersports != null)
				return false;
		} else if (!othersports.equals(other.othersports))
			return false;
		if (overmij == null) {
			if (other.overmij != null)
				return false;
		} else if (!overmij.equals(other.overmij))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (place != other.place)
			return false;
		if (profileimage == null) {
			if (other.profileimage != null)
				return false;
		} else if (!profileimage.equals(other.profileimage))
			return false;
		if (sportLevel != other.sportLevel)
			return false;
		if (sports == null) {
			if (other.sports != null)
				return false;
		} else if (!sports.equals(other.sports))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (vervoer != other.vervoer)
			return false;
		if (voornaam == null) {
			if (other.voornaam != null)
				return false;
		} else if (!voornaam.equals(other.voornaam))
			return false;
		if (vrienden == null) {
			if (other.vrienden != null)
				return false;
		} else if (!vrienden.equals(other.vrienden))
			return false;
		return true;
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
