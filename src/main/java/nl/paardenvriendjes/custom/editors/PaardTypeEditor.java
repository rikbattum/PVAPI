package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.pvapi.enumerations.PaardType;

public class PaardTypeEditor extends PropertyEditorSupport{

	static Logger log = Logger.getLogger(SportLevelEditor.class.getName()); 
		
		@Override
	    public void setAsText(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        PaardType paardtype = PaardType.valueOf(capitalizedValue);
	        setValue(paardtype);
	    } 
		
		
		  public PaardType returnAsPaardType(String text) throws IllegalArgumentException {

		        String capitalizedValue = text.toUpperCase();
		        PaardType paardtype = PaardType.valueOf(capitalizedValue);
		        return paardtype;
		  }
}
