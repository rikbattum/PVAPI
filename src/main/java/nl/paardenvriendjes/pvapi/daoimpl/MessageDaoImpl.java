package nl.paardenvriendjes.pvapi.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.service.AbstractDaoService;

@Component
@Transactional

public class MessageDaoImpl extends AbstractDaoService<Message> {

	public MessageDaoImpl() {
		super(Message.class);
	}	
}
