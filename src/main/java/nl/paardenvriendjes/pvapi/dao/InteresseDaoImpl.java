package nl.paardenvriendjes.pvapi.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.dao.abstractdao.AbstractDaoService;
import nl.paardenvriendjes.pvapi.data.Interesse;


@Repository
@Transactional

public class InteresseDaoImpl extends AbstractDaoService<Interesse>{

	public InteresseDaoImpl() {
		super(Interesse.class);
	}	
}
