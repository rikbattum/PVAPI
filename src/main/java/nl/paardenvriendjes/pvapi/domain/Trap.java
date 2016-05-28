package nl.paardenvriendjes.pvapi.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Trap {

	
	@Id
	private Long id;
	
	public Trap() {}

	public Trap(Long id) {
		super();
		this.id = id;
	} 
	
	
	
}
