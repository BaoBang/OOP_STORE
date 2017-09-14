package interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import ConnectDatabase.ConnectDatabse;
import model.User;

public class DialogEditInforUser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbName, lbPhone, lbAddress;
	JTextField txtName, txtPhone, txtAddress;
	JButton btnOk, btnCancle;
	JPanel pnMain;
	User user;
	private boolean isChage = false;

	public DialogEditInforUser(String title, User user) {
		setTitle(title);
		this.user = user;
		addControlls();
		addEvents();
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(400, 250);
		setVisible(true);
	}

	private void addEvents() {
		btnCancle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();

			}
		});
		btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doChangeUserInformation();

			}
		});
		txtAddress.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {
				isChage = true;

			}
		});
		txtName.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {
				isChage = true;

			}
		});
		txtPhone.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent e) {
				isChage = true;

			}
		});
	}

	public void doChangeUserInformation() {
		if(isChage){
			int answer = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Thay Đổi Thông Tin Không?", "Tin Nhắn Hệ Thống",
					JOptionPane.YES_NO_OPTION);
			if (answer == 0) {
				String name = txtName.getText();
				String phone = txtPhone.getText();
				String address = txtAddress.getText();

				user.setName(name);
				user.setAddress(address);
				user.setPhone(phone);
				if (update(user)) {
					JOptionPane.showMessageDialog(null, "Cập Nhật Thông Tin Thành Công");
					isChage = false;
					
				} else {
					JOptionPane.showMessageDialog(null, "Thay Đổi Thông Tin Thất Bại!");
				}
			}
		}
		dispose();
	}

	private boolean update(User user) {
		String sqlCommand = "update users set name = ?, phone = ?, address = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getPhone());
			preparedStatement.setString(3, user.getAddress());
			preparedStatement.setString(4, String.valueOf(user.getId()));
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void addControlls() {
		pnMain = new JPanel(new BorderLayout(0, 30));
		pnMain.setBorder(new EmptyBorder(20, 20, 20, 20));
		JPanel pnText = new JPanel(new GridLayout(3, 2, 0, 20));
		// pnText.setBorder(new EmptyBorder(0, 0, 0, 0));

		lbName = new JLabel("Họ Và Tên");
		lbPhone = new JLabel("Số Điện Thoại");
		lbAddress = new JLabel("Địa Chỉ");

		txtName = new JTextField();
		txtPhone = new JTextField();
		txtAddress = new JTextField();

		txtName.setText(user.getName());
		txtAddress.setText(user.getAddress());
		txtPhone.setText(user.getPhone());

		pnText.add(lbName);
		pnText.add(txtName);
		pnText.add(lbPhone);
		pnText.add(txtPhone);
		pnText.add(lbAddress);
		pnText.add(txtAddress);

		pnMain.add(pnText, BorderLayout.CENTER);

		JPanel pnButton = new JPanel(new GridLayout(1, 2, 50, 0));
		pnButton.setBorder(new EmptyBorder(0, 50, 0, 50));
		btnOk = new JButton("Lưu");
		btnCancle = new JButton("Hủy");
		pnButton.add(btnOk);
		pnButton.add(btnCancle);
		pnMain.add(pnButton, BorderLayout.SOUTH);

		add(pnMain);
	}
}
