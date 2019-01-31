package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.SportType;

@Slf4j
public class SportTypeEditor extends PropertyEditorSupport {
	
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


