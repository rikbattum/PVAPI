package nl.paardenvriendjes.pvapi.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;

import nl.paardenvriendjes.pvapi.data.enums.Vervoer;

@Slf4j
public class VervoerEditor extends PropertyEditorSupport {
	
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


