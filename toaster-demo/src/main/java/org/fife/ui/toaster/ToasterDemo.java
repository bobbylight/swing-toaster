package org.fife.ui.toaster;

import javax.swing.*;
import java.awt.*;

/**
 * A demo of the toaster component.
 */
public final class ToasterDemo {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ToasterDemo() {
		// Do nothing (comment for Sonar)
	}

	private static JMenuBar createJMenuBar() {

		JMenuBar mb = new JMenuBar();
		mb.add(createLookAndFeelMenu());

		return mb;
	}

	private static JMenu createLookAndFeelMenu() {

		JMenu lafMenu = new JMenu("Look and Feel");

		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Native", UIManager.getSystemLookAndFeelClassName())));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Metal",
			UIManager.getCrossPlatformLookAndFeelClassName())));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel")));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Ocean", "javax.swing.plaf.nimbus.NimbusLookAndFeel")));
		lafMenu.addSeparator();
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Flat Light", "com.formdev.flatlaf.FlatLightLaf")));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Flat Dark", "com.formdev.flatlaf.FlatDarkLaf")));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Flat IntelliJ", "com.formdev.flatlaf.FlatIntelliJLaf")));
		lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Flat Darcula", "com.formdev.flatlaf.FlatDarculaLaf")));
		lafMenu.addSeparator();

		String[] substanceLafNames = { "Business", "Business Blue Steel", "Business Black Steel", "Creme", "Graphite",
			"GraphiteAqua", "Graphite Glass", "Magellan", "Nebula", "Raven", "Sahara", "Twilight" };

		for (String name : substanceLafNames) {
			String className = name.replace(" ", "");
			lafMenu.add(new JMenuItem(new ChangeLookAndFeelAction("Substance " + name,
				"org.pushingpixels.substance.api.skin.Substance" + className + "LookAndFeel")));
		}

		return lafMenu;
	}

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame();
            frame.setJMenuBar(createJMenuBar());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel cp = new JPanel(new BorderLayout());
            cp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			cp.setPreferredSize(new Dimension(500, 500));
			frame.setContentPane(cp);

            JButton button = new JButton("Show it");
            frame.add(button, BorderLayout.NORTH);

            int[] count = { 0 };

            button.addActionListener((e) -> {

            	JPanel content = new JPanel(new BorderLayout());
            	content.setOpaque(false);
            	content.add(new JLabel("This is toaster #" + count[0]), BorderLayout.LINE_START);
            	count[0]++;
            	content.add(new JButton("Action"), BorderLayout.LINE_END);

            	Toaster toaster = ToasterManager.get().addToaster(frame, content);
            	toaster.setVisible(true);
			});

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
