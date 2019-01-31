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

import nl.paardenvriendjes.pvapi.data.enums.LineType;
import nl.paardenvriendjes.pvapi.data.enums.MessageType;

@Entity
@Cacheable("messagecache")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
public class Message {

	// Properties of Message

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@NotNull
	@Size(min = 10, max = 150)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String message;
	@Enumerated(EnumType.STRING)
	private MessageType messageType;
	@Enumerated(EnumType.STRING)
	private LineType lineType;
	@Temporal(TemporalType.DATE)
	private Date insertDate;
	@ManyToOne
	@NotNull
	private Member member;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String piclink;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String picLinkSecond;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String picLinkThird;
	private Boolean publicPost;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 150)
	private String messageLocation;

	// Collections of Message

	@OneToMany(mappedBy = "message", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.DELETE })
	// @org.hibernate.annotations.Cache(usage =
	// CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<MessageComment> commentlist = new ArrayList<MessageComment>();
	@OneToMany(mappedBy = "message", orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.PERSIST, CascadeType.DELETE })
	// @org.hibernate.annotations.Cache(usage =
	// CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<MessageLike> likelist = new ArrayList<MessageLike>();

	// convenience methods for cardinality with MessageComments

	public void addOrUpdateComment(MessageComment messageComment) {

		if (messageComment == null) {
			throw new NullPointerException("add null comment can not be possible");
		}
		if (messageComment.getMessage() != null && messageComment.getMessage() != this) {
			throw new IllegalArgumentException("comment is already assigned to an other message");
		}
		getCommentlist().add(messageComment);
		messageComment.setMessage(this);
	}

	public void removeMessageComment(MessageComment messageComment) {

		if (messageComment == null) {
			throw new NullPointerException("delete null comment can not be possible");
		}
		if (messageComment.getMessage() != null && messageComment.getMessage() != this) {
			throw new IllegalArgumentException("comment is already assigned to an other message");
		}
		getCommentlist().remove(messageComment);
	}

	// convenience methods for cardinality with MesssageLikes

	public void addOrUpdateMessageLike(MessageLike messageLike) {

		if (messageLike == null) {
			throw new NullPointerException("add null like can not be possible");
		}
		if (messageLike.getMessage() != null && messageLike.getMessage() != this) {
			throw new IllegalArgumentException("like is already assigned to an other message");
		}
		getLikelist().add(messageLike);
		messageLike.setMessage(this);
	}

	public void removeMessageLike(MessageLike messageLike) {

		if (messageLike == null) {
			throw new NullPointerException("delete null like can not be possible");
		}
		if (messageLike.getMessage() != null && messageLike.getMessage() != this) {
			throw new IllegalArgumentException("like is already assigned to an other message");
		}
		getLikelist().remove(messageLike);
	}
}
