package nl.paardenvriendjes.pvapi.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.SportLevel;

@Slf4j
public class SportLevelEditor extends PropertyEditorSupport {

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


