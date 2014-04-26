package de.skyrising.ld29;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
	
	
	public static Font getFont(int size) {
		List<String> available = new ArrayList<String>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		if(available.contains("Helvetica Neue"))
			return new Font("Helvetica Neue", Font.PLAIN, size);
		if(available.contains("Helvetica"))
			return new Font("Helvetica", Font.PLAIN, size);
		if(available.contains("Arial"))
			return new Font("Arial", Font.PLAIN, size);
		return new Font(Font.SANS_SERIF, Font.PLAIN, size);
	}
}
