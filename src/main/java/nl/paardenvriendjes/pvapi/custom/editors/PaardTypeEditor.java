package nl.paardenvriendjes.pvapi.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;

import nl.paardenvriendjes.pvapi.data.enums.PaardType;

@Slf4j
public class PaardTypeEditor extends PropertyEditorSupport{

		
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
