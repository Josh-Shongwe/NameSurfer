/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {

	private ArrayList <NameSurferEntry> enteredNames;


	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		enteredNames = new ArrayList<NameSurferEntry>();
	}


	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		enteredNames.clear();
	}

	public void delete() {
		if(enteredNames.size() > 0) {
			enteredNames.remove(enteredNames.size() - 1);
		}
	}


	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		enteredNames.add(entry);

	}


	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */

	public void update() {
		removeAll();
		background();
		if(enteredNames.size() > 0) {
			for(int i = 0; i < enteredNames.size(); i++) {
				NameSurferEntry namePlot = enteredNames.get(i);
				displayRank(namePlot, i);
			}
		}
	}
	//Creates the background of the graph.
	private void background() {
		decadeMarker();
		borderLines();
		decadeDivider();
	}
	//Creates the decade numbers and places them 4 units after the decadeDivider.
	private void decadeMarker() {
		for(int i = 0; i < NDECADES; i++) {
			int decades = START_DECADE;
			decades = decades + 10*i;
			String year = Integer.toString((decades));
			double x = 4 + (getWidth()/NDECADES)*i;
			double y = getHeight() - GRAPH_MARGIN_SIZE/4;
			GLabel yearNum = new GLabel(year, x, y);
			add(yearNum);
		}
	}
	//Draws the horizontal lines across the top and the bottom of the JFrame.
	private void borderLines() {
		double leftX = 0;
		double rightX = getWidth();
		double bottom = getHeight() - GRAPH_MARGIN_SIZE;
		double top = GRAPH_MARGIN_SIZE;
		GLine botLine = new GLine(leftX, bottom, rightX, bottom);
		GLine topLine = new GLine(leftX, top, rightX, top);
		add(botLine);
		add(topLine);

	}
	//Draws the vertical lines displaying the area that each decade allocates.
	private void decadeDivider() {
		double topY = 0;
		double botY = getHeight();
		for(int i = 0; i < NDECADES; i++) {
			double x = (getWidth()/NDECADES) * i;
			GLine vertLine = new GLine(x, topY, x, botY);
			add(vertLine);
		}
	}

	//Loops through one less than the allocated decades to determine the rank of each name at the given decade.
	private void displayRank(NameSurferEntry entry, int entryNumber) {
		for(int i = 0; i < NDECADES - 1; i++) {
			//Creates a starting position x (x1) and a finishing position x (x2) for each decade.
			double rank1 = entry.getRank(i);
			double rank2 = entry.getRank(i+1);
			double x1 = (getWidth()/NDECADES) * i;
			double y1;
			double x2 = (getWidth()/NDECADES) * (i+1);
			double y2;
			/* Conditions the rank for said name at each decade creating a starting y-coordinate (y1) and a 
			 * finishing y-coordinate (y2).
			 */
			if(rank1 == 0 && rank2 == 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}else if(rank1 != 0 && rank2 != 0) {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - 2*GRAPH_MARGIN_SIZE) * (rank1/MAX_RANK);
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - 2*GRAPH_MARGIN_SIZE) * (rank2/MAX_RANK);
			}else if(rank1 == 0 && rank2 != 0) {
				y1 = getHeight() - GRAPH_MARGIN_SIZE;
				y2 = GRAPH_MARGIN_SIZE + (getHeight() - 2*GRAPH_MARGIN_SIZE) * (rank2/MAX_RANK);
			}else  {
				y1 = GRAPH_MARGIN_SIZE + (getHeight() - 2*GRAPH_MARGIN_SIZE) * (rank1/MAX_RANK);
				y2 = getHeight() - GRAPH_MARGIN_SIZE;
			}
			GLine slope = new GLine(x1, y1, x2, y2);
			//Determines the color of the GLine slope based on the entryNumber.
			if(entryNumber % 4 == 0) {
				slope.setColor(Color.BLACK);
			}
			else if(entryNumber % 4 == 1) {
				slope.setColor(Color.RED);
			}
			else if(entryNumber % 4 == 2) {
				slope.setColor(Color.BLUE);
			}
			else if(entryNumber % 4 == 3) {
				slope.setColor(Color.MAGENTA);
			}
			add(slope);
		}
		rankName(entry, entryNumber);
		

	}


	//Takes in a NameSurferEntry and an entryNumber.
	//Determines the rank of the entry, converts the integer to a string and adds it to the nameString.
	//The nameRank takes in the nameString and places the GLabel at 3 units above the line.
	private void rankName(NameSurferEntry entry, int entryNumber) {
		for(int i = 0; i < NDECADES; i++) {
			String name = entry.getName();
			int rank = entry.getRank(i);
			String ranker = Integer.toString(rank);
			String nameString = name + " " + ranker;
			double x = (getWidth() / NDECADES) * i + 3;
			double y = 0;
			if(rank != 0) {
				//When there's parenthesis around rank/MAX_RANK it does not work, but all the values are integers.
				//Can we please discuss this.
				y = GRAPH_MARGIN_SIZE + (getHeight() - 2*GRAPH_MARGIN_SIZE) * rank/MAX_RANK - 3;
			} else {
				nameString = name + " *";
				y = getHeight() - GRAPH_MARGIN_SIZE - 3;
			}
			GLabel nameRank = new GLabel(nameString, x, y);
			if(entryNumber % 4  == 0) {
				nameRank.setColor(Color.BLACK);
			}
			else if(entryNumber % 4 == 1) {
				nameRank.setColor(Color.RED);
			}
			else if(entryNumber % 4 == 2) {
				nameRank.setColor(Color.BLUE);
			}
			else if(entryNumber % 4 == 3) {
				nameRank.setColor(Color.MAGENTA);
			}
			add(nameRank);
		}

	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
