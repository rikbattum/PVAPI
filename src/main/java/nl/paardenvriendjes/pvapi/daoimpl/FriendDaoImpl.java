package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Friend;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional
public class FriendDaoImpl extends AbstractDaoService<Friend> {

	public FriendDaoImpl() {
		super(Friend.class);
	}

}
