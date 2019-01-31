package nl.paardenvriendjes.pvapi.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

import nl.paardenvriendjes.pvapi.data.enums.Geslacht;
import nl.paardenvriendjes.pvapi.data.enums.PaardType;

@Entity
@Cacheable("other")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Data
public class Horse {

	// Properties Horse

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Long id;
	@NotNull
	@Size(min = 2, max = 30)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	private String name;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String horseimage1;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String horseimage2;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Pattern(regexp = "^http://res.cloudinary.com/epona/.*")
	@Size(max = 100)
	private String horseimage3;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 2, max = 40)
	private String afstamming;
	@Temporal(TemporalType.DATE)
	@Past
	private Date geboortedatum;
	private Geslacht geslacht;
	@Max(200)
	private int stokmaat;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 2, max = 150)
	private String karakter;
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Size(min = 2, max = 150)
	private String overmijnpaard;
	private Boolean overleden;
	@Max(5000000)
	private int waarde;
	@Temporal(TemporalType.DATE)
	private Date createdonDate;
	@Temporal(TemporalType.DATE)
	private Date deactivatedDate;
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	@OneToOne(mappedBy = "horse")
	@NotNull
	private Paspoort paspoort;
	@ManyToOne
	@NotNull
	private Member member;
	@ElementCollection
	private Map<String, String> sports = new HashMap<String, String>();
	@Enumerated(EnumType.STRING)
	private PaardType paardType;
	private Boolean active;


	public void setCreatedonDate() {
		// set date from backend
		this.createdonDate = new Date();
	}

	public void setDeactivatedDate() {
		// set date from backend
		this.deactivatedDate = new Date();
	}

	// convenience methods for working with SportsMap

	public void addSportToMap(String sporttype, String sportlevel) {

		if (sporttype == null || sportlevel == null) {
			throw new NullPointerException("add null to sportsmap can not be possible");
		} else {
			sports.put(sporttype, sportlevel);
		}
	}

	public void removeSportFromMap(String sporttype) {

		if (sporttype == null) {
			throw new NullPointerException("delete null from sportsmap can not be possible");
		} else {
			this.sports.remove(sporttype);
		}
	}
}
