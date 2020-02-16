package org.fife.ui.toaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Changes the look and feel of the application.
 */
class ChangeLookAndFeelAction extends AbstractAction {

	private String lafName;

	ChangeLookAndFeelAction(String name, String lafName) {
		putValue(NAME, name);
		this.lafName = lafName;
	}

	public void actionPerformed(ActionEvent e) {

		try {

			UIManager.setLookAndFeel(lafName);

			for (Window window : JWindow.getWindows()) {
				SwingUtilities.updateComponentTreeUI(window);
				window.pack();
			}

		} catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace(); // Never happens
		}
	}
}
