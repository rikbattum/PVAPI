package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.MessageType;

public class MessageTypeEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(MessageTypeEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        MessageType messagetype = MessageType.valueOf(capitalizedValue);
        setValue(messagetype);
    } 
	
	  public MessageType returnMessageType(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        MessageType messagetype = MessageType.valueOf(capitalizedValue);
	        return messagetype;
	  }
}


