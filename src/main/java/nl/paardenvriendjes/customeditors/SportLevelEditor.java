package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.SportLevel;

public class SportLevelEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(SportLevelEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        SportLevel sportlevel= SportLevel.valueOf(capitalizedValue);
        setValue(sportlevel);
    } 
	
	  public SportLevel returnSportLevel(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        SportLevel sportlevel = SportLevel.valueOf(capitalizedValue);
	        return sportlevel;
	  }
}


