/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	private String name;
	private int[] ranking = new int[NDECADES];
	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears
	 * in the data file.  Each line begins with the name, which is
	 * followed by integers giving the rank of that name for each
	 * decade.
	 */
	public NameSurferEntry(String line) {
		sortInfo(line);
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular
	 * decade.  The decade value is an integer indicating how many
	 * decades have passed since the first year in the database,
	 * which is given by the constant START_DECADE.  If a name does
	 * not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return ranking[decade];
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a
	 * NameSurferEntry.
	 */
	public String toString() {
		String value = "\"" + name + " [";
		for(int i = 0; i < NDECADES; i++) {
			value = value + getRank(i) + " ";
		}
		value = value + "]\"";
		return value;
	}

	//Stores the entered name under a separate string.
	//Stores the entered name's ranking under a separate string.
	private void sortInfo(String line) {
		int nameFinish = line.indexOf(" ");
		name = line.substring(0, nameFinish);
		String rank = line.substring(nameFinish + 1);
		StringTokenizer tokenizer = new StringTokenizer(rank);
		for(int i = 0; tokenizer.hasMoreTokens(); i++) {
			int convertRank = Integer.parseInt(tokenizer.nextToken());
			ranking[i] = convertRank;
		}
	}
}


