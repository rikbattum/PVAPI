package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.Vervoer;

public class VervoerEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(VervoerEditor.class.getName());
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        Vervoer vervoer= Vervoer.valueOf(capitalizedValue);
        setValue(vervoer);
    } 
	
	  public Vervoer returnVervoer(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        Vervoer vervoer= Vervoer.valueOf(capitalizedValue);
	        return vervoer;
	  }
}


