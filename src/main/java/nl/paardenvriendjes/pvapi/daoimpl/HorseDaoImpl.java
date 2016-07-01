package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Repository
@Transactional
public class HorseDaoImpl extends AbstractDaoService<Horse> {

	public HorseDaoImpl() {
		super(Horse.class);
	}
}
