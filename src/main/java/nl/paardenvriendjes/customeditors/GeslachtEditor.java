package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.Geslacht;

public class GeslachtEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(GeslachtEditor.class.getName());
	
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


