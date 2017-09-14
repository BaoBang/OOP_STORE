package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogBrand extends JDialog implements MouseListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnOk, btnCancle, btnFileChoose;
	JTextField txtName, txtImage;
	JLabel lbName, lbImage;
	JPanel pnBrand;
	JFileChooser fileChooser = new JFileChooser();

	public DialogBrand(String title) {
		setTitle(title);
		addControlls();
		addEvents();
		setSize(500, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addEvents() {

		txtName.addKeyListener(this);
		txtImage.addKeyListener(this);
		btnOk.addKeyListener(this);
		btnCancle.addKeyListener(this);
		btnFileChoose.addKeyListener(this);
		
		btnOk.addMouseListener(this);
		btnCancle.addMouseListener(this);
		btnFileChoose.addMouseListener(this);
		addKeyListener(this);
	}

	private void doCancel() {
		dispose();
	}

	public void doAddBrand() {

	}

	private void doOpenFile() {
		//---------------------------
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		//-------------------------------
		int select = fileChooser.showOpenDialog(btnFileChoose);
		
		
		
		if (select == JFileChooser.APPROVE_OPTION) {
			txtImage.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}

	}

	private void addControlls() {

		pnBrand = new JPanel(new BorderLayout(20, 20));
		pnBrand.setBorder(new EmptyBorder(20, 20, 20, 20));

		lbName = new JLabel("Tên Hãng Sản Xuất");
		lbImage = new JLabel("Hình Đại Diện");

		txtName = new JTextField();
		txtImage = new JTextField();
		btnOk = new JButton("Xác Nhận");
		btnCancle = new JButton("Hủy");

		JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));

		panel.add(lbName);
		panel.add(txtName);
		panel.add(lbImage);

		JPanel pnTextImage = new JPanel(new BorderLayout(20, 20));
		pnTextImage.add(txtImage, BorderLayout.CENTER);
		btnFileChoose = new JButton();
		try {
			btnFileChoose.setIcon(new ImageIcon(getClass().getResource("/images/openfile.png")));
		} catch (Exception e) {
			btnFileChoose.setText("Chọn Hình Đại Diện");
			e.printStackTrace();
		}
		pnTextImage.add(btnFileChoose, BorderLayout.EAST);
		panel.add(pnTextImage);

		JPanel pnButon = new JPanel(new GridLayout(1, 2, 100, 20));
		pnButon.setBorder(new EmptyBorder(0, 50, 0, 50));
		pnButon.add(btnOk);
		pnButon.add(btnCancle);
		pnBrand.add(panel, BorderLayout.CENTER);
		pnBrand.add(pnButon, BorderLayout.SOUTH);

		add(pnBrand);
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == btnCancle) {
			doCancel();

		}
		if (e.getSource() == btnOk) {
			doAddBrand();
		}
		if (e.getSource() == btnFileChoose) {
			doOpenFile();
		}

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == btnOk) {
			btnOk.setForeground(Color.red);
		}
		if (e.getSource() == btnCancle) {
			btnCancle.setForeground(Color.red);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btnOk) {
			btnOk.setForeground(Color.BLACK);
		}
		if (e.getSource() == btnCancle) {
			btnCancle.setForeground(Color.BLACK);
		}

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			doCancel();
		}
		if (arg0.getSource() == txtImage) {
			if(arg0.getKeyCode() ==  KeyEvent.VK_UP || arg0.getKeyCode() ==  KeyEvent.VK_RIGHT ){
				txtName.grabFocus();
			}
			if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
				btnOk.grabFocus();
			}
			
		}
		if (arg0.getSource() == txtName) {
			if(arg0.getKeyCode() ==  KeyEvent.VK_ENTER ||arg0.getKeyCode() ==  KeyEvent.VK_DOWN || arg0.getKeyCode() ==  KeyEvent.VK_LEFT ){
				txtImage.grabFocus();
			}
			
		}
		if (arg0.getSource() == btnOk) {

		}
		if (arg0.getSource() == btnCancle) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				doCancel();
			}
		}
		if (arg0.getSource() == btnFileChoose) {
			if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				doOpenFile();
			}
		}

	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
