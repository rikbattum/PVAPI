package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.Place;



public class LocationTypeEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(LocationTypeEditor.class.getName());
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
	
		String capitalizedValue = text.toUpperCase();

		for (Place x : Place.values()) {
			if (capitalizedValue == x.toString()) {
				setValue(x);
				break;
			}
		}
	}

	public Place returnAsPlace(String text) throws IllegalArgumentException {

		String capitalizedValue = text.toUpperCase();
		log.debug("tempie2");
		for (Place x : Place.values()) {
			if (capitalizedValue == x.toString()) {
				log.debug("tempie" + x);
				return x;
			}
		}
		Place x = Place.GARDEREN;
		return x;
	}
}

