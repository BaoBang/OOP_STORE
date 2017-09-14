package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DialogOrder extends JDialog implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnOk, btnCancel;
	JLabel lbUser, lbCustomer, lbAddress,lbTotal, lbStatus;
	JTextField txtUser, txtName, txtAddress;
	JSpinner spTotal;
	JComboBox<String> cbStatus;
	JPanel pnOrder;
	
	String status[] = { "Chưa Xác Nhận" , "Đã Xác Nhận" , "Giao Thành Công" }; 
	
	public DialogOrder(String title){
		setTitle(title);
		addControlls();
		addEvents();
		setSize(500, 400);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addEvents() {
		
		btnOk.addMouseListener(this);
		btnCancel.addMouseListener(this);
		
		btnCancel.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				doCancel();
				
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				doAddOrder();
				
			}
		});
		
	}

	private void doAddOrder() {
		// TODO Auto-generated method stub
		
	}

	private void doCancel() {
		dispose();
	}

	private void addControlls() {
		
		lbAddress = new JLabel("Địa Chỉ");
		lbCustomer = new JLabel("Tên Khách hàng");
		//lbUser = new JLabel("Nhân Viên");
		lbStatus = new JLabel("Tình Trạng");
		lbTotal = new JLabel("Tổng Tiền");
		
		txtAddress = new JTextField();
		txtName = new JTextField();
		spTotal = new JSpinner();
		//txtUser = new JTextField();
		cbStatus = new JComboBox<String>(status);
		
		btnOk = new JButton("Xác Nhận");
		btnCancel = new JButton("Hủy");
		
		JPanel panel = new JPanel(new GridLayout(6, 2, 20, 20));
		//panel.add(lbUser);panel.add(txtUser);
		panel.add(lbCustomer);panel.add(txtName);
		panel.add(lbAddress);panel.add(txtAddress);
		panel.add(lbTotal);panel.add(spTotal);
		panel.add(lbStatus);panel.add(cbStatus);
		
		JPanel pnOrder = new JPanel(new BorderLayout(20,20));
		pnOrder.setBorder(new EmptyBorder(20,20,20,20));
		pnOrder.add(panel, BorderLayout.CENTER);
		
		JPanel pnControlls = new JPanel(new GridLayout(1,2,80,0));
		pnControlls.setBorder(new EmptyBorder(0, 50, 0, 50));
		pnControlls.add(btnOk);
		pnControlls.add(btnCancel);
		
		pnOrder.add(pnControlls, BorderLayout.SOUTH);
		add(pnOrder);
		
		
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
			btnOk.setForeground(Color.BLACK);
		}
		if(e.getSource() == btnCancel){
			btnCancel.setForeground(Color.BLACK);
		}
		
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
