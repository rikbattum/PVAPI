package nl.paardenvriendjes.pvapi.services;

import java.util.List;
import nl.paardenvriendjes.pvapi.domain.Trap;

public interface TrapDao {

	
	public List <Trap> listTrappen();

	public void saveTrap(Trap trap);

	public void removeTrap(long id);
	
	
}