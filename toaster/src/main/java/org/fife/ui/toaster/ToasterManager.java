package org.fife.ui.toaster;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Creates and tracks {@code Toaster} instances and ensures they are laid out appropriately.
 */
public final class ToasterManager {

	public static final int DEFAULT_CLOSE_DELAY = Toaster.DEFAULT_CLOSE_DELAY;

	private static ToasterManager instance;

	private List<Toaster> toasters = new ArrayList<>();
	private Map<Window, ParentListener> listeners = new HashMap<>();
	private int defaultCloseDelay;

	/**
	 * Private constructor to prevent instantiation.
	 */
	private ToasterManager() {
		defaultCloseDelay = DEFAULT_CLOSE_DELAY;
	}

	public Toaster addToaster(Window parent, JComponent content) {

		Toaster toaster = new Toaster(parent, content);
		toaster.setCloseDelay(getDefaultCloseDelay());
		toaster.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				removeToasterAndAdjustToasterPositions(toaster);
				toaster.removeWindowListener(this);
			}
		});

		toaster.pack();

		List<Toaster> toasters = getToastersForWindow(parent);
		if (toasters.isEmpty()) {
			setLocationRelativeToParent(toaster);
		}
		else {
			setLocationRelativeToToaster(toaster, toasters.get(toasters.size() - 1));
		}

		ToasterManager.get().toasters.add(toaster);
		if (!listeners.containsKey(parent)) {
			ParentListener listener = new ParentListener();
			listener.install(parent);
			listeners.put(parent, listener);
		}
		return toaster;
	}

	private void adjustToasterPositionsForWindow(Window parent) {

		List<Toaster> toasters = getToastersForWindow(parent);

		for (int i = 0; i < toasters.size(); i++) {
			if (i == 0) {
				setLocationRelativeToParent(toasters.get(i));
			}
			else {
				setLocationRelativeToToaster(toasters.get(i), toasters.get(i - 1));
			}
		}
	}

	public static ToasterManager get() {

		if (instance == null) {
			instance = new ToasterManager();
		}
		return instance;
	}

	public int getDefaultCloseDelay() {
		return defaultCloseDelay;
	}

	private List<Toaster> getToastersForWindow(Window parent) {
		return ToasterManager.get().toasters.stream()
			.filter(f -> f.getParent() == parent)
			.collect(Collectors.toList());
	}

	private void removeToasterAndAdjustToasterPositions(Toaster toaster) {
		toasters.remove(toaster);
		adjustToasterPositionsForWindow((Window)toaster.getParent());
	}

	public void setDefaultCloseDelay(int millis) {
		this.defaultCloseDelay = millis;
	}

	private void setLocationRelativeToParent(Toaster toaster) {

		Window parent = (Window)toaster.getParent();

		Dimension size = toaster.getPreferredSize();
		int x = parent.getX() + parent.getWidth() - size.width - 5;
		int y = parent.getY() + parent.getHeight() - size.height - 5;

		toaster.setLocation(x, y);
	}

	private void setLocationRelativeToToaster(Toaster toaster, Toaster relativeTo) {

		Window parent = (Window)toaster.getParent();

		Dimension size = toaster.getPreferredSize();
		int x = parent.getX() + parent.getWidth() - size.width - 5;
		int y = relativeTo.getY() - size.height - 5;

		toaster.setLocation(x, y);
	}

	private class ParentListener implements ComponentListener {

		public void install(Window parent) {
			parent.addComponentListener(this);
		}

		public void uninstall(Window parent) {
			parent.removeComponentListener(this);
		}

		@Override
		public void componentResized(ComponentEvent e) {
			adjustToasterPositionsForWindow((Window)e.getComponent());
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			adjustToasterPositionsForWindow((Window)e.getComponent());
		}

		@Override
		public void componentShown(ComponentEvent e) {
			adjustToasterPositionsForWindow((Window)e.getComponent());
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			adjustToasterPositionsForWindow((Window)e.getComponent());
		}
	}
}
