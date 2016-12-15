package nl.paardenvriendjes.pvapi.genericservicelayer.messagecreate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.MemberDaoImpl;
import nl.paardenvriendjes.pvapi.daoimpl.MessageDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Event;
import nl.paardenvriendjes.pvapi.domain.Horse;
import nl.paardenvriendjes.pvapi.domain.Member;
import nl.paardenvriendjes.pvapi.domain.Message;
import nl.paardenvriendjes.pvapi.enumerations.LineType;
import nl.paardenvriendjes.pvapi.enumerations.MessageType;

@Repository
@Transactional
public class Genericmessageservice {

		@Autowired
		private MessageDaoImpl messageservice;
		@Autowired
		private MemberDaoImpl memberservice;
		
		
// MEMBER REGISTRATION
		
			// generic update for becoming a member
			public void newMemberHappyMessage (Member member) { 
			
			// TODO
			String somefancyLinkSplitupLogoProfilePic = "";	
				
			Message message = new Message(); 
			message.setMessage(member.getVoornaam() + " " + member.getAchternaam() +  "uit " + member.getPlace() + " is lid geworden "   );
			message.setMessageType(MessageType.NEWPROFILE);
			message.setLineType(LineType.FRIENDS);
			message.setPiclink(somefancyLinkSplitupLogoProfilePic); 
			message.setMember(member);
			message.setPublicPost(false);
			memberservice.edit (member);
			}
		
			// generic update for signing out?
			// nope
		
// FOLLOWER REGISTRATION
		
			// Generic update for getting a follower 
			// SOLVE THIS WITH A SPECIFIC FRONT END COUNTER OF FOLLOWERS AND FOLLOWING


// HORSE REGISTRATION
			
			// ADDING A HORSE
		
			public void newHappyHorseMessage (Member member, Horse horse) { 
			String somefancyLinkSplitupLogoProfilePic = "";	 
				
			Message message = new Message(); 
			message.setMessage(member.getVoornaam() + " " + member.getAchternaam() +  "heeft  " + horse.getName() + " als paard toegevoegd"   );
			message.setMessageType(MessageType.NEWHORSE);
			message.setLineType(LineType.FRIENDS);
			message.setPiclink(somefancyLinkSplitupLogoProfilePic); 
			message.setMember(member);
			message.setPublicPost(false);
			memberservice.edit (member); 		
			}
		
		
		
		// ADDING A EVENT
		
			// generic update for becoming a member
			public void newHappyEventMessage (Member member, Event event) { 
			String somefancyLinkSplitupLogoProfilePic = "";	 
			
			Message message = new Message(); 
			message.setMessage(member.getVoornaam() + " " + member.getAchternaam() +  "heeft " + event.getEventName() + " als event toegevoegd ");
			message.setMessageType(MessageType.NEWEVENT);
			message.setLineType(LineType.FRIENDS);
			message.setPiclink(somefancyLinkSplitupLogoProfilePic); 
			message.setMember(member);
			message.setPublicPost(false);
			memberservice.edit (member); 		
				
			memberservice.edit(member); 	
				}
				
		
			

			//ADDING A RIT
			}
		

		
