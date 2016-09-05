package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Event {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String eventName;
	private Date eventDate;
	private Date createdOnDate;
	private Date deactivatedDate;
	private String Message;
	private Horse horse;
	private String messageScore;
	private int score; 
	private int ranking;
	private List <Member> vriendenTagList = new ArrayList <Member>();
	@ManyToOne
	private Paspoort paspoort;
	
	
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
	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Horse getHorse() {
		return horse;
	}
	public void setHorse(Horse horse) {
		this.horse = horse;
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
	public List<Member> getVriendenTagList() {
		return vriendenTagList;
	}
	public void setVriendenTagList(List<Member> vriendenTagList) {
		this.vriendenTagList = vriendenTagList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Message == null) ? 0 : Message.hashCode());
		result = prime * result + ((createdOnDate == null) ? 0 : createdOnDate.hashCode());
		result = prime * result + ((deactivatedDate == null) ? 0 : deactivatedDate.hashCode());
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((horse == null) ? 0 : horse.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((messageScore == null) ? 0 : messageScore.hashCode());
		result = prime * result + ((paspoort == null) ? 0 : paspoort.hashCode());
		result = prime * result + ranking;
		result = prime * result + score;
		result = prime * result + ((vriendenTagList == null) ? 0 : vriendenTagList.hashCode());
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
		if (ranking != other.ranking)
			return false;
		if (score != other.score)
			return false;
		if (vriendenTagList == null) {
			if (other.vriendenTagList != null)
				return false;
		} else if (!vriendenTagList.equals(other.vriendenTagList))
			return false;
		return true;
	}
}
