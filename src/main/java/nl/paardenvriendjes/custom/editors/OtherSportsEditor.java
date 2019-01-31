package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.OtherSport;

@Slf4j
public class OtherSportsEditor extends PropertyEditorSupport {

	
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


