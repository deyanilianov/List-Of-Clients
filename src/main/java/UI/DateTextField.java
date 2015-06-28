package UI;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import Utill.DateValidator;

public class DateTextField extends Text {

	private Text txt;
	private static DateValidator dv = new DateValidator();

	public DateTextField(Composite parent, int style) {
		super(parent, style);
		txt = this;
		this.addKeyListener(customKeyListener());
		this.addFocusListener(customFocusListener());
	}

	public boolean isDateValid() {
		return new DateValidator().validate(this.getText());
	}

	private KeyAdapter customKeyListener() {
		KeyAdapter keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				try {
					String[] strArr = txt.getText().split("\\.");
					String newDate;
					String zeroDay;
					if (((event.stateMask & SWT.CTRL) == SWT.CTRL) && (event.keyCode == SWT.ARROW_UP)) {

						int day = Integer.parseInt(strArr[0]);
						zeroDay = ((strArr[0].contains("0")) && (day < 9)) ? "0" : "";
						day++;
						newDate = zeroDay + day + "." + strArr[1] + "." + strArr[2];
						if (!dv.validate(newDate)) {
							return;
						}
						txt.setText(newDate);
					}
					if (((event.stateMask & SWT.CTRL) == SWT.CTRL) && (event.keyCode == SWT.ARROW_DOWN)) {

						int day = Integer.parseInt(strArr[0]);
						zeroDay = ((strArr[0].contains("0")) && (day < 9)) ? "0" : "";
						day = day > 1 ? --day : 1;
						newDate = zeroDay + day + "." + strArr[1] + "." + strArr[2];
						if (!dv.validate(newDate)) {
							return;
						}
						txt.setText(newDate);
					}
				} catch (ArrayIndexOutOfBoundsException arr) {
					return;
				} catch (NumberFormatException e) {
					return;
				}

			}
		};
		return keyListener;
	}

	private FocusListener customFocusListener() {
		FocusListener focusListener = new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if (!txt.getText().trim().isEmpty() && dv.validateWithoutYear(txt.getText().trim())) {
					int year = Calendar.getInstance().get(Calendar.YEAR);
					txt.setText(txt.getText().trim() + "." + year);
				}
			}
		};
		return focusListener;
	}

	@Override
	protected void checkSubclass() {
	}
}
