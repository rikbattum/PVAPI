package nl.paardenvriendjes.pvapi.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Friend {

	// Properties
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne  (mappedBy = "friend")
	private Member member;
	@OneToMany
	private List <Member> vriendenlijst;
	
	//Getters and Setters 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public List<Member> getVriendenlijst() {
		return vriendenlijst;
	}
	public void setVriendenlijst(List<Member> vriendenlijst) {
		this.vriendenlijst = vriendenlijst;
	}
	
	
	//Hashcode
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((vriendenlijst == null) ? 0 : vriendenlijst.hashCode());
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
		Friend other = (Friend) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (vriendenlijst == null) {
			if (other.vriendenlijst != null)
				return false;
		} else if (!vriendenlijst.equals(other.vriendenlijst))
			return false;
		return true;
	}

	
	
	
	
	
	
	
	
}
