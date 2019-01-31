package nl.paardenvriendjes.custom.editors;

import java.beans.PropertyEditorSupport;


import lombok.extern.slf4j.Slf4j;
import nl.paardenvriendjes.pvapi.data.enums.Place;


@Slf4j
public class LocationTypeEditor extends PropertyEditorSupport {

	
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

