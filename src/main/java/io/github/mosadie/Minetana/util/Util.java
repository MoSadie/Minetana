package io.github.mosadie.Minetana.util;

import io.github.mosadie.Minetana.Minetana;

public class Util {

	public static boolean areNamesCloseEnough(String name1, String name2) {
		if (name1 == name2) return true;
		if (simplifyName(name1).equalsIgnoreCase(simplifyName(name2))) return true;
		return false;
	}

	public static String simplifyName(String name) {
		Minetana.LOGGER.info("Name: "+ name + " Result: "+ name.replaceAll("/([t/T][h/H][e/E])|([a/A]\\s)/g", ""));
		return name.replaceAll("/([t/T][h/H][e/E])|([a/A]\\s)/g", "");
	}
}
