package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Like;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Component
@Transactional

public class LikeDaoImpl extends AbstractDaoService<Like>{
	
	
	public LikeDaoImpl() {
		super(Like.class);
	}
	
	
	
}
