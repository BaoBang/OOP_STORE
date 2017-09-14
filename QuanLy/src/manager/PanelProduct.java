package manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import ConnectDatabase.ConnectDatabse;
import model.Brand;
import model.Category;
import model.Product;
import other.MyTable;
import other.MyTableRender;

public class PanelProduct extends PanelImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PRODUCT_STT = 0;
	private static final int PRODUCT_ID = 1;
	private static final int PRODUCT_IMAGE = 2;
	private static final int PRODUCT_NAME = 3;
	private static final int PRODUCT_MODEL = 4;
	private static final int PRODUCT_CATEGORY = 5;
	private static final int PRODUCT_BRAND = 6;
	private static final int PRODUCT_STOCK = 7;
	private static final int PRODUCT_PRICE = 8;
	private static final int PRODUCT_DISCOUNT = 9;
	private static final int PRODUCT_CREATED_AT = 10;

	private final static String title = "Sản Phẩm";
	MyTable table = new MyTable();
	DialogProduct dialogProduct;
	String[] colums = { "Số Thứ Tự", "Mã Sản Phẩm", "Hình Ảnh", "Tên Sản Phẩm", "Kiểu Mẫu", "Loại Sản Phẩm",
			"Hãng Sản Xuất", " Số Lượng Tồn", "Đơn Giá", "Giảm Giá", "Ngày Khởi Tạo" };
	String sql = "select products.id, products.image, products.name, model, categories.name, brands.name, stock, price, discount, created_at from products"
			+ " inner join brands on brand_id = brands.id" + " inner join categories on category_id = categories.id";

	public PanelProduct() {
		super(title);
		connectDatabse.setSqlCommand(sql);
		loadDataIntoTable(connectDatabse.getData());
		table.setChangeRowHeight(true);
		table.setRowHeight(60);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	@Override
	public void doAdd() {
		dialogProduct = new DialogProduct("Thêm Sản Phẩm");
		dialogProduct.cbCategory.setModel(getModel("categories", "name"));
		dialogProduct.cbBrand.setModel(getModel("brands", "name"));
		dialogProduct.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String productName = dialogProduct.txtProductName.getText().toString();
				String model = dialogProduct.txtModel.getText().toString();
				String category_name = dialogProduct.cbCategory.getSelectedItem().toString();
				String brand_name = dialogProduct.cbBrand.getSelectedItem().toString();
				int brand_id = 0;
				int category_id = 0;
				String brand_image = null;
				try {

					category_id = connectDatabse.getDataName(category_name,"name", "categories").getInt(1);
	
					brand_id = connectDatabse.getDataName(brand_name,"name", "brands").getInt(1);
					
					brand_image = connectDatabse.getDataName(brand_name,"name", "brands").getString(3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				int stock = (Integer) dialogProduct.spStock.getValue();
				int price = (Integer) dialogProduct.spPrice.getValue();
				int discount = (Integer) dialogProduct.spDiscount.getValue();
				
				if(discount >= price){
					JOptionPane.showMessageDialog(null, "Giá Không Hợp Lệ");
					return;
				}
				
				String description = dialogProduct.txtDescription.getText().toString();
				String image = dialogProduct.txtImage.getText().toString();
				if (!productName.isEmpty() && !model.isEmpty() && !category_name.isEmpty() && !brand_name.isEmpty()
						&& stock != 0 && price != 0 && !image.isEmpty()) {
					
					Path p = Paths.get(image);
					if (!Files.exists(p)) {
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
					
					
					if (connectDatabse.checkData("name", productName, "products") == true) {
						JOptionPane.showMessageDialog(null, "Tên sản phẩm này đã tồn tại", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else {
						
				
						final Product newProduct = new Product(productName, model,
								new Brand(brand_id, brand_name, brand_image), new Category(category_id, category_name),
								stock, price, discount, p.getFileName().toString(), description);
						System.out.println("doAdd ok");
						insert(newProduct);
						loadDataIntoTable(connectDatabse.getData());
						dialogProduct.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin sản phẩm", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

	}

	private void insert(Product product) {
		String sqlCommand = "insert into products (name, model, brand_id, category_id, stock, price, discount, image,description,created_at) value(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, product.getName());
			preparedStatement.setString(2, product.getModel());
			preparedStatement.setInt(3, product.getBrand().getId());
			preparedStatement.setInt(4, product.getCategory().getId());
			preparedStatement.setInt(5, product.getStock());
			preparedStatement.setInt(6, product.getPrice());
			preparedStatement.setInt(7, product.getDiscount());
			preparedStatement.setString(8, product.getImage());
			preparedStatement.setString(9, product.getDescription());
			preparedStatement.setTimestamp(10, product.getCreatedAt());
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
			JOptionPane.showMessageDialog(null, "Bạn cần chọn hàng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "Bạn có muôn xóa không?", "Xóa", 0, JOptionPane.YES_NO_OPTION,
				null, null, 1);

		if (select == 0) {
			connectDatabse.setSqlStatement("delete from products where id = ?");
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
		dialogProduct = new DialogProduct("Sửa Thông Tin Sản Phẩm");
		dialogProduct.txtProductName.setText((String) table.getValueAt(row, PRODUCT_NAME));
		dialogProduct.txtModel.setText((String) table.getValueAt(row, PRODUCT_MODEL));

		dialogProduct.cbCategory.setModel(getModel("categories", "name"));
		dialogProduct.cbCategory.setSelectedItem(table.getValueAt(row, PRODUCT_CATEGORY));

		dialogProduct.cbBrand.setModel(getModel("brands", "name"));
		dialogProduct.cbBrand.setSelectedItem(table.getValueAt(row, PRODUCT_BRAND));

		SpinnerModel modelStock = new SpinnerNumberModel(
				Integer.parseInt(table.getValueAt(row, PRODUCT_STOCK).toString()), 0, Integer.MAX_VALUE, 1);
		dialogProduct.spStock.setModel(modelStock);

		SpinnerModel modelPrice = new SpinnerNumberModel(
				Integer.parseInt(table.getValueAt(row, PRODUCT_PRICE).toString()), 0, Integer.MAX_VALUE, 1);
		dialogProduct.spPrice.setModel(modelPrice);

		SpinnerModel modelDiscount = new SpinnerNumberModel(
				Integer.parseInt(table.getValueAt(row, PRODUCT_DISCOUNT).toString()), 0, Integer.MAX_VALUE, 1);
		dialogProduct.spDiscount.setModel(modelDiscount);

		dialogProduct.txtImage.setText((String) table.getValueAt(row, PRODUCT_IMAGE));
				
		ResultSet resultSet = connectDatabse.getDataId(Integer.parseInt((String) table.getValueAt(row, PRODUCT_ID)),
				"products");
		
		
		try {
			String des = "";
			if (resultSet != null)
				des = resultSet.getString(8);
			dialogProduct.txtDescription.setText(des);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogProduct.btnOk.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String productName = dialogProduct.txtProductName.getText().toString();
				String model = dialogProduct.txtModel.getText().toString();
				String category_name = dialogProduct.cbCategory.getSelectedItem().toString();
				String brand_name = dialogProduct.cbBrand.getSelectedItem().toString();
				int stock = (Integer) dialogProduct.spStock.getValue();
				int price = (Integer) dialogProduct.spPrice.getValue();
				int discount = (Integer) dialogProduct.spDiscount.getValue();
				String description = dialogProduct.txtDescription.getText().toString();
				String image = dialogProduct.txtImage.getText().toString();
				String strImage = image;
				int product_id = Integer.parseInt((String) table.getValueAt(row, PRODUCT_ID));
				if(discount >= price){
					JOptionPane.showMessageDialog(null, "Giá Không Hợp Lệ");
					return;
				}
				if (!productName.isEmpty() && !model.isEmpty() && !category_name.isEmpty() && !brand_name.isEmpty()
						&& stock != 0 && price != 0 && !image.isEmpty()) {
					if (!productName.equals((String) table.getValueAt(row, PRODUCT_NAME))
							&& connectDatabse.checkData("name", productName, "products") == true) {
						JOptionPane.showMessageDialog(null, "Tên sản phẩm đã tồn tại", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							
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
										
							update(product_id, productName, model,
									connectDatabse.getDataName(brand_name,"name", "brands").getInt(1),
									connectDatabse.getDataName(category_name,"name", "categories").getInt(1), stock, price,
									discount, image, description);
							System.out.println("doUpdate ok");
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						loadDataIntoTable(connectDatabse.getData());
						dialogProduct.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin sản phẩm", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	private void update(int id, String productName, String model, int brand, int category, int stock, int price,
			int discount, String image, String description) {
		String sqlCommand = "update products set name = ?, model = ?, brand_id = ? , category_id = ?, stock = ?, price = ?, discount = ?, image = ?, description = ? where id = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
			preparedStatement.setString(1, productName);
			preparedStatement.setString(2, model);
			preparedStatement.setInt(3, brand);
			preparedStatement.setInt(4, category);
			preparedStatement.setInt(5, stock);
			preparedStatement.setInt(6, price);
			preparedStatement.setInt(7, discount);
			preparedStatement.setString(8, image);
			preparedStatement.setString(9, description);
			preparedStatement.setInt(10, id);
			preparedStatement.executeUpdate();
			System.out.println("update ok");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doDetail() {
		
	};

	public void doSearch() {
		String key = txtSearch.getText();
		ResultSet resultSet = connectDatabse.getDataSearchProduct(key);
		if(!key.isEmpty()){
			loadDataIntoTable(resultSet);
		}else{
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
		table.getColumnModel().getColumn(PRODUCT_IMAGE).setCellRenderer(new MyTableRender.ImageRender());
		table.getColumnModel().getColumn(PRODUCT_STT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(PRODUCT_ID).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(PRODUCT_STOCK).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(PRODUCT_PRICE).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(PRODUCT_DISCOUNT).setCellRenderer(new MyTableRender.AlignRender());
		table.getColumnModel().getColumn(PRODUCT_STT).setMinWidth(100);
		table.getColumnModel().getColumn(PRODUCT_ID).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_NAME).setMinWidth(300);
		table.getColumnModel().getColumn(PRODUCT_MODEL).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_CATEGORY).setMinWidth(200);
		table.getColumnModel().getColumn(PRODUCT_STOCK).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_PRICE).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_DISCOUNT).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_BRAND).setMinWidth(150);
		table.getColumnModel().getColumn(PRODUCT_IMAGE).setMinWidth(200);
		table.getColumnModel().getColumn(PRODUCT_CREATED_AT).setMinWidth(200);
	}

	private DefaultComboBoxModel<String> getModel(String tableName, String colum) {
		ResultSet resultSet = connectDatabse.getColum(tableName, colum);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

		try {

			while (resultSet.next()) {
				model.addElement(resultSet.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return model;
	}

	

}
