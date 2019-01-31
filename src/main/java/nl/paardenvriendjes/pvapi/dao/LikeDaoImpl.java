package nl.paardenvriendjes.pvapi.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.MessageLike;

@Repository
@Transactional

public class LikeDaoImpl extends AbstractDaoService<MessageLike>{
	
	
	public LikeDaoImpl() {
		super(MessageLike.class);
	}
	
	
	
}
