package nl.paardenvriendjes.pvapi.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;

import nl.paardenvriendjes.pvapi.data.enums.LikeType;

@Slf4j
public class LikeTypeEditor extends PropertyEditorSupport {

	@Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalizedValue = text.toUpperCase();
        LikeType liketype= LikeType.valueOf(capitalizedValue);
        setValue(liketype);
    } 
	
	  public LikeType returnLiketType(String text) throws IllegalArgumentException {

	        String capitalizedValue = text.toUpperCase();
	        LikeType liketype= LikeType.valueOf(capitalizedValue);
	        return liketype;
	  }
}


