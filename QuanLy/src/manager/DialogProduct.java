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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogProduct extends JDialog implements MouseListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbProductName, lbModel, lbBrand, lbCategory, lbStock, lbPrice, lbDiscount, lbDescription, lbImage;
	JTextField txtProductName, txtModel, txtImage;
	JSpinner spStock;
	JSpinner spPrice, spDiscount;
	JTextArea txtDescription;
	JComboBox<String> cbBrand, cbCategory;
	JButton btnOk, btnCancel, btnFileChoose;
	JPanel pnUser;
	JFileChooser fileChooser = new JFileChooser();

	public DialogProduct(String title) {
		setTitle(title);
		addControlls();
		addEvents();
		setSize(500, 550);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addEvents() {
		btnOk.addMouseListener(this);
		btnCancel.addMouseListener(this);
		btnFileChoose.addMouseListener(this);

		btnOk.addKeyListener(this);
		btnFileChoose.addKeyListener(this);
		btnCancel.addKeyListener(this);

		txtProductName.addKeyListener(this);
		txtModel.addKeyListener(this);
		txtImage.addKeyListener(this);
		txtDescription.addKeyListener(this);
		cbBrand.addKeyListener(this);
		cbCategory.addKeyListener(this);
		spDiscount.addKeyListener(this);
		spPrice.addKeyListener(this);
		

	}

	protected void doCancel() {
		dispose();

	}

	protected void doAddBrand() {
		// TODO Auto-generated method stub

	}

	private void doOpenFile() {
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		int select = fileChooser.showOpenDialog(btnFileChoose);
		if (select == JFileChooser.APPROVE_OPTION) {
			txtImage.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}

	}

	private void addControlls() {
		pnUser = new JPanel(new BorderLayout(20, 20));
		pnUser.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel pn = new JPanel(new BorderLayout(20, 20));
		JPanel panel = new JPanel(new GridLayout(8, 2, 20, 20));
		lbProductName = new JLabel("Tên Sản Phẩm");
		lbModel = new JLabel("Kiểu Mẫu");
		lbBrand = new JLabel("Hãng Sản Xuất");
		lbCategory = new JLabel("Loại Sản Phẩm");
		lbStock = new JLabel("Số Lượng");
		lbPrice = new JLabel("Đơn Giá");
		lbDiscount = new JLabel("Giảm Giá");
		lbDescription = new JLabel("Miêu tả");
		lbImage = new JLabel("Hình Đại Diện");

		txtProductName = new JTextField();
		txtModel = new JTextField();

		txtDescription = new JTextArea();
		txtDescription.setColumns(5);
		txtDescription.setRows(5);

		spStock = new JSpinner();
		spPrice = new JSpinner();
		spDiscount = new JSpinner();
		
		SpinnerModel modelStock = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		spStock.setModel(modelStock);

		SpinnerModel modelPrice = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		spPrice.setModel(modelPrice);

		SpinnerModel modelDiscount = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
		spDiscount.setModel(modelDiscount);
		
		cbBrand = new JComboBox<String>();
		cbCategory = new JComboBox<String>();
		txtImage = new JTextField();

		panel.add(lbProductName);
		panel.add(txtProductName);
		panel.add(lbModel);
		panel.add(txtModel);
		panel.add(lbCategory);
		panel.add(cbCategory);
		panel.add(lbBrand);
		panel.add(cbBrand);
		panel.add(lbStock);
		panel.add(spStock);
		panel.add(lbPrice);
		panel.add(spPrice);
		panel.add(lbDiscount);
		panel.add(spDiscount);

		JPanel pnImage = new JPanel(new BorderLayout(20, 20));
		pnImage.add(txtImage, BorderLayout.CENTER);
		btnFileChoose = new JButton();
		try {
			btnFileChoose.setIcon(new ImageIcon(getClass().getResource("/images/openfile.png")));
		} catch (Exception e) {
			btnFileChoose.setText("Chọn Hình Đại Diện");
			e.printStackTrace();
		}

		pnImage.add(btnFileChoose, BorderLayout.EAST);

		panel.add(lbImage);
		panel.add(pnImage);

		pn.add(panel, BorderLayout.CENTER);

		JPanel pnDescription = new JPanel(new BorderLayout());
		pnDescription.add(lbDescription, BorderLayout.WEST);
		JPanel pnTextAre = new JPanel(new BorderLayout());
		pnTextAre.add(new JScrollPane(txtDescription));
		pnTextAre.setBorder(new EmptyBorder(0, 190, 0, 0));
		pnDescription.add(pnTextAre, BorderLayout.CENTER);

		pn.add(pnDescription, BorderLayout.SOUTH);

		pnUser.add(pn, BorderLayout.CENTER);

		JPanel panelButton = new JPanel(new GridLayout(1, 2, 20, 20));
		panelButton.setBorder(new EmptyBorder(0, 50, 0, 50));
		btnOk = new JButton("Xác Nhận");
		btnCancel = new JButton("Hủy");
		panelButton.add(btnOk);
		panelButton.add(btnCancel);

		pnUser.add(panelButton, BorderLayout.SOUTH);
		add(pnUser);

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == btnFileChoose) {
			doOpenFile();
		}
		if (e.getSource() == btnCancel) {
			doCancel();
		}

	}

	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == btnOk) {
			btnOk.setForeground(Color.red);
		}
		if (e.getSource() == btnCancel) {
			btnCancel.setForeground(Color.red);
		}

	}

	public void mouseExited(MouseEvent e) {
		if (e.getSource() == btnOk) {
			btnOk.setForeground(Color.black);
		}
		if (e.getSource() == btnCancel) {
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			doCancel();
		}

		if (e.getSource() == txtProductName) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
				txtModel.grabFocus();
			}
		}

		if (e.getSource() == txtModel) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_DOWN) {
				cbCategory.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				txtProductName.grabFocus();
			}
		}

		if (e.getSource() == cbCategory) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				cbBrand.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				txtModel.grabFocus();
			}
		}

		if (e.getSource() == cbBrand) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				spStock.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				cbCategory.grabFocus();
			}
		}

		if (e.getSource() == spStock) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				spPrice.grabFocus();
			}
		}

		if (e.getSource() == spPrice) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				spDiscount.grabFocus();
			}
		}

		if (e.getSource() == spDiscount) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				txtImage.grabFocus();
			}
		}

		if (e.getSource() == txtImage) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnFileChoose.grabFocus();
			}
		}

		if (e.getSource() == btnFileChoose) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				doOpenFile();
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				txtDescription.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT) {
				txtImage.grabFocus();
			}
		}

		if (e.getSource() == btnOk) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				btnCancel.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP) {
				txtDescription.grabFocus();
			}
		}
		
		if (e.getSource() == btnCancel) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				txtProductName.grabFocus();
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_UP) {
				btnOk.grabFocus();
			}
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
