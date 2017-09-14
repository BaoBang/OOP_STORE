package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.User;
import other.MyTable;

public class DialogHistory extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbName, lbPhone, lbAddress;
	JTextField txtName, txtPhone, txtAddress;
	JPanel pnCenter;
	JTable tableOrder;
	JButton btnDetail;
	User user;
	private static final int ORDER_STT = 0;
	private static final int ORDER_ID = 1;
	private static final int ORDER_TOTAL = 2;
	private static final int ORDER_ADDRESS = 3;
	private static final int ORDER_CREATED_AT = 4;
	private static final int ORDER_STATUS = 5;
	String colums[] = { "Số Thứ Tự", "Số Hóa Đơn", "Tổng Tiền", "Địa Chỉ Giao Hàng", "Ngày Đặt Hàng",
			"Tình Trạng Đơn Hàng" };

	public DialogHistory(String title, User user) {
		this.user = user;
		setTitle(title);
		setSize(800, 500);
		HomePage.connectDatabse.connect();
		HomePage.connectDatabse.setSqlCommand(
				"select id, total, address, created_at, status from orders where user_id = " + user.getId());
		loadDataIntoTable(HomePage.connectDatabse.getData());
		addControlls();
		addEvents();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void addEvents() {
		btnDetail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				doShowOrderDetail();
			}
		});
	}

	private void doShowOrderDetail() {
		int selectedRow = tableOrder.getSelectedRow();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(null, "Bạn Cần Chọn Hóa Đơn Để Xem Chi Tiết!");
			return;
		}
		int orderId = Integer.parseInt(String.valueOf(tableOrder.getValueAt(selectedRow, ORDER_ID)));
		new DialogDetail("Chi Tiết Hóa Đơn", orderId);
	}

	private JTable createTable() {
		JTable table = new MyTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(colums);
		table.setModel(model);
		table.getColumnModel().getColumn(ORDER_STT).setMaxWidth(70);
		table.getColumnModel().getColumn(ORDER_ID).setMaxWidth(80);
		table.getColumnModel().getColumn(ORDER_TOTAL).setMinWidth(150);
		table.getColumnModel().getColumn(ORDER_ADDRESS).setMinWidth(200);
		table.getColumnModel().getColumn(ORDER_CREATED_AT).setMinWidth(150);
		table.getColumnModel().getColumn(ORDER_STATUS).setMinWidth(150);
		return table;
	}

	public void loadDataIntoTable(ResultSet resultSet) {
		tableOrder = createTable();
		if (resultSet != null) {
			DefaultTableModel model = (DefaultTableModel) tableOrder.getModel();

			try {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int colum = metaData.getColumnCount();
				String[] arr = new String[colum + 1];

				int index = 0;
				while (resultSet.next()) {
					index++;
					arr[0] = index + "";
					for (int i = 1; i <= colum; i++) {
						if (i == 5) {
							arr[i] = resultSet.getInt(i) == 2 ? "Giao Thành Công"
									: resultSet.getInt(i) == 1 ? "Đã Xác Nhận" : "Chưa Xác Nhận";
						} else
							arr[i] = resultSet.getString(i);
					}
					model.addRow(arr);
				}

			} catch (SQLException e) {

			}
			tableOrder.setModel(model);

		}
	}

	private void addControlls() {
		pnCenter = new JPanel(new BorderLayout());
		pnCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

		JPanel pnText = new JPanel(new GridLayout(2, 4, 20, 20));
		lbName = new JLabel("Họ và Tên");
		lbPhone = new JLabel("Số Điện Thoại");
		lbAddress = new JLabel("Địa Chỉ");

		txtName = new JTextField();
		txtName.setEditable(false);
		txtPhone = new JTextField();
		txtPhone.setEditable(false);
		txtAddress = new JTextField();
		txtAddress.setEditable(false);

		txtName.setText(user.getName());
		txtPhone.setText(user.getPhone());
		txtAddress.setText(user.getAddress());

		pnText.add(lbName);
		pnText.add(txtName);
		pnText.add(lbPhone);
		pnText.add(txtPhone);
		pnText.add(lbAddress);
		pnText.add(txtAddress);
		pnText.add(new JPanel());
		pnText.add(new JPanel());

		JPanel pnTable = new JPanel(new BorderLayout());
		btnDetail = new JButton();
		try{
			ImageIcon icon = new ImageIcon(getClass().getResource("/images/detail.png"));
			btnDetail.setIcon(icon);
		}catch (Exception e) {
			btnDetail.setText("Chi Tiết");
			System.out.println(e.getMessage());
		}
		
		JPanel pnButton = new JPanel(new FlowLayout(2, 10, 10));
		pnButton.add(btnDetail);
		pnTable.add(pnButton, BorderLayout.NORTH);
		pnTable.add(new JScrollPane(tableOrder), BorderLayout.CENTER);

		pnCenter.add(pnTable, BorderLayout.CENTER);
		pnCenter.add(pnText, BorderLayout.NORTH);
		add(pnCenter);

	}

}
