package nl.paardenvriendjes.pvapi.daoimpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Interesse;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;


@Repository
@Transactional

public class InteresseDaoImpl extends AbstractDaoService<Interesse>{

	public InteresseDaoImpl() {
		super(Interesse.class);
	}	
}
