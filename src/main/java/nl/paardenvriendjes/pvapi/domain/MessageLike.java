package nl.paardenvriendjes.pvapi.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import nl.paardenvriendjes.pvapi.enumerations.LikeType;

@Entity
@Cacheable("messagelike")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MessageLike {

	// Properties of Message
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@Enumerated(EnumType.STRING)
	private LikeType liketype;
	@ManyToOne
	private Message message;
	@ManyToOne
	private MessageComment messageComment;
	@ManyToOne
	private Member member;
	@Temporal(TemporalType.DATE)
	private Date createdon;

	// Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LikeType getLiketype() {
		return liketype;
	}

	public void setLiketype(LikeType liketype) {
		this.liketype = liketype;
	}

	public Date getInsertDate() {
		return createdon;
	}

	public void setInsertDate() {
		// set-date in backend;
		this.createdon = new Date();
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public MessageComment getMessageComment() {
		return messageComment;
	}

	public void setMessageComment(MessageComment messageComment) {
		this.messageComment = messageComment;
	}

	// ToString
	
	@Override
	public String toString() {
		return "MessageLike [id=" + id + ", liketype=" + liketype + ", message=" + message + ", messageComment="
				+ messageComment + ", member=" + member + ", createdon=" + createdon + "]";
	}

	// Hashcode and Equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdon == null) ? 0 : createdon.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((liketype == null) ? 0 : liketype.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageComment == null) ? 0 : messageComment.hashCode());
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
		MessageLike other = (MessageLike) obj;
		if (createdon == null) {
			if (other.createdon != null)
				return false;
		} else if (!createdon.equals(other.createdon))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liketype != other.liketype)
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (messageComment == null) {
			if (other.messageComment != null)
				return false;
		} else if (!messageComment.equals(other.messageComment))
			return false;
		return true;
	}	
}
