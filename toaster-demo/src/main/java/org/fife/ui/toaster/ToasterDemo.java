package org.fife.ui.toaster;

import javax.swing.*;
import java.awt.*;

/**
 * A demo of the toaster component.
 */
public class ToasterDemo extends JWindow {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel cp = new JPanel(new BorderLayout());
            cp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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

            	Toaster toaster = ToasterManager.addToaster(frame, content);
            	toaster.setVisible(true);
			});

            frame.setSize(new Dimension(500, 500));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
