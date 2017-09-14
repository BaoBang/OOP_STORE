package interfaces;

import javax.swing.SwingUtilities;

import login.Login;

public class Main {
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new Login();
		
			}
		});
		

	}
}
