package sell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class PanelPickItem extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbIcon, lbPrice;
	JPanel pnIcon, pnText;
	String nameProduct = "", price = "", icon = "";
	JButton btnAddCart;
	JTextArea txtName;
	private int index = 0;

	public PanelPickItem(String name, String price, String icon) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		setBackground(Color.white);
		setString(name, price, icon);
		addControlls();
		addEvents();
		
	}

	private void addEvents() {
		btnAddCart.addMouseListener(this);
		
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	private void setString(String n, String p, String i) {
		nameProduct = n;
		price = p;
		icon = i;
	}

	private void addControlls() {
		lbIcon = new JLabel();
		lbIcon.setOpaque(true);
		ImageIcon imageIcon = null;
		Image image = null;
		Image newImage = null;
		try {
			imageIcon = new ImageIcon(getClass().getResource("/images/" + icon));

			image = imageIcon.getImage();

			newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

			lbIcon.setIcon(new ImageIcon(newImage));

		} catch (Exception e) {
			e.printStackTrace();
		}

		txtName = new JTextArea(nameProduct);
		txtName.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtName.setForeground(Color.blue);
		txtName.setOpaque(true);
		txtName.setAlignmentX(SwingConstants.CENTER);
		txtName.setColumns(5);
		txtName.setRows(3);
		txtName.setEditable(false);
		txtName.setLineWrap(true);
		txtName.setWrapStyleWord(true);

		lbPrice = new JLabel("Giá: " + price + " ₫");
		lbPrice.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbPrice.setForeground(Color.red);
		lbPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lbPrice.setBackground(Color.WHITE);
		lbPrice.setOpaque(true);

		pnIcon = new JPanel(new FlowLayout());
		pnIcon.setBackground(Color.WHITE);
		pnIcon.add(lbIcon);

		pnText = new JPanel(new BorderLayout());

		pnText.setBackground(Color.white);

		pnText.add(new JScrollPane(txtName), BorderLayout.CENTER);

		JPanel pnPrice = new JPanel(new BorderLayout());
		// pnText.add(lbPrice);
		pnPrice.add(lbPrice);

		JPanel pnButtonAdd = new JPanel(new BorderLayout());
		pnButtonAdd.setBackground(Color.white);
		// pnButtonAdd.setBorder(new EmptyBorder(10, 10, 10, 10));

		btnAddCart = new JButton("Đặt Hàng");
		try {
			btnAddCart.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/images/addcart.png")).getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT)));
		} catch (Exception e) {
			System.out.println("Không tìm thấy hình addcart.png");
		}
		pnButtonAdd.add(btnAddCart);

		pnPrice.add(pnButtonAdd, BorderLayout.SOUTH);
		pnText.add(pnPrice, BorderLayout.SOUTH);

		add(pnIcon, BorderLayout.CENTER);
		add(pnText, BorderLayout.SOUTH);

	}
	public JButton getBtnAddCart(){
		return this.btnAddCart;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == btnAddCart){
			btnAddCart.setForeground(Color.RED);
		}
		
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource() == btnAddCart){
			btnAddCart.setForeground(Color.BLACK);
		}
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
