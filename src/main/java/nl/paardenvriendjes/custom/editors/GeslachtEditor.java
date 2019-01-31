package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.Geslacht;

@Slf4j
public class GeslachtEditor extends PropertyEditorSupport {
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        Geslacht geslacht = Geslacht.valueOf(capitalizedValue);
        setValue(geslacht);
    } 
	
	  public Geslacht returnSportgeslacht(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        Geslacht geslacht = Geslacht.valueOf(capitalizedValue);
	        return geslacht;
	  }
}


