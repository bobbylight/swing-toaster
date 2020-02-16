package org.fife.ui.toaster;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * A notification that pops up in the bottom corner of an application.
 */
public class Toaster extends JWindow {

	public static final int DEFAULT_CLOSE_DELAY = 8000;

	private Timer timer;
    private JComponent content;
    private int closeDelay;

    private static final Border EMPTY_5_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	public Toaster(Window parent, String content) {
		this(parent, new JLabel(content));
	}

    public Toaster(Window parent, JComponent content) {

    	super(parent);
        JPanel cp = new JPanel(new BorderLayout());
        setContentPane(cp);
        cp.setBorder(BorderFactory.createCompoundBorder(
                UIManager.getBorder("ToolTip.border"),
                EMPTY_5_BORDER));
        cp.setBackground(UIManager.getColor("ToolTip.background"));
        cp.add(content);

        closeDelay = DEFAULT_CLOSE_DELAY;
        setAlwaysOnTop(true);
    }

	public int getCloseDelay() {
    	return closeDelay;
	}

    public void setCloseDelay(int millis) {
    	this.closeDelay = millis;
	}

    public void setVisible(boolean visible) {

		if (timer != null) {
			timer.stop();
			timer = null;
		}

		super.setVisible(visible);
		if (visible) {
			setOpacity(1f);
		}
		else {
			return;
		}

    	float[] opacity = { 1 + (closeDelay / 1000f) };

		timer = new Timer(20, (e) -> {
			opacity[0] -= 0.05f;
			if (opacity[0] <= 0) {
				super.setVisible(false);
				dispose();
				timer = null;
			} else if (opacity[0] < 1) {
				setOpacity(opacity[0]);
			}
		});
		timer.start();
	}

    static class ToasterLayoutManager implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {

        }
    }
}
