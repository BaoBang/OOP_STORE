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
import other.MyTable;
import other.MyTableRender;
import ConnectDatabase.ConnectDatabse;
import model.Category;

public class PanelCategory extends PanelImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String title = "Loại Sản Phẩm";
	private static final int CATEGORY_STT = 0;
	private static final int CATEGORY_ID = 1;
	private static final int CATEGORY_NAME = 2;
	JTable table = new MyTable();
	DialogCategory dialogCategory;
	String colums[] = { "Số Thứ Tự", "Mã Loại Sản Phẩm", "Tên Loại Sản Phẩm" };

	public PanelCategory() {
		super(title);
		connectDatabse.setSqlCommand("select id, name from categories");
		loadDataIntoTable(connectDatabse.getData());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void doAdd() {
		dialogCategory = new DialogCategory("Thêm Loại Sản Phẩm");
		dialogCategory.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String categoryName = dialogCategory.txtName.getText().toString();
				if(categoryName.isEmpty()){
					JOptionPane.showMessageDialog(null, "Chưa nhập tên loại");
					return;
				}
				if (connectDatabse.checkData("name", categoryName, "categories") == true) {
					JOptionPane.showMessageDialog(null, "Tên loại sản phẩm này đã tồn tại", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				} else {
					final Category newCategory = new Category(categoryName);
					insert(newCategory);
					loadDataIntoTable(connectDatabse.getData());
					dialogCategory.dispose();
				}

			}
		});
	}
	
	private void insert(Category category) {
		String sqlCommand = "insert into categories (name) value(?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, category.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doDelete() {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Xóa loại sẽ mất dữ liệu sản phẩm! Bạn có chắc muốn xóa không?", "Xóa", 0, JOptionPane.YES_NO_OPTION,
				null, null, 1);

		if (select == 0) {
			if(connectDatabse.checkData("category_id", (String) table.getValueAt(row, 1) , "products")){
				connectDatabse.setSqlStatement("delete from products where category_id = ?");
				connectDatabse.deleteId((String) table.getValueAt(row, 1));
			}
			connectDatabse.setSqlStatement("delete from categories where id = ?");
			connectDatabse.deleteId((String) table.getValueAt(row, 1));
			loadDataIntoTable(connectDatabse.getData());
		}

	}
	
	@Override
	public void doUpdate() {
		final int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		dialogCategory = new DialogCategory("Cập Nhật Thông Tin Loại Sản Phẩm");
		dialogCategory.txtName.setText((String) table.getValueAt(row, 2));
		dialogCategory.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String categoryName = dialogCategory.txtName.getText().toString();
				if(categoryName.isEmpty()){
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống thông tin", "Cảnh Báo", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if (!categoryName.equals((String) table.getValueAt(row, CATEGORY_NAME))
							&& connectDatabse.checkData("name", categoryName, "categories") == true) {
						JOptionPane.showMessageDialog(null, "Tên loại sản phẩm này đã tồn tại", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else {
						final Category newCategory = new Category(categoryName);
						update((String) table.getValueAt(row, CATEGORY_ID), newCategory);
						loadDataIntoTable(connectDatabse.getData());
						dialogCategory.dispose();
					}
				}

			}
		});
	}
	
	private void update(String id, Category category) {
		String sqlCommand = "update categories set name = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, category.getName());
			preparedStatement.setString(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doDetail() {
	
	};
	
	public void doSearch() {
		String key = txtSearch.getText();
		if (!key.isEmpty()) {
			ResultSet resultSet = connectDatabse.getDataSearchCategory(key);
			loadDataIntoTable(resultSet);
			
		} else{
			loadDataIntoTable(connectDatabse.getData());
		}			
	};

	@Override
	public void loadDataIntoTable(ResultSet resultSet) {

		DefaultTableModel model = new DefaultTableModel();
		if (resultSet != null) {
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
						arr[i] = resultSet.getString(i);
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
		table.getColumnModel().getColumn(CATEGORY_STT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(CATEGORY_ID).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(CATEGORY_STT).setMinWidth(100);
		table.getColumnModel().getColumn(CATEGORY_ID).setMinWidth(150);
		table.getColumnModel().getColumn(CATEGORY_STT).setMaxWidth(200);
		table.getColumnModel().getColumn(CATEGORY_ID).setMaxWidth(200);
	}

}
