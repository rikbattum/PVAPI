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
	private Date createdon;
	private Date geboortedatum;
	private String email;
	private String overmij;
	@OneToMany
	private List <Horse> horses;
	@OneToOne
	private Interesse interesse;
    private String profileimage;
	private String password;
    private String plaatsnaam;
	@OneToMany (mappedBy = "member")
	@Cascade({CascadeType.ALL})
	private List <Message> messages = new ArrayList<Message>();
    @OneToMany
    private List <Comment> comments;
    @OneToMany
    private List <Like> likes;
    private SportLevel sportLevel;
    @OneToOne
    private Friend friend; 
    
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
	public Date getCreatedon() {
		return createdon;
	}
	public void setCreatedon() {
		// Manually create createdOn backend side; 
		this.createdon = new Date();
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
	public Friend getFriend() {
		return friend;
	}
	public void setFriend(Friend friend) {
		this.friend = friend;
	}
		
		//ToString
	

	@Override
	public String toString() {
		return "Member [id=" + id + ", username=" + username + ", createdon=" + createdon + ", geboortedatum="
				+ geboortedatum + ", email=" + email + "]";
	}
	
	//HashCode
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((achternaam == null) ? 0 : achternaam.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((createdon == null) ? 0 : createdon.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((voornaam == null) ? 0 : voornaam.hashCode());
		return result;
	}
	
	//Equals
	
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
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (createdon == null) {
			if (other.createdon != null)
				return false;
		} else if (!createdon.equals(other.createdon))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
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
		return true;
	} 
}
