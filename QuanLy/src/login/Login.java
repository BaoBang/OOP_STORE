package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import ConnectDatabase.ConnectDatabse;
import interfaces.HomePage;
import model.User;
import other.MyColor;

public class Login extends JFrame implements MouseListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isLogin = false;
	JLabel lbTitle, lbEmail, lbPassword, lbIconLogin;
	JTextField txtEmail;
	JPasswordField txtPassword;

	JButton btnLogin, btnExit;

	JPanel pnCenter, pnTitle, pnText, pnButton;

	ConnectDatabse connectDatabse = new ConnectDatabse();

	public Login() {
		
		setSize(450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		connectDatabse.connect();
		addControlls();
		addEvent();
		setVisible(true);

	}

	private void addEvent() {
		btnLogin.addMouseListener(this);
		txtEmail.addKeyListener(this);
		txtPassword.addKeyListener(this);
		btnLogin.addKeyListener(this);
		addKeyListener(this);
	}

	public boolean isLogin() {
		String email = txtEmail.getText();
		@SuppressWarnings("deprecation")
		String password = txtPassword.getText();
		if (!email.isEmpty() && !password.isEmpty()) {
			ResultSet rs = connectDatabse.getDataName(email, "email", "users");
			
			if (rs != null) {
				String pwd = null;
				try {
					pwd = rs.getString(3);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (password.equals(pwd)) {
					System.out.println("Login Success");
					isLogin = true;

					try {
						final User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getString(5), rs.getString(6), rs.getInt(8));

						SwingUtilities.invokeLater(new Runnable() {

							public void run() {
						
								new HomePage(user);

							}
						});

					} catch (SQLException e) {
						e.printStackTrace();
					}

					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác.");
			}

		} else
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");

		return false;

	}

	public boolean CheckLogin() {
		if (isLogin == false)
			return false;
		else
			return true;

	}

	private void addControlls() {

		pnCenter = new JPanel(new BorderLayout(20, 20));
		pnCenter.setBorder(new EmptyBorder(10, 20, 20, 20));
		pnCenter.setBackground(MyColor.BACK_GROUND);
		
		pnText = new JPanel(new GridLayout(4, 1, 0, 0));
		pnText.setBackground(MyColor.BACK_GROUND);
		
		lbEmail = new JLabel("Tên Đăng Nhập");
		lbPassword = new JLabel("Mật Khẩu");
		txtEmail = new JTextField();
		txtPassword = new JPasswordField();
		pnText.add(lbEmail);
		pnText.add(txtEmail);
		pnText.add(lbPassword);
		pnText.add(txtPassword);

		pnButton = new JPanel(new GridLayout(1, 1, 0, 20));
		pnButton.setBorder(new EmptyBorder(0, 230, 20, 20));
		pnButton.setBackground(MyColor.BACK_GROUND);
		
		btnLogin = new JButton("Đăng Nhập");
		btnLogin.setBackground(Color.WHITE);
		
		ImageIcon iconLogin = new ImageIcon(getClass().getResource("/images/buttonlogin.png"));
		Image imageLogin = iconLogin.getImage().getScaledInstance(43, 43, Image.SCALE_DEFAULT);

		btnLogin.setIcon(new ImageIcon(imageLogin));

		pnButton.add(btnLogin);

		pnCenter.add(pnButton, BorderLayout.SOUTH);
		pnCenter.add(pnText, BorderLayout.CENTER);

		JPanel pnIconLogin = new JPanel(new BorderLayout());
		pnIconLogin.setBackground(MyColor.BACK_GROUND);
		lbIconLogin = new JLabel();

		ImageIcon icon = new ImageIcon(getClass().getResource("/images/keylogin.png"));
		Image image = icon.getImage().getScaledInstance(87, 114, Image.SCALE_DEFAULT);

		lbIconLogin.setIcon(new ImageIcon(image));

		pnIconLogin.add(lbIconLogin);

		pnCenter.add(pnIconLogin, BorderLayout.WEST);

		add(pnCenter);

	}

	public void mouseClicked(MouseEvent e) {
		isLogin();
	}

	public void mouseEntered(MouseEvent e) {

		if (e.getSource() == btnLogin) {
			btnLogin.setForeground(Color.red);
		}
		if (e.getSource() == btnExit) {
			btnExit.setForeground(Color.red);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btnLogin) {
			btnLogin.setForeground(Color.black);
		}
		if (e.getSource() == btnExit) {
			btnExit.setForeground(Color.BLACK);
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			dispose();
		}

		if (e.getSource() == txtEmail) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN
					|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
				txtPassword.grabFocus();
			}
		}

		if (e.getSource() == txtPassword) {
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT) {
				txtEmail.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnLogin.setSelected(true);
				isLogin();
			}
		}

		if (e.getSource() == btnLogin) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				isLogin();
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
