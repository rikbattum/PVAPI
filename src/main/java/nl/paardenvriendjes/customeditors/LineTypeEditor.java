package nl.paardenvriendjes.customeditors;

import java.beans.PropertyEditorSupport;

import org.apache.log4j.Logger;

import nl.paardenvriendjes.enumerations.LineType;

public class LineTypeEditor extends PropertyEditorSupport {

	static Logger log = Logger.getLogger(LineTypeEditor.class.getName());

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
