package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.pvapi.enumerations.OtherSport;

public class OtherSportsEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(OtherSportsEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        OtherSport othersport= OtherSport.valueOf(capitalizedValue);
        setValue(othersport);
    } 
	
	  public OtherSport returnOtherSport(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        OtherSport othersport= OtherSport.valueOf(capitalizedValue);
	        return othersport;
	  }
}


