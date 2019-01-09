/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private JTextField text;
	private JButton graphButton;
	private JButton clear;
	private JLabel name;
	private NameSurferDataBase dataBaseName;
	private NameSurferGraph graph;
	private JButton delete;
	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	//Initializes the GUI interactors for the NameSurfer program.
	//Adds action listeners for the Clear and Graph button.
	//Adds action listen for the JTextField to ensure the enter key is equivalent to pressing the "Graph" button.
	public void init() {
		name = new JLabel("Name");
		text = new JTextField(25);
		graphButton = new JButton("Graph");
		clear = new JButton("Clear");
		graph = new NameSurferGraph();
		delete = new JButton("Delete");
		add(name, NORTH);
		add(text, NORTH);
		add(graphButton, NORTH);
		add(clear, NORTH);
		add(graph);
		add(delete, NORTH);
		addActionListeners();
		text.addActionListener(this);
		dataBaseName = new NameSurferDataBase(NAMES_DATA_FILE);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Clear")) {
			graph.clear();
			graph.update();
		}
		if(e.getSource() == text || e.getActionCommand().equals("Graph")) {
			String entry = text.getText();
			NameSurferEntry ranking = dataBaseName.findEntry(entry);
			//Makes sure the program doesn't crash if the user enters an invalid name.
			if(ranking != null) {
				graph.addEntry(ranking);
				graph.update();
			}
		}
		if(e.getSource() == delete) {
			graph.delete();
			graph.update();
			}
		}
			
	}

