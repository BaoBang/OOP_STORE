package manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ConnectDatabase.ConnectDatabse;
import model.User;
import other.MyTable;
import other.MyTableRender;

public class PanelUser extends PanelImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String title = "Người Dùng";
	private final static int USER_STT = 0;
	private final static int USER_ID = 1;
	private final static int USER_EMAIL = 2;
	private final static int USER_NAME = 3;
	private final static int USER_PHONE = 4;
	private final static int USER_ADDRESS = 5;
	private final static int USER_CREATED_AT = 6;
	private final static int USER_LEVEL = 7;
	
	JTable table = new MyTable();
	DialogUser dialogUser;
	String[] colums = { "Số Thứ Tự", "Mã", "Email", "Họ Và Tên", "Số Điện Thoại" , "Địa Chỉ", "Ngày Đăng Ký", "Chức Vụ" };

	public PanelUser() {
		super(title);
		connectDatabse.setSqlCommand("select id,email,name,phone,address,created_at,level  from users");
		loadDataIntoTable(connectDatabse.getData());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	@Override
	public void doAdd() {
		dialogUser = new DialogUser("Thêm Người Dùng");
		dialogUser.btnOk.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String email = dialogUser.txtEmail.getText().toString();
				String name = dialogUser.txtName.getText().toString();
				String password = dialogUser.txtPassWord.getText().toString();
				String rePassword = dialogUser.txtRePassWord.getText().toString();
				String phone = dialogUser.txtPhone.getText().toString();
				for(int i = 0; i < phone.length(); i++){
					
					if(phone.charAt(i) < '0' || phone.charAt(i) > '9'){
						JOptionPane.showMessageDialog(null, "Số Điện Thoại Không Hợp Lệ!!");
						return;
					}
				}
				String address = dialogUser.txtAdress.getText().toString();
				String level_name = dialogUser.cbLevel.getSelectedItem().toString();
				int level = level_name == "SuperAdmin" ? 2 : level_name == "Admin" ? 1 : 0 ;

				if(!email.isEmpty() && !name.isEmpty() && !password.isEmpty() && !rePassword.isEmpty() && !phone.isEmpty() && !address.isEmpty())
				{
					if (connectDatabse.checkData("email",email, "users") == true) {
						JOptionPane.showMessageDialog(null, "Địa chỉ email này đã tồn tại",
								"Lỗi", JOptionPane.ERROR_MESSAGE);
					} else {
						if(password.equals(rePassword)){
							final User newUser = new User( email, password,  name, phone, address, level);
							insert(newUser);
							loadDataIntoTable(connectDatabse.getData());
							dialogUser.dispose();
						}
						else {
							JOptionPane.showMessageDialog(null, "Xác nhận lại mật khẩu không đúng, Vui lòng nhập lại",
									"Lỗi", JOptionPane.ERROR_MESSAGE);
						}
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống thông tin",
							"Cảnh Báo", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	
	private void insert(User user) {
		String sqlCommand = "insert into users (email, password, name, phone, address, level, created_at) value(?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setInt(6, user.getLevel());
			preparedStatement.setTimestamp(7, user.getCreatedAt());
			preparedStatement.executeUpdate();
			System.out.println("insert ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doDelete() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để xóa",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null,
				"Bạn có muôn xóa không?", "Xóa", 0, JOptionPane.YES_NO_OPTION,
				null, null, 1);

		if (select == 0) {
			connectDatabse.setSqlStatement("delete from users where id = ?");
			connectDatabse.deleteId((String) table.getValueAt(row, 1));
			loadDataIntoTable(connectDatabse.getData());
		}

	}

	@Override
	public void doUpdate() {
		final int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để sửa",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		dialogUser = new DialogUser("Cập Nhật Thông Tin Người Dùng");
		dialogUser.txtEmail.setText((String) table.getValueAt(row,USER_EMAIL));
		dialogUser.txtName.setText((String) table.getValueAt(row,USER_NAME));
		dialogUser.txtPhone.setText((String) table.getValueAt(row,USER_PHONE));
		dialogUser.txtAdress.setText((String) table.getValueAt(row,USER_ADDRESS));
		dialogUser.cbLevel.setSelectedItem(table.getValueAt(row, USER_LEVEL));
		dialogUser.txtEmail.setEditable(false);
		
		dialogUser.btnOk.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				String name = dialogUser.txtName.getText().toString();
				String password = dialogUser.txtPassWord.getText().toString();
				String rePassword = dialogUser.txtRePassWord.getText().toString();
				String phone = dialogUser.txtPhone.getText().toString();
				String address = dialogUser.txtAdress.getText().toString();
				String level_name = dialogUser.cbLevel.getSelectedItem().toString();
				int level = level_name == "SuperAdmin" ? 2 : level_name == "Admin" ? 1 : 0 ;
				int user_id = Integer.parseInt((String) table.getValueAt(row, USER_ID));
				for(int i = 0; i < phone.length(); i++){
					
					if(phone.charAt(i) < '0' || phone.charAt(i) > '9'){
						JOptionPane.showMessageDialog(null, "Số Điện Thoại Không Hợp Lệ!!");
						return;
					}
				}
				if(!name.isEmpty() && !phone.isEmpty() && !address.isEmpty() && !password.isEmpty() && !rePassword.isEmpty()){
					if(!password.equals(rePassword)) {				
							JOptionPane.showMessageDialog(null, "Xác nhận lại mật khẩu không đúng, Vui lòng nhập lại",
									"Lỗi", JOptionPane.ERROR_MESSAGE);
					}
					else {				
						updatePass(user_id, password ,name, phone, address, level);
						System.out.println("doUpdate ok");
						loadDataIntoTable(connectDatabse.getData());
						dialogUser.dispose();
					}
				}
				else if(!name.isEmpty() && !phone.isEmpty() && !address.isEmpty() && password.isEmpty()) {				
						update(user_id, name, phone, address, level);
						System.out.println("doUpdate ok");
						loadDataIntoTable(connectDatabse.getData());
						dialogUser.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin cá nhân",
							"Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void update(int id,String name,String phone,String address, int level) {
		String sqlCommand = "update users set name = ? , phone = ?, address = ?, level = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, phone);
			preparedStatement.setString(3, address);
			preparedStatement.setInt(4, level);
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			System.out.println("update ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void updatePass(int id, String password,String name,String phone,String address, int level) {
		String sqlCommand = "update users set password = ?, name = ? , phone = ?, address = ?, level = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, address);
			preparedStatement.setInt(5, level);
			preparedStatement.setInt(6, id);
			preparedStatement.executeUpdate();
			System.out.println("update ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doDetail() {
		
	};
	
	public void doSearch(){
		String key = txtSearch.getText();
		ResultSet resultSet = connectDatabse.getDataSearchUser(key);
		if(key.isEmpty()){
			loadDataIntoTable(connectDatabse.getData());
		}else{
			loadDataIntoTable(resultSet);
		}
	};
	
	@Override
	public void loadDataIntoTable(ResultSet resultSet) {	
		if(resultSet != null){
			DefaultTableModel model = new DefaultTableModel();
			try {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int colum = metaData.getColumnCount();
				String[] arr = new String[colum + 1];

				model.setColumnIdentifiers(colums);
				int index = 0;
				while (resultSet.next()) {
					index++;
					arr[0] = index + "";
					for (int i = 1; i <= colum; i++) {
						if(i==7){
							arr[i] = resultSet.getInt(i) == 2  ? "SuperAdmin" : resultSet.getInt(i) == 1 ? "Admin" : "User" ;
						}
						else arr[i] = resultSet.getString(i);
					}
					model.addRow(arr);
				}

			} catch (SQLException e) {

			}
			table.setModel(model);
			doFormatTable();
		}else{
			table.setModel(new DefaultTableModel(null, colums));
			doFormatTable();
		}
	}
	private void doFormatTable(){
		table.getColumnModel().getColumn(USER_STT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(USER_ID).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(USER_STT).setMinWidth(100);
		table.getColumnModel().getColumn(USER_ID).setMinWidth(100);
		table.getColumnModel().getColumn(USER_EMAIL).setMinWidth(200);
		table.getColumnModel().getColumn(USER_NAME).setMinWidth(200);
		table.getColumnModel().getColumn(USER_PHONE).setMinWidth(150);
		table.getColumnModel().getColumn(USER_ADDRESS).setMinWidth(200);
		table.getColumnModel().getColumn(USER_CREATED_AT).setMinWidth(200);
		table.getColumnModel().getColumn(USER_LEVEL).setMinWidth(100);
	}

}
