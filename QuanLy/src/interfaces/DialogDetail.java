package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import other.MyTable;

public class DialogDetail extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[]colum_detail = {"STT", "Tên Sản Phẩm ", "Số Lượng", "Đơn Giá", "Giảm Giá"};
	JLabel lbOrderId;
	JLabel txtOrderId;
	JTable table = new MyTable();
	JPanel pnCenter;
	private static final int ORDER_DETAIL_STT = 0;
	private static final int ORDER_DETAIL_NAME = 1;
	private static final int ORDER_DETAIL_STOCK = 2;
	//private static final int ORDER_DETAIL_PRICE = 3;
	private static final int ORDER_DETAIL_DISCOUNT = 4;
	
	public DialogDetail(String title, int orderId){
		setTitle(title);
		setSize(800, 400);
		loadDataDetailTable(orderId);
		addControlls(orderId);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public void loadDataDetailTable(int order_id) {
		table = createTable();
		ResultSet resultSet = HomePage.connectDatabse.getOrderDetail("product_id, qty, price, discount",order_id);
		DefaultTableModel model_detail = (DefaultTableModel) table.getModel();
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
						ar[i] = HomePage.connectDatabse.getDataId(resultSet.getInt(i), "products").getString(2);
					}
					else
						ar[i] = resultSet.getString(i);
						
				}
				model_detail.addRow(ar);
			}

		} catch (SQLException e) {

		}
		table.setModel(model_detail);
		
	}
	private void addControlls(int orderId) {
		pnCenter = new JPanel(new BorderLayout());
		
		lbOrderId = new JLabel("Hóa Đơn Số");
		lbOrderId.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtOrderId = new JLabel();
		txtOrderId.setText(orderId + "");
		txtOrderId.setFont(new Font("Tahoma", Font.BOLD, 20));
		JPanel pnTitle = new JPanel(new FlowLayout());
		pnTitle.add(lbOrderId);
		pnTitle.add(txtOrderId);
		pnCenter.add(pnTitle, BorderLayout.NORTH);
		
		pnCenter.add(new JScrollPane(table));
		
		add(pnCenter);
		
	}
	
	private JTable createTable() {
		JTable table = new MyTable();
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(colum_detail);
		table.setModel(model);
		table.getColumnModel().getColumn(ORDER_DETAIL_STT).setMaxWidth(50);
		table.getColumnModel().getColumn(ORDER_DETAIL_NAME).setMinWidth(350);
		table.getColumnModel().getColumn(ORDER_DETAIL_STOCK).setMaxWidth(80);
		table.getColumnModel().getColumn(ORDER_DETAIL_DISCOUNT).setMinWidth(100);
		return table;
	}
	
}
