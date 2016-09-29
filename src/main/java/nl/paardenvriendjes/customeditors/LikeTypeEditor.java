package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.LikeType;

public class LikeTypeEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(LikeTypeEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        LikeType liketype= LikeType.valueOf(capitalizedValue);
        setValue(liketype);
    } 
	
	  public LikeType returnLiketType(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        LikeType liketype= LikeType.valueOf(capitalizedValue);
	        return liketype;
	  }
}


