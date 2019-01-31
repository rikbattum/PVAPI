package nl.paardenvriendjes.pvapi.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Cacheable("eventcomment")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
@Data
public class EventComment {

	// Properties of EventCommment 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@NotNull
	@Size(min = 10, max = 150)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String comment; 
	@NotNull
	@ManyToOne
	private Member member;
	@Temporal(TemporalType.DATE)
	private Date insertDate;
	@NotNull
	@ManyToOne
	private Event event;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern (regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String piclink;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(max = 150)
	private String commmentLocation;	

	public void setInsertDate() {
		// set-date in backend;
		this.insertDate = new Date();
	}
}
