package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.domain.Likes;

@Repository
@Transactional

public class LikeDaoImpl extends AbstractDaoService<Likes>{
	
	
	public LikeDaoImpl() {
		super(Likes.class);
	}
	
	
	
}
