package de.skyrising.ld29;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
	
	private static Font f;
	public static Font getFont(int size) {
		if(f != null)
			return f.deriveFont((float)size);
		List<String> available = new ArrayList<String>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		if(available.contains("Helvetica Neue"))
			return f = new Font("Helvetica Neue", Font.PLAIN, size);
		if(available.contains("Helvetica"))
			return f = new Font("Helvetica", Font.PLAIN, size);
		if(available.contains("Arial"))
			return f = new Font("Arial", Font.PLAIN, size);
		return f = new Font(Font.SANS_SERIF, Font.PLAIN, size);
	}
}
