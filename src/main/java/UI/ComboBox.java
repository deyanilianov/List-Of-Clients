package UI;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ComboBox extends Combo {

	private static final String[] cities = { "Sofia", "Plovdiv", "Varna", "Burgas" };
	private Combo combo;

	public ComboBox(Composite parent, int style) {
		super(parent, style);
		this.setItems(cities);
		this.addKeyListener(citySwitcherListener());
		this.combo = this;
	}

	private KeyAdapter citySwitcherListener() {
		return new KeyAdapter() {

			String selected = "";

			public void keyPressed(KeyEvent e) {
				if (combo.getText().isEmpty()) {
					int key;
					try {
						key = Integer.parseInt(Character.toString(e.character));
					} catch (NumberFormatException ex) {
						return;
					}

					if (key >= 1 && key <= cities.length) {
						selected = cities[key - 1];
					}
				}

			}

			public void keyReleased(KeyEvent e) {
				if (selected.length() > 0) {
					combo.setText(selected);
				}
				selected = "";
			}
		};
	}

	@Override
	protected void checkSubclass() {
	}
}
