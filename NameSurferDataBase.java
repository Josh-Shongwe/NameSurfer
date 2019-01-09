/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import acm.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	private Map <String, NameSurferEntry> dataBaseName = new HashMap <String, NameSurferEntry>();
	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		dataNameSurfer(filename);
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		char ch = name.charAt(0);
		String properCase = "";
		if(Character.isLowerCase(ch) == true) {
			ch = Character.toUpperCase(ch);
		}
		properCase = name.substring(1);
		properCase = properCase.toLowerCase();
		name = ch + properCase;
		//.equals doesn't work?
		if(dataBaseName.containsKey(name)) {
			return dataBaseName.get(name);
		}
		else {
			return null;
		}
	}

	private void dataNameSurfer(String filename) {
		try {
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = rd.readLine();
				if(line == null) break;
				NameSurferEntry surferEntry = new NameSurferEntry(line);
				dataBaseName.put(surferEntry.getName(), surferEntry);
			}
			rd.close();
		} catch(IOException ex) {
			throw new ErrorException(ex);
		}
	}
}

