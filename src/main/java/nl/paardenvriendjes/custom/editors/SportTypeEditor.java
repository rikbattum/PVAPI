package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.pvapi.enumerations.SportType;

public class SportTypeEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(SportTypeEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        SportType sporttype = SportType.valueOf(capitalizedValue);
        setValue(sporttype);
    } 
	
	  public SportType returnSportType(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        SportType sporttype= SportType.valueOf(capitalizedValue);
	        return sporttype;
	  }
}


