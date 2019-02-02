package nl.paardenvriendjes.pvapi.custom.editors;

import java.beans.PropertyEditorSupport;

import lombok.extern.slf4j.Slf4j;

import nl.paardenvriendjes.pvapi.data.enums.LineType;

@Slf4j
public class LineTypeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		String capitalizedValue = text.toUpperCase();
		LineType linetype = LineType.valueOf(capitalizedValue);
		setValue(linetype);
	}

	public LineType returnLineType(String text) throws IllegalArgumentException {

		String capitalizedValue = text.toUpperCase();
		LineType linetype = LineType.valueOf(capitalizedValue);
		return linetype;
	}
}
