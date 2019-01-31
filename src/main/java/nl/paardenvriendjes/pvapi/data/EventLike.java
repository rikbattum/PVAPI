package nl.paardenvriendjes.pvapi.data;

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

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import nl.paardenvriendjes.pvapi.data.enums.LikeType;

@Entity
@Cacheable("eventlike")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
public class EventLike {

	// Properties of EventLike

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@Enumerated(EnumType.STRING)
	private LikeType liketype;
	@ManyToOne
	@NotNull
	private Event event;
	@ManyToOne
	private EventComment EventComment;
	@ManyToOne
	private Member member;
	@Temporal(TemporalType.DATE)
	private Date createdon;

	public void setInsertDate() {
		// set-date in backend;
		this.createdon = new Date();
	}
}
