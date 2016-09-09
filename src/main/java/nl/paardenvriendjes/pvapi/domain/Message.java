package nl.paardenvriendjes.pvapi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import nl.paardenvriendjes.enumerations.LineType;
import nl.paardenvriendjes.enumerations.MessageType;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String message;
	private MessageType messageType;
	private LineType lineType;
	@Temporal(TemporalType.DATE)
	private Date insertDate;
	
	@ManyToOne
	private Member member;
	private String piclink;
	private String picLinkSecond;
	private String picLinkThird;
	@OneToMany (mappedBy="message", orphanRemoval=true)
	@Cascade({CascadeType.ALL})
	private List<Comment> commentlist = new ArrayList<Comment>();
	@OneToMany(mappedBy = "message")
	@Cascade({CascadeType.ALL})
	private List<Like> likelist;
	private Boolean publicPost; 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public LineType getLineType() {
		return lineType;
	}

	public void setLineType(LineType lineType) {
		this.lineType = lineType;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate() {
		// set-date in backend;
		this.insertDate = new Date();
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPiclink() {
		return piclink;
	}

	public void setPiclink(String piclink) {
		this.piclink = piclink;
	}

	public String getPicLinkSecond() {
		return picLinkSecond;
	}

	public void setPicLinkSecond(String picLinkSecond) {
		this.picLinkSecond = picLinkSecond;
	}

	public String getPicLinkThird() {
		return picLinkThird;
	}

	public void setPicLinkThird(String picLinkThird) {
		this.picLinkThird = picLinkThird;
	}

	public List<Comment> getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(List<Comment> commentlist) {
		this.commentlist = commentlist;
	}

	public List<Like> getLikelist() {
		return likelist;
	}

	public void setLikelist(List<Like> likelist) {
		this.likelist = likelist;
	}

	
	public Boolean getPublicPost() {
		return publicPost;
	}

	public void setPublicPost(Boolean publicPost) {
		this.publicPost = publicPost;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", type=" + messageType + ", insertDate=" + insertDate + "]";
	}

	// Hashcode and Equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentlist == null) ? 0 : commentlist.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insertDate == null) ? 0 : insertDate.hashCode());
		result = prime * result + ((likelist == null) ? 0 : likelist.hashCode());
		result = prime * result + ((lineType == null) ? 0 : lineType.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
		result = prime * result + ((picLinkSecond == null) ? 0 : picLinkSecond.hashCode());
		result = prime * result + ((picLinkThird == null) ? 0 : picLinkThird.hashCode());
		result = prime * result + ((piclink == null) ? 0 : piclink.hashCode());
		result = prime * result + ((publicPost == null) ? 0 : publicPost.hashCode());
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
		Message other = (Message) obj;
		if (commentlist == null) {
			if (other.commentlist != null)
				return false;
		} else if (!commentlist.equals(other.commentlist))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insertDate == null) {
			if (other.insertDate != null)
				return false;
		} else if (!insertDate.equals(other.insertDate))
			return false;
		if (likelist == null) {
			if (other.likelist != null)
				return false;
		} else if (!likelist.equals(other.likelist))
			return false;
		if (lineType != other.lineType)
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
		if (messageType != other.messageType)
			return false;
		if (picLinkSecond == null) {
			if (other.picLinkSecond != null)
				return false;
		} else if (!picLinkSecond.equals(other.picLinkSecond))
			return false;
		if (picLinkThird == null) {
			if (other.picLinkThird != null)
				return false;
		} else if (!picLinkThird.equals(other.picLinkThird))
			return false;
		if (piclink == null) {
			if (other.piclink != null)
				return false;
		} else if (!piclink.equals(other.piclink))
			return false;
		if (publicPost == null) {
			if (other.publicPost != null)
				return false;
		} else if (!publicPost.equals(other.publicPost))
			return false;
		return true;
	}
	
	// convenience methods for cardinality with Comments
	
	public void addOrUpdateComment (Comment comment) { 

		if (comment== null) { 
			throw new NullPointerException("add null comment can not be possible");
		}
		if (comment.getMessage() != null && comment.getMessage()!= this) {
			throw new IllegalArgumentException("comment is already assigned to an other message");
		}
		getCommentlist().add(comment);
		comment.setMessage(this);
	}

	public void removeComment (Comment comment) { 

		if (comment == null) { 
			throw new NullPointerException("delete null comment can not be possible");
		}
		if (comment.getMessage() != null  && comment.getMessage()!= this) {
			throw new IllegalArgumentException("comment is already assigned to an other message");
		}
		getCommentlist().remove(comment);
	}
	
	// convenience methods for cardinality with Likes
	
		public void addOrUpdateLike (Like like) { 

			if (like == null) { 
				throw new NullPointerException("add null like can not be possible");
			}
			if (like.getMessage() != null && like.getMessage()!= this) {
				throw new IllegalArgumentException("like is already assigned to an other message");
			}
			getLikelist().add(like);
			like.setMessage(this);
		}
		
		public void removeLike (Like like) { 

			if (like == null) { 
				throw new NullPointerException("delete null like can not be possible");
			}
			if (like.getMessage() != null  && like.getMessage()!= this) {
				throw new IllegalArgumentException("like is already assigned to an other message");
			}
			getLikelist().remove(like);
		}	
}
