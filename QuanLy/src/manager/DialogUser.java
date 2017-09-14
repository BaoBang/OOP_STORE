package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DialogUser extends JDialog implements MouseListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel  lbUserName, lbPassWord, lbRePassWord, lbPhone, lbEmail, lbAddress, lbLevel;
	JTextField  txtName, txtPhone, txtEmail, txtAdress;
	JPasswordField txtPassWord, txtRePassWord;
	JComboBox<String> cbLevel;
	JButton btnOk, btnCancel;
	JPanel pnUser;
	
	String level[] = {"User" , "Admin", "SuperAdmin"}; 
	public DialogUser(String title){
		setTitle(title);
		addControlls();
		addEvents();
		setSize(500, 450);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	
	private void addEvents() {
		
		btnOk.addMouseListener(this);
		btnCancel.addMouseListener(this);
		
		txtAdress.addKeyListener(this);
		txtEmail.addKeyListener(this);
		txtName.addKeyListener(this);
		txtPassWord.addKeyListener(this);
		txtPhone.addKeyListener(this);
		txtRePassWord.addKeyListener(this);
		cbLevel.addKeyListener(this);
		btnOk.addKeyListener(this);
		btnCancel.addKeyListener(this);
	}


	protected void doCancel() {
		dispose();
		
	}




	private void addControlls() {
		pnUser = new JPanel(new BorderLayout(20,20));
		pnUser.setBorder(new EmptyBorder(20,20,20,20));
		
		JPanel panel = new JPanel(new GridLayout(7,2,20,20));
		
		
		lbEmail = new JLabel("Email");
		lbPassWord = new JLabel("Mật Khẩu");
		lbRePassWord = new JLabel("Nhập Lại Mật Khẩu");
		lbUserName = new JLabel("Họ Và Tên");
		lbPhone = new JLabel("Số Điện Thoại");
		lbAddress = new JLabel("Địa Chỉ");
		lbLevel = new JLabel("Chức vụ");
		
		
		txtEmail = new JTextField();
		txtPassWord = new JPasswordField();
		txtRePassWord = new JPasswordField();
		txtName = new JTextField();
		txtPhone = new JTextField();
		txtAdress = new  JTextField();
		cbLevel = new JComboBox<String>(level);
	
	
		panel.add(lbEmail);panel.add(txtEmail);
		panel.add(lbPassWord);panel.add(txtPassWord);
		panel.add(lbRePassWord);panel.add(txtRePassWord);
		panel.add(lbUserName);panel.add(txtName);
		panel.add(lbPhone);panel.add(txtPhone);
		panel.add(lbAddress);panel.add(txtAdress);
		panel.add(lbLevel);panel.add(cbLevel);
		
		pnUser.add(panel, BorderLayout.CENTER);
		
		JPanel panelButton = new JPanel(new GridLayout(1,2,50,20));
		panelButton.setBorder(new EmptyBorder(0,50,0,50));
		
		btnOk = new JButton("Xác Nhận");
		btnCancel = new  JButton("Hủy");
		panelButton.add(btnOk);
		panelButton.add(btnCancel);
		
		pnUser.add(panelButton, BorderLayout.SOUTH);
		add(pnUser);
			
	}


	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == btnCancel){
			dispose();
		}
	}


	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == btnOk){
			btnOk.setForeground(Color.red);
		}
		if(e.getSource() == btnCancel){
			btnCancel.setForeground(Color.red);
		}
		
	}


	public void mouseExited(MouseEvent e) {
		if(e.getSource() == btnOk){
			btnOk.setForeground(Color.black);
		}
		if(e.getSource() == btnCancel){
			btnCancel.setForeground(Color.black);
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
		
		if(e.getSource() == txtEmail){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				txtPassWord.grabFocus();
			}
		}
		if(e.getSource() == txtPassWord){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				txtRePassWord.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				txtEmail.grabFocus();
			}
		}
		
		if(e.getSource() == txtRePassWord){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				txtName.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				txtPassWord.grabFocus();
			}
		}
		
		if(e.getSource() == txtName){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				txtPhone.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				txtRePassWord.grabFocus();
			}
		}
		
		if(e.getSource() == txtPhone){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				txtAdress.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				txtName.grabFocus();
			}
		}
		
		if(e.getSource() == txtAdress){
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN){
				cbLevel.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				txtPhone.grabFocus();
			}
		}
		
		if(e.getSource() == cbLevel){
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				btnOk.grabFocus();
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				txtAdress.grabFocus();
			}
		
		}
		if(e.getSource() == btnOk){
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				btnCancel.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				cbLevel.grabFocus();
			}
		}
		if(e.getSource() == btnCancel){
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				doCancel();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				txtEmail.grabFocus();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_UP){
				btnOk.grabFocus();
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
