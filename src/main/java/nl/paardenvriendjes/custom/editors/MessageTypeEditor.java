package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;


import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.MessageType;

@Slf4j
public class MessageTypeEditor extends PropertyEditorSupport {

	
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


