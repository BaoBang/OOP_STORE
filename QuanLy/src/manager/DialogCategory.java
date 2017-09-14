package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DialogCategory extends JDialog implements MouseListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnOk, btnCancle;
	JTextField txtName;
	JLabel lbName;
	JPanel pnCategory; 
	public DialogCategory(String title){
		setTitle(title);
		addControlls();
		addEvents();
		setSize(350, 180);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addEvents() {
	
		btnOk.addMouseListener(this);
		btnCancle.addMouseListener(this);
		btnCancle.addKeyListener(this);
		btnOk.addKeyListener(this);
		txtName.addKeyListener(this);
		addKeyListener(this);
		
		
	}

	private void doCancel() {
		dispose();
		
	}

	

	private void addControlls() {
		
		pnCategory = new JPanel(new BorderLayout(20,30));
		pnCategory.setBorder(new EmptyBorder(30,20,30,20));
		
		
		lbName = new JLabel("Tên Loại Sản Phẩm");
		
		
		txtName =  new JTextField();
	
		btnOk = new JButton("Xác Nhận");
		btnCancle = new JButton("Hủy");
		
		JPanel panel = new JPanel(new GridLayout(1,2, 20,20));
		panel.add(lbName);
		panel.add(txtName);
		
		
		JPanel pnButon = new JPanel(new GridLayout(1,2,30,20));
		pnButon.setBorder(new EmptyBorder(0,30,0,30));
		pnButon.add(btnOk);
		pnButon.add(btnCancle);
		pnCategory.add(panel, BorderLayout.CENTER);
		pnCategory.add(pnButon, BorderLayout.SOUTH);
		
		add(pnCategory);
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == btnCancle){
			doCancel();
		}
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == btnOk){
			btnOk.setForeground(Color.red);
		}
		if(e.getSource() == btnCancle){
			btnCancle.setForeground(Color.red);
		}
		
		
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource() == btnOk){
			btnOk.setForeground(Color.black);
		}
		if(e.getSource() == btnCancle){
			btnCancle.setForeground(Color.black);
		}
		
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			doCancel();
		}
		if(e.getSource() == btnCancle){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				doCancel();
			}
		}
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
