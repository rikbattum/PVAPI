package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Component
@Transactional
public class HorseDaoImpl extends AbstractDaoService<Horse> {

	public HorseDaoImpl() {
		super(Horse.class);
	}
}
