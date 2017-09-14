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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ConnectDatabase.ConnectDatabse;
import model.User;

public class DialogEditAcount extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbUserName, lbOldPassWord, lbNewPassWord, lbComfigNewPassWord;
	JTextField txtUserName;
	JPasswordField txtOldPassWord, txtNewPassWord, txtPassWordComfig;
	JButton btnOk, btnCancle;
	JPanel pnMain;
	User user;
	public DialogEditAcount(String title, User user){
		setTitle(title);
		this.user = user;
		addControlls();
		addEvents();
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(400,300);
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
				doChangeAcountInformation(user);
				
			}
		});
		
	}
	@SuppressWarnings("deprecation")
	public void doChangeAcountInformation(User user) {
		String oldPass = txtOldPassWord.getText();
		String newPass = txtNewPassWord.getText();
		String passConfig = txtPassWordComfig.getText();
		
		if(oldPass.isEmpty()){
			JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Mật Khẩu Cũ!");
			return;
		}
		if(newPass.isEmpty()){
			JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Mật Khẩu Mới!");
			return;
		}
		if(passConfig.isEmpty()){
			JOptionPane.showMessageDialog(null, "Vui Lòng Xác Nhận Mật Khẩu Mới!");
			return;
		}
		
		if(user.getPassword().compareTo(oldPass) != 0){
			JOptionPane.showMessageDialog(null, "Mật Khẩu Cũ Không Đúng!");
			return;
		}
		if(newPass.compareTo(passConfig) != 0){
			JOptionPane.showMessageDialog(null, "Xác Nhận Mật Khẩu Không Đúng!");
			return;
		}
		
		int answer = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Thay Đổi Thông Tin Không?", "Messeage", JOptionPane.YES_NO_OPTION);
			if(answer == 0){
				user.setPassword(newPass);
				if(update(user)){
					JOptionPane.showMessageDialog(null, "Thay Đổi Mật Khẩu Thành Công!");
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Thay Đổi Mật Khẩu Thất Bại!");
				}
		}
	}
	
	private boolean update(User user) {
		String sqlCommand = "update users set password = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, String.valueOf(user.getId()));
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	private void addControlls() {
		pnMain = new JPanel(new BorderLayout(0,30));
		pnMain.setBorder(new EmptyBorder(20, 20, 20, 20));
		JPanel pnText = new JPanel(new GridLayout(4, 2, 0, 20));
		//pnText.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		lbUserName = new JLabel("Tên Đăng Nhập");
		lbOldPassWord = new JLabel("Mật Khẩu Cũ");
		lbNewPassWord = new JLabel("Mật Khẩu Mới");
		lbComfigNewPassWord = new JLabel("Xác Nhận Mật Khẩu");
		
		txtUserName = new JTextField();
		txtOldPassWord = new JPasswordField();
		txtNewPassWord = new JPasswordField();
		txtPassWordComfig = new JPasswordField();
		txtUserName.setEditable(false);
		txtUserName.setText(user.getEmail());
		
		pnText.add(lbUserName);pnText.add(txtUserName);
		pnText.add(lbOldPassWord);pnText.add(txtOldPassWord);
		pnText.add(lbNewPassWord);pnText.add(txtNewPassWord);
		pnText.add(lbComfigNewPassWord);pnText.add(txtPassWordComfig);
		
		pnMain.add(pnText, BorderLayout.CENTER);
		
		JPanel pnButton = new JPanel(new GridLayout(1, 2,50,0));
		pnButton.setBorder(new EmptyBorder(0, 50, 0, 50));
		btnOk = new JButton("Lưu");
		btnCancle = new JButton("Hủy");
		pnButton.add(btnOk);
		pnButton.add(btnCancle);
		pnMain.add(pnButton, BorderLayout.SOUTH);
		
		add(pnMain);
	}
	
}
