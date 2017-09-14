package manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import ConnectDatabase.ConnectDatabse;
import other.MyTable;
import other.MyTableRender;
import model.Brand;

public class PanelBrand extends PanelImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int BRAND_STT = 0;
	public static int BRAND_ID = 1;
	public static int BRAND_IMAGE = 2;
	public static int BRAND_NAME = 3;
	DialogBrand dialogBrand;
	private final static String title = "Hãng Sản Xuất";
	MyTable table = new MyTable();
	String colums[] = { "Số Thứ Tự", "Mã Hãng Sản Xuất", "Hình Đại Diện", "Tên Hãng Sản Xuất" };
	String sqlComand = "";
	public PanelBrand() {
		super(title);
		connectDatabse.setSqlCommand("select * from brands");
		loadDataIntoTable(connectDatabse.getData());
		table.setChangeRowHeight(true);
		table.setRowHeight(60);
		add(new JScrollPane(table), BorderLayout.CENTER);

	}

	@Override
	public void doAdd() {
		dialogBrand = new DialogBrand("Thêm Hãng Sản Xuất");

		dialogBrand.btnOk.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					doAddBrand(dialogBrand);
				}

			}
		});

		dialogBrand.txtImage.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					doAddBrand(dialogBrand);
				}

			}
		});

		dialogBrand.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doAddBrand(dialogBrand);

			}
		});

	}

	private void doAddBrand(DialogBrand dialogBrand2) {
		String brandName = dialogBrand.txtName.getText().toString();
		String image = dialogBrand.txtImage.getText().toString();

		if (brandName.isEmpty() || image.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Đầy Đủ Thông Tin!");
			return;
		}

		Path p = Paths.get(image);
		
		if (!Files.exists(p) && image.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Không Tìm Thấy Hình Ảnh!");
			return;
		} else {
			try {
				FileOutputStream fos = new FileOutputStream("./src/images/" + p.getFileName());
				Files.copy(p.toAbsolutePath(), fos);
				fos.close(); 

				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (connectDatabse.checkData("name", brandName, "brands") == true) {
			JOptionPane.showMessageDialog(null, "Tên hãng đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
		} else {

			final Brand newBrand = new Brand(brandName, p.getFileName().toString());
			
			insert(newBrand);
			ResultSet resultSet = connectDatabse.getData();
			loadDataIntoTable(resultSet);
			dialogBrand.dispose();
		}

	}

	private void insert(Brand brand) {
		String sqlCommand = "insert into brands (name,image) value(?, ?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, brand.getName());
			preparedStatement.setString(2, brand.getImage());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doDelete() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Xóa hãng sẽ mất dữ liệu sản phẩm! Bạn có chắc muốn xóa không?", "Xóa", 0, JOptionPane.YES_NO_OPTION,
				null, null, 1);

		if (select == 0) {
			if(connectDatabse.checkData("brand_id", (String) table.getValueAt(row, 1) , "products")){
				connectDatabse.setSqlStatement("delete from products where brand_id = ?");
				connectDatabse.deleteId((String) table.getValueAt(row, 1));
			}
			connectDatabse.setSqlStatement("delete from brands where id = ?");
			connectDatabse.deleteId((String) table.getValueAt(row, 1));
			ResultSet resultSet = connectDatabse.getData();
			loadDataIntoTable(resultSet);
			
			
		}

	}

	@Override
	public void doUpdate() {
		final int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		final String strImage = table.getValueAt(row, BRAND_IMAGE).toString();
		dialogBrand = new DialogBrand("Cập Nhật Thông Tin Hãng Sản Xuất");
		dialogBrand.txtName.setText(table.getValueAt(row, BRAND_NAME).toString());
		dialogBrand.txtImage.setText(table.getValueAt(row, BRAND_IMAGE).toString());
		dialogBrand.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String brandName = dialogBrand.txtName.getText().toString();
				String image =  dialogBrand.txtImage.getText().toString();
				
				Path path = Paths.get(image);
				if(strImage.compareTo(path.getFileName().toString()) != 0){
					try {
						FileOutputStream fos = new FileOutputStream("./src/images/" + path.getFileName());
						Files.copy(path.toAbsolutePath(), fos);
						fos.close(); 

						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					image = path.getFileName().toString();

				}
					//			System.out.println(image);
				if(brandName.isEmpty() || image.isEmpty()){
					JOptionPane.showMessageDialog(null, "Vui lòng không để trống thông tin", "Cảnh Báo", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if (!brandName.equals((String) table.getValueAt(row, BRAND_NAME))
							&& connectDatabse.checkData("name", brandName, "brands") == true) {
						JOptionPane.showMessageDialog(null, "Tên hãng này đã tồn tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
					} else {
						final Brand newBrand = new Brand(brandName, image);
						update((String) table.getValueAt(row, BRAND_ID), newBrand);
						ResultSet resultSet = connectDatabse.getData();
						loadDataIntoTable(resultSet);
						dialogBrand.dispose();
					}
				}

			}
		});

	}

	private void update(String id, Brand brand) {
		String sqlCommand = "update brands set name = ?, image = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, brand.getName());
			preparedStatement.setString(2, brand.getImage());
			preparedStatement.setString(3, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doDetail() {

	};

	public void doSearch() {
		String key = txtSearch.getText();
		if (!key.isEmpty()) {

			ResultSet resultSet = connectDatabse.getDataSearchBrand(key);
			loadDataIntoTable(resultSet);

		} else {
			ResultSet resultSet = connectDatabse.getData();
			loadDataIntoTable(resultSet);

		}
	};

	@Override
	public void loadDataIntoTable(ResultSet resultSet) {
		if (resultSet != null) {
			DefaultTableModel model = new DefaultTableModel();
			// ResultSet resultSet = connectDatabse.getData();

			try {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int colum = metaData.getColumnCount();
				String[] arr = new String[colum + 1];

				// set Tiêu đề cho các cột của bảng
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

		} else {
			table.setModel(new DefaultTableModel(null, colums));
			doFormatTable();
		}
	}

	private void doFormatTable(){
		table.getColumnModel().getColumn(BRAND_IMAGE).setCellRenderer(new MyTableRender.ImageRender());
		table.getColumnModel().getColumn(BRAND_STT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(BRAND_ID).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(BRAND_NAME).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(BRAND_STT).setMinWidth(100);
		table.getColumnModel().getColumn(BRAND_ID).setMinWidth(150);
		table.getColumnModel().getColumn(BRAND_STT).setMaxWidth(200);
		table.getColumnModel().getColumn(BRAND_ID).setMaxWidth(200);
	}
}
