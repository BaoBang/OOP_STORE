package manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import other.MyTable;
import other.MyTableRender;
import ConnectDatabase.ConnectDatabse;


public class PanelOrder extends PanelImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int ORDER_STT = 0;
	private static final int ORDER_ID = 1;
	private static final int ORDER_TOTAL = 3;
	private static final int ORDER_NAME = 2;
	private static final int ORDER_PHONE = 4;
	private static final int ORDER_ADDRESS = 5;
	private static final int ORDER_CREATED_AT = 6;
	private static final int ORDER_STATUS = 7;
	private final static String title = "Đơn Hàng";
	
	JTable table = new MyTable();
	String colums[] = {"Số Thứ Tự", "Số Hóa Đơn","Tên Khách Hàng", "Tổng Tiền", "Số Điện Thoại Liên Hệ", "Địa Chỉ Giao Hàng", "Ngày Đặt Hàng", "Tình Trạng Đơn Hàng"};
	DialogOrder dialogOrder;
	DialogOrderDetail dialogOrderDetail;
	public PanelOrder() {
		super(title);
		connectDatabse.setSqlCommand("select orders.id, users.name, orders.total, users.phone,"
				+ " orders.address, orders.created_at, status "
				+ "from orders inner join users on users.id = user_id");
		pnCardLayout.setVisible(true);
		CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
		cardLayout.show(pnCardLayout, "ButtonDetail");
		
		btnUpdate.setVisible(true);
		
		loadDataIntoTable(connectDatabse.getData());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public ResultSet getResultSet(){
		return connectDatabse.getData();
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
			connectDatabse.setSqlStatement("delete from order_details where order_id = ?");
			connectDatabse.deleteId((String) table.getValueAt(row, 1));
			connectDatabse.setSqlStatement("delete from orders where id = ?");
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
		dialogOrder = new DialogOrder("Cập Nhật Thông Tin Đơn Hàng");
		dialogOrder.txtName.setText((String) table.getValueAt(row,ORDER_NAME));
		dialogOrder.txtAddress.setText((String) table.getValueAt(row,ORDER_ADDRESS));
		SpinnerModel modelTotal = new SpinnerNumberModel(Long.parseLong(table.getValueAt(row, ORDER_TOTAL).toString()), 0,Long.MAX_VALUE, 0);
		dialogOrder.spTotal.setModel(modelTotal);
		dialogOrder.cbStatus.setSelectedItem(table.getValueAt(row, ORDER_STATUS));
		dialogOrder.txtName.setEditable(false);
		dialogOrder.txtAddress.setEditable(false);
		((JSpinner.DefaultEditor) dialogOrder.spTotal.getEditor()).getTextField().setEditable(false);
		//ResultSet resultSet = connectDatabse.getDataId( Integer.parseInt((String) table.getValueAt(row, ORDER_ID)), "orders");
		
		dialogOrder.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String status_name = dialogOrder.cbStatus.getSelectedItem().toString();
				int status = status_name == dialogOrder.status[2] ? 2 : status_name == dialogOrder.status[1] ? 1 : 0 ;
				int order_id = Integer.parseInt((String) table.getValueAt(row, ORDER_ID));
					if(status_name.equals((String) table.getValueAt(row, ORDER_STATUS))) {				
							JOptionPane.showMessageDialog(null, "Vui lòng chọn trạng thái đơn hàng.",
									"Thông Báo", JOptionPane.WARNING_MESSAGE);
					}
					else {				
						update(order_id, status);
						System.out.println("doUpdate ok");
						loadDataIntoTable(connectDatabse.getData());
						dialogOrder.dispose();
					}
				}
		});

	}

	public void update(int order_id, int status) {
		String sqlCommand = "update orders set status = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, status);
			preparedStatement.setInt(2, order_id);
			preparedStatement.executeUpdate();
			System.out.println("update ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doAdd() {
		// nothing
	}
	
	public void doSearch(){
		String key = txtSearch.getText();
		ResultSet resultSet = connectDatabse.getDataSearchOrder(key);
		if(!key.isEmpty()){
			loadDataIntoTable(resultSet);
		}else{
			loadDataIntoTable(connectDatabse.getData());
		}
	};
	
	public void doDetail() {
		final int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để xem chi tiết",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		dialogOrderDetail = new DialogOrderDetail();
		dialogOrderDetail.txtName.setText((String) table.getValueAt(row,ORDER_NAME));
		dialogOrderDetail.txtAddress.setText((String) table.getValueAt(row,ORDER_ADDRESS));
		dialogOrderDetail.txtPhone.setText((String) table.getValueAt(row,ORDER_PHONE));
		dialogOrderDetail.txtCreatedAt.setText((String) table.getValueAt(row,ORDER_CREATED_AT));
		dialogOrderDetail.txtTotal.setText((String) table.getValueAt(row,ORDER_TOTAL));
		dialogOrderDetail.txtStatus.setText((String) table.getValueAt(row,ORDER_STATUS));
		dialogOrderDetail.txtOrderID.setText((String) table.getValueAt(row, ORDER_ID));

		loadDataDetailTable(Integer.parseInt((String) table.getValueAt(row, ORDER_ID)));
	}
	
	public void loadDataDetailTable(int order_id) {
		dialogOrderDetail.createdTable();
		ResultSet resultSet = connectDatabse.getOrderDetail("product_id, qty, price, discount",order_id);
		DefaultTableModel model_detail = new DefaultTableModel();
		model_detail.setColumnIdentifiers(dialogOrderDetail.colum_detail);

		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columm = metaData.getColumnCount();
			String[] ar = new String[columm + 1];
			
			int index = 0;
			
			while (resultSet.next()) {
				index++;
				ar[0] = index + "";
				for (int i = 1; i <= columm; i++) {
					if(i==1){
						ar[i] = connectDatabse.getDataId(resultSet.getInt(i), "products").getString(2);
					}
					else
						ar[i] = resultSet.getString(i);
						
				}
				model_detail.addRow(ar);
			}

		} catch (SQLException e) {

		}
		dialogOrderDetail.tbOrderDetail.setModel(model_detail);
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(0).setCellRenderer(new MyTableRender.AlignRender());
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(2).setCellRenderer(new MyTableRender.AlignRender());
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(3).setCellRenderer(new MyTableRender.AlignRender());
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(4).setCellRenderer(new MyTableRender.AlignRender());
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(0).setMaxWidth(70);
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(1).setMinWidth(200);
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(2).setMaxWidth(100);
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(3).setMinWidth(100);
		dialogOrderDetail.tbOrderDetail.getColumnModel().getColumn(4).setMinWidth(100);
		
	}
	
	
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
							arr[i] = resultSet.getInt(i) == 2  ? "Giao Thành Công" : resultSet.getInt(i) == 1 ? "Đã Xác Nhận" : "Chưa Xác Nhận" ;
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
		table.getColumnModel().getColumn(ORDER_STT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(ORDER_ID).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(ORDER_TOTAL).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(ORDER_STT).setMinWidth(100);
		table.getColumnModel().getColumn(ORDER_ID).setMinWidth(100);
		table.getColumnModel().getColumn(ORDER_TOTAL).setMinWidth(150);
		table.getColumnModel().getColumn(ORDER_NAME).setMinWidth(200);
		table.getColumnModel().getColumn(ORDER_PHONE).setMinWidth(150);
		table.getColumnModel().getColumn(ORDER_ADDRESS).setMinWidth(200);
		table.getColumnModel().getColumn(ORDER_CREATED_AT).setMinWidth(200);
		table.getColumnModel().getColumn(ORDER_STATUS).setMinWidth(150);
	}
	
	

}
