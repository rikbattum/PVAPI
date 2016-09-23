package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import nl.paardenvriendjes.enumerations.SportLevel;

@Entity
public class Member {

	//Properties
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@NotNull
	private Long id;
	private String voornaam;
	private String achternaam;
	private String username;
	@Temporal(TemporalType.DATE)
	private Date createdonDate;
	private Date deactivatedDate;
	private Date geboortedatum;
	private String email;
	private String overmij;
	@OneToMany (mappedBy = "member")
	@Cascade({CascadeType.ALL})
	private List <Horse> horses;
	@Embedded
	@Basic(fetch=FetchType.EAGER)  //probably not needed
	private Interesse interesse;
    private String profileimage;
	private String password;
    private String plaatsnaam;
	@OneToMany (mappedBy = "member")
	@Cascade({CascadeType.ALL})
	private List <Message> messages = new ArrayList<Message>();
    @OneToMany
    private List <Comment> comments = new ArrayList<Comment>();
    @OneToMany
    private List <Like> likes = new ArrayList<Like>();
    private SportLevel sportLevel;
    private Boolean active;
    @ManyToMany
    private List <Member> vrienden = new ArrayList<Member>();
    @ManyToMany
    private List <Event> events = new ArrayList<Event> ();
    
    //Getters and Setters
   
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
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
	public String getPlaatsnaam() {
		return plaatsnaam;
	}
	public void setPlaatsnaam(String plaatsnaam) {
		this.plaatsnaam = plaatsnaam;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Like> getLikes() {
		return likes;
	}
	public void setLikes(List<Like> likes) {
		this.likes = likes;
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
	public List<Member> getvrienden() {
		return vrienden;
	}
	public void setvrienden(List<Member> vrienden) {
		this.vrienden = vrienden;		
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}	
	public List<Member> getVrienden() {
		return vrienden;
	}
	public void setVrienden(List<Member> vrienden) {
		this.vrienden = vrienden;
	}
	
	//ToString

	

	//Hashcode and Equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achternaam == null) ? 0 : achternaam.hashCode());
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((createdonDate == null) ? 0 : createdonDate.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((geboortedatum == null) ? 0 : geboortedatum.hashCode());
		result = prime * result + ((horses == null) ? 0 : horses.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((interesse == null) ? 0 : interesse.hashCode());
		result = prime * result + ((likes == null) ? 0 : likes.hashCode());
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + ((overmij == null) ? 0 : overmij.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((plaatsnaam == null) ? 0 : plaatsnaam.hashCode());
		result = prime * result + ((profileimage == null) ? 0 : profileimage.hashCode());
		result = prime * result + ((sportLevel == null) ? 0 : sportLevel.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((voornaam == null) ? 0 : voornaam.hashCode());
		result = prime * result + ((vrienden == null) ? 0 : vrienden.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", voornaam=" + voornaam + ", achternaam=" + achternaam + ", username=" + username
				+ ", createdonDate=" + createdonDate + ", deactivatedDate=" + deactivatedDate + ", geboortedatum="
				+ geboortedatum + ", email=" + email + ", overmij=" + overmij + ", horses=" + horses + ", interesse="
				+ interesse + ", profileimage=" + profileimage + ", password=" + password + ", plaatsnaam=" + plaatsnaam
				+ ", messages=" + messages + ", comments=" + comments + ", likes=" + likes + ", sportLevel="
				+ sportLevel + ", active=" + active + ", vrienden=" + vrienden + ", events=" + events + "]";
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
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
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
		if (likes == null) {
			if (other.likes != null)
				return false;
		} else if (!likes.equals(other.likes))
			return false;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
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
		if (plaatsnaam == null) {
			if (other.plaatsnaam != null)
				return false;
		} else if (!plaatsnaam.equals(other.plaatsnaam))
			return false;
		if (profileimage == null) {
			if (other.profileimage != null)
				return false;
		} else if (!profileimage.equals(other.profileimage))
			return false;
		if (sportLevel != other.sportLevel)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
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
	
	public void addOrUpdateMessage (Message message) { 

		if (message == null) { 
			throw new NullPointerException("add null message can not be possible");
		}
		if (message.getMember() != null && message.getMember()!= this) {
			throw new IllegalArgumentException("message is already assigned to an other member");
		}
		getMessages().add(message);
		message.setMember(this);
	}
	
	public void removeMessage (Message message) { 

		if (message == null) { 
			throw new NullPointerException("delete null message can not be possible");
		}
		if (message.getMember() != null  && message.getMember()!= this) {
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
			getvrienden().add(member);
		}
		
		public void removeFriend(Member member) { 

			if (member == null) { 
				throw new NullPointerException("remove null member can not be possible");
			}
			getvrienden().remove(member);
		}	
}
