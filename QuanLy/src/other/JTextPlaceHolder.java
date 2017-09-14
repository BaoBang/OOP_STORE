package other;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class JTextPlaceHolder extends JTextField implements FocusListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String placeHolder;
	private Color colorHint = getForeground();
	private boolean showHint = true;

	public JTextPlaceHolder() {
	
	}

	public JTextPlaceHolder(String placeHolder) {
		super(placeHolder);
		this.placeHolder = placeHolder;
		showHint = true;
		addFocusListener(this);
		setColorHint(new Color(100, 100, 100));
		setForeground(colorHint);
		//setFocusable(false);
	}

	public Color getColorHint() {
		return colorHint;
	}

	public void setColorHint(Color colorHint) {
		this.colorHint = colorHint;
	}

	public void focusGained(FocusEvent e) {
		if (getText().isEmpty()) {
			super.setText("");
			showHint = false;
		}

	}

	public void focusLost(FocusEvent e) {
		if (getText().isEmpty()) {
			super.setText(placeHolder);
			showHint = true;
		}
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return showHint ? "" : super.getText();
	}

}
