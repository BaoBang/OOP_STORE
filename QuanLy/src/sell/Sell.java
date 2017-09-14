package sell;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import other.MyColor;
import other.MyTable;
import other.MyTableRender;
import ConnectDatabase.ConnectDatabse;
import interfaces.HomePage;
import model.Order;
import model.Product;
import model.User;
import other.TableButtonListenner;

public class Sell extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String column[] = { "Xóa", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Giảm Giá", "Thành Tiền" };
	Vector<String> tableTitle;
	Vector<Vector<String>> tableValue;
	Vector<Product> listProduct = new Vector<Product>();
	Vector<String> listIndex = new Vector<String>();
	JPanel pnCart, pnShowProduct, panelTextBill, panelTextCustomer, pnCardLayout;
	JLabel lbUser, lbPhone, lbAddress;
	JTextField txtUser, txtPhone, txtAddress;
			
	// -----Cart----------------------
	JTable tableCart;
	DefaultTableModel modelCart = new DefaultTableModel();

	JLabel lbTextPrice, lbPrice, lbTextShip, lbTextPay, lbPay;
	JTextField txtShip;

	JButton btnSave, btnCancle, btnCustomerInformation, btnBillInformation;

	MyTableRender.ButtonRender btnEditorDelete = new MyTableRender.ButtonRender("Xóa");

	MyTableRender.SpinnerColum editSpiner = new MyTableRender.SpinnerColum();

	// -----------------Show Product
	JComboBox<String> cbCategory;
	ArrayList<String> listCategoryName;
	JTextField txtSearch;

	
	//User infor
//	JPanel pnUserInfor;
//	JLabel lbUserName;
//	JButton btnLogout;

	JPanel pnProduct = new JPanel(new GridBagLayout());

	ConnectDatabse connectDatabse = new ConnectDatabse();

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User usr) {
		this.user = usr;
	}

	static int selectedIndex = -1;
	private static final int COLUM_SHOW_PRODUCT = 4;

	private static final int PRODUCT_ID = 1;
	private static final int PRODUCT_NAME = 2;
	private static final int PRODUCT_STOCK = 3;
	private static final int PRODUCT_PRICE = 4;
	private static final int PRODUCT_IMAGE = 5;
	private static final int PRODUCT_DISCOUNT = 6;

	private static final int TABLE_DELETE = 0;
	private static final int TABLE_NAME = 1;
	private static final int TABLE_STOCK = 2;
	private static final int TABLE_PRICE = 3;
	private static final int TABLE_DISCOUNT = 4;
	private static final int TABLE_AMOUNT = 5;

	public Sell() {
		//color
		setBackground(MyColor.BACK_GROUND);
		connectDatabse.connect();
		connectDatabse
				.setSqlCommand("select id, name, stock,price, image, discount from products where category_id = 36");
		user = HomePage.getUser();
		setLayout(new BorderLayout(20, 20));
		setBorder(new EmptyBorder(10, 30, 20, 30));
		loadTableCart();
		loadAllData();
		addControlls();
		addEvents();

	}

	private void addEvents() {

		btnEditorDelete.addTableButtonListener(new TableButtonListenner() {

			public void tableButtonClicked(int row, int col) {

				int answer = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa khỏi giỏ hàng không?", "Tin Nhắn Hệ Thống",
						JOptionPane.YES_NO_OPTION);

				if (answer == JOptionPane.YES_OPTION) {
					tableValue.remove(row);
					modelCart.setDataVector(tableValue, tableTitle);
					tableCart.setModel(modelCart);
					
					if(modelCart.getRowCount() <= 0){
						txtShip.setText("0");
					}
					
					
					tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellRenderer(editSpiner);
					tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellEditor(editSpiner);
					tableCart.getColumnModel().getColumn(TABLE_DELETE)
							.setCellRenderer(new MyTableRender.ButtonRender("Xóa"));
					tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellEditor(btnEditorDelete);
					tableCart.getColumnModel().getColumn(TABLE_NAME).setMinWidth(200);
					tableCart.getColumnModel().getColumn(TABLE_PRICE).setMinWidth(100);
					tableCart.getColumnModel().getColumn(TABLE_AMOUNT).setMinWidth(100);
					caculatedCart();
				}

			}
		});

		editSpiner.getEditSpinner().addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {

				modelCart = (DefaultTableModel) tableCart.getModel();
				int price = 0, discount = 0;
				try {
					tableCart.setRowSelectionInterval(editSpiner.getSelectedRows() , editSpiner.getSelectedRows() );
					price = Integer
							.parseInt(String.valueOf(modelCart.getValueAt(editSpiner.getSelectedRows(), TABLE_PRICE)));
					discount = Integer
							.parseInt(String.valueOf(modelCart.getValueAt(editSpiner.getSelectedRows(), TABLE_DISCOUNT)));
				} catch (Exception e2) {
				}
				
				int stock = Integer.parseInt(String.valueOf(editSpiner.getEditSpinner().getValue()));
				

				int amount = stock * price - (discount * stock);
				editSpiner.getEditSpinner().setValue(stock);
				try {
					modelCart.setValueAt(stock, tableCart.getSelectedRow(), TABLE_STOCK);
					modelCart.setValueAt(amount, tableCart.getSelectedRow(), TABLE_AMOUNT);
					Vector<String> vt = tableValue.get(editSpiner.getSelectedRows());
					vt.set(2, stock + "");
					tableValue.set(editSpiner.getSelectedRows(), vt);
				} catch (Exception e2) {
				}
				
				
				
				
				caculatedCart();

			}
		});

		cbCategory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == cbCategory) {
					// pnProduct = new JPanel(new GridBagLayout());
					doChangeShowData();
				}

			}
		});
		
		
		txtSearch.addCaretListener(new CaretListener() {

			public void caretUpdate(CaretEvent arg0) {
				doSearch();

			}
		});

		btnCancle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doClearCart();

			}
		});

		btnSave.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doSaveOrders();

			}
		});

		btnCustomerInformation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "CustomerInformation");

			}
		});

		btnBillInformation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "BillInformation");

			}
		});

//		txtShip.addCaretListener(new CaretListener() {
//			
//			public void caretUpdate(CaretEvent e) {
//				String strShip = txtShip.getText();
//				
//				if(strShip.compareTo("") != 0){
//					try {
//						int ship = Integer.parseInt(txtShip.getText());
//						int total = Integer.parseInt(lbPrice.getText());
//						lbPay.setText((total - ship) + "");
//					} catch (Exception e2) {
//						JOptionPane.showMessageDialog(null, "Phí Giao Hàng Không Hợp Lệ");
//						return;
//					}
//				}
//				
//			}
//		});
//	
//		btnLogout.addActionListener(new ActionListener() {
//			
//		
//			public void actionPerformed(ActionEvent arg0) {
//				System.exit(0);
//				
//			}
//		});
	}

	private void doSaveOrders() {
		int stock = 0;
		int id ;
		String total = lbPay.getText();
		String address = txtAddress.getText();
		String strShip = txtShip.getText();
		DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
		if(model.getRowCount() == 0){
			JOptionPane.showMessageDialog(null, "Giỏ Hàng Rỗng!!");
			return;
		}
	
		try {
			@SuppressWarnings("unused")
			int ship = Integer.parseInt(strShip);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Phí giao hàng không hợp lệ");
			return;
		}
		
		if(address.isEmpty()){
			JOptionPane.showMessageDialog(null, "Vui Lòng Nhập Địa Chỉ Giao Hàng!");
			return;
		}
		for(int i = 0; i < tableValue.size(); i++){
			stock = Integer.parseInt(String.valueOf(tableValue.get(i).get(2)));
			id = Integer.parseInt(String.valueOf(tableValue.get(i).get(0)));
			for(int j = 0; j < listProduct.size();j ++){
				
				if(id == listProduct.get(j).getId() && stock > listProduct.get(j).getStock()){
					JOptionPane.showMessageDialog(null, "Hiện tại cửa hàng không có sản phẩm" + tableValue.get(i).get(1));
					return;
				}
			}
		}
		
		Order order = new Order(user, Long.parseLong(total), address, 0);
		if(insert(order)){
			JOptionPane.showMessageDialog(null, "Đặt Hàng Thành Công!");
		
			doClearCart();
		}

	    
	    
	    

		
//		for(int i = 0; i < tableValue.size(); i++){
//			stock = Integer.parseInt(String.valueOf(tableValue.get(i).get(2)));
//			id = Integer.parseInt(String.valueOf(tableValue.get(i).get(0)));
//			for(int j = 0; j < listProduct.size();j ++){
//				if(id == listProduct.get(j).getId()){
//					listProduct.get(j).setStock(listProduct.get(j).getStock() - stock);
//					update(id, listProduct.get(j).getStock());
//				}
//			}
//		}
		
		
		
		
	}

	private boolean insert(Order order){
		int qty = 0;
		int product_id ;
	   	String sqlCommand = "INSERT INTO orders (user_id,total,address,created_at,status) VALUES (?,?,?,?,?)";
	    PreparedStatement preparedStatement;
		try {
			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, order.getUser().getId());
			preparedStatement.setString(2, order.getTotal() + "");
			preparedStatement.setString(3, order.getAddress());
			preparedStatement.setTimestamp(4, order.getCreatedAt());
			preparedStatement.setInt(5, order.getStatus());
		    preparedStatement.executeUpdate();
		    ResultSet rs = preparedStatement.getGeneratedKeys();
		    if (rs.next()) {
		            int order_id = rs.getInt(1);
		            for(int i = 0; i < tableValue.size(); i++){
		            	qty = Integer.parseInt(String.valueOf(tableValue.get(i).get(2)));
		    			product_id = Integer.parseInt(String.valueOf(tableValue.get(i).get(0)));
		    			ResultSet rs_product = connectDatabse.getDataId(product_id, "products");
		    			String sql = "INSERT INTO order_details (product_id,order_id,qty,price,discount) VALUES (?,?,?,?,?)";
		    		    PreparedStatement preparedSt;
		    		    preparedSt = ConnectDatabse.connection.prepareStatement(sql);
		    		    preparedSt.setInt(1, product_id);
		    		    preparedSt.setInt(2, order_id);
		    		    preparedSt.setInt(3, qty);
		    		    preparedSt.setInt(4, rs_product.getInt(6));
		    		    preparedSt.setInt(5, rs_product.getInt(7));
		    		    preparedSt.executeUpdate();
		    		}
		    }
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
//	private void update(int id, int stock) {
//		String sqlCommand = "update products set stock = ? where id = ?";
//		PreparedStatement preparedStatement = null;
//		try {
//			preparedStatement = ConnectDatabse.connection.prepareStatement(sqlCommand);
//			preparedStatement.setInt(1, stock);
//			preparedStatement.setInt(2, id);
//		
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	private void doClearCart() {

		listIndex = new Vector<String>();
		txtAddress.setText(null);
		txtPhone.setText(user.getPhone());
		txtUser.setText(user.getName());
		
		txtShip.setText("0");
	
		lbPay.setText("0");
		lbPrice.setText("0");

		tableValue = new Vector<Vector<String>>();
		tableCart.setModel(new DefaultTableModel(tableValue, tableTitle));

		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellRenderer(new MyTableRender.SpinnerColum());
		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellEditor(editSpiner);
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellRenderer(new MyTableRender.ButtonRender("Xóa"));
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellEditor(btnEditorDelete);
		tableCart.getColumnModel().getColumn(TABLE_NAME).setMinWidth(200);
		tableCart.getColumnModel().getColumn(TABLE_PRICE).setMinWidth(100);
		tableCart.getColumnModel().getColumn(TABLE_AMOUNT).setMinWidth(100);

	}

	private void doSearch() {

		String textSearch = txtSearch.getText();
		String category = listCategoryName.get(cbCategory.getSelectedIndex());
		String sql = "";
		int category_id = 0;

		try {
			category_id = connectDatabse.getDataName(category, "name", "categories").getInt(1);

			if (!textSearch.isEmpty()) {
				sql = "select id, name, stock, price, image, discount from products where category_id = " + category_id
						+ " and (name like '%" + textSearch + "%' or name = '" + textSearch + "' )";
			} else {
				sql = "select id, name, stock, price, image, discount from products where category_id = " + category_id;
			}

			connectDatabse.setSqlCommand(sql);
			pnProduct.removeAll();
			pnProduct.updateUI();
			loadAllData();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void doChangeShowData() {
		String category = listCategoryName.get(cbCategory.getSelectedIndex());
		String []brand_arr = category.split(" ");
		String brand = brand_arr[1];
		int category_id, brand_id = 0;
		ResultSet rs = null;

		try {
			category_id = connectDatabse.getDataName(category, "name", "categories").getInt(1);
			if((rs = connectDatabse.getDataName(brand, "name", "brands")) != null) brand_id = rs.getInt(1);
			String sql = "select id, name, stock, price, image, discount from products where category_id = "
					+ category_id + " or brand_id = " + brand_id +" limit 10";

			connectDatabse.setSqlCommand(sql);
			pnProduct.removeAll();
			pnProduct.updateUI();
			loadAllData();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void caculatedCart() {
		modelCart = (DefaultTableModel) tableCart.getModel();
		int row = modelCart.getRowCount();
		int total = 0;
		int ship = 0;
		try {
			ship = Integer.parseInt(txtShip.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Phí Không Hợp Lệ");
			return;
		}
		for (int i = 0; i < row; i++) {
			total += Integer.parseInt(String.valueOf(modelCart.getValueAt(i, TABLE_AMOUNT)));
		}
		lbPrice.setText(total + "");
		lbPay.setText((total - ship) + "");

	}

	private ArrayList<String> getColumName(String tableName, String colum) {
		ArrayList<String> listName = new ArrayList<String>();
		ResultSet resultSet = connectDatabse.getColum(tableName, colum);
		try {
			
			while (resultSet.next()) {
				listName.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void addControlls() {
		// User infor
//		pnUserInfor = new JPanel(new FlowLayout(2,10,10));
//		
//		lbUserName = new JLabel(user.getName());
//		lbUserName.setFont(new Font("Tahoma", Font.BOLD, 18));
//		lbUserName.setHorizontalAlignment(SwingConstants.RIGHT);
//		btnLogout = new JButton("Thoát");
//		pnUserInfor.add(lbUserName);
//		pnUserInfor.add(btnLogout);
//		add(pnUserInfor, BorderLayout.NORTH);
		
		// -------------Gi? H�ng---------------
		pnCart = new JPanel(new BorderLayout(20, 20));
		//color
		pnCart.setBackground(MyColor.BACK_GROUND);
		
		JPanel panelText = createTextCart();
		
		JScrollPane sc = new JScrollPane(tableCart);
		
		
		pnCart.add(sc);
		pnCart.add(panelText, BorderLayout.SOUTH);
		// -----show product--------

		pnShowProduct = new JPanel(new BorderLayout(20, 20));
		//color
		pnShowProduct.setBackground(MyColor.BACK_GROUND);
	
		JPanel pnSearch = new JPanel(new BorderLayout(20, 0));
	//	pnSearch.setBorder(new EmptyBorder(0, 0, 0, 10));
		pnSearch.setBackground(MyColor.BACK_GROUND);
		JPanel pnTextFieldSearch = new JPanel(new BorderLayout(20,0));
		JPanel pnComboboxSearch = new JPanel(new BorderLayout());
		//JPanel pnButtonSearch = new JPanel(new BorderLayout());

		pnTextFieldSearch.setBackground(MyColor.BACK_GROUND);
		
		txtSearch =  new JTextField();
		
		
		// combo box--------------//
		cbCategory = new JComboBox<String>();
		listCategoryName = getColumName("categories", "name");

		cbCategory.setModel(new DefaultComboBoxModel(listCategoryName.toArray()));
		cbCategory.setSelectedIndex(6);

		pnTextFieldSearch.add(txtSearch, BorderLayout.CENTER);
		
		JLabel lbSearch = new JLabel("Tìm kiếm");
		try{
			lbSearch.setIcon(new ImageIcon("./src/images/search.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		pnTextFieldSearch.add(lbSearch, BorderLayout.WEST);
		
		
		pnComboboxSearch.add(cbCategory, BorderLayout.CENTER);
		

		pnSearch.add(pnComboboxSearch, BorderLayout.WEST);
		pnSearch.add(pnTextFieldSearch, BorderLayout.CENTER);
		//pnSearch.add(pnButtonSearch, BorderLayout.EAST);
		// -----------------------------
		pnShowProduct.add(pnSearch, BorderLayout.NORTH);

		// pnProduct = cretedProduct();
		pnProduct.setBackground(Color.WHITE);
		JScrollPane scPro = new JScrollPane(pnProduct);
		scPro.getVerticalScrollBar().setUnitIncrement(50);// speed scroll
		pnShowProduct.add(scPro, BorderLayout.CENTER);

		add(pnCart, BorderLayout.WEST);
		add(pnShowProduct, BorderLayout.CENTER);

	}

	private void loadTableCart() {
		modelCart = new DefaultTableModel();
		tableCart = new MyTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int colum) {
				if (colum == TABLE_DELETE || colum == TABLE_STOCK)
					return true;
				return false;
			}

		};
		tableTitle = new Vector<String>();
		for (int i = 0; i < column.length; i++) {
			tableTitle.add(column[i]);

		}
		tableValue = new Vector<Vector<String>>();
		tableCart.setRowHeight(30);

		tableCart.setModel(new DefaultTableModel(tableValue, tableTitle));

		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellRenderer(new MyTableRender.SpinnerColum());
		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellEditor(editSpiner);
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellRenderer(new MyTableRender.ButtonRender("X�a"));
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellEditor(btnEditorDelete);
		tableCart.getColumnModel().getColumn(TABLE_NAME).setMinWidth(200);
		tableCart.getColumnModel().getColumn(TABLE_PRICE).setMinWidth(100);
		tableCart.getColumnModel().getColumn(TABLE_AMOUNT).setMinWidth(100);
	}

	private JPanel createTextCart() {

	
		JPanel panel = new JPanel(new BorderLayout(0, 20));
		//color
		panel.setBackground(MyColor.BACK_GROUND);
		// -------Text
		pnCardLayout = new JPanel(new CardLayout());

		panelTextCustomer = new JPanel(new GridLayout(3, 2, 0, 10));

		JPanel pnLableUser = new JPanel(new BorderLayout());
		JPanel pnTextFiledUser = new JPanel(new BorderLayout());
		
		
		lbUser = new JLabel("Khách Hàng");
		txtUser = new JTextField();
		txtUser.setText(user.getName());
		txtUser.setEditable(false);
		pnLableUser.add(lbUser, BorderLayout.CENTER);
		pnTextFiledUser.add(txtUser, BorderLayout.CENTER);
		panelTextCustomer.add(pnLableUser);
		panelTextCustomer.add(pnTextFiledUser);

		JPanel pnLablePhone = new JPanel(new BorderLayout());
		JPanel pnTextFiledPhone = new JPanel(new BorderLayout());
		lbPhone = new JLabel("Số ĐT");
		txtPhone = new JTextField();
		txtPhone.setText(user.getPhone());
		txtPhone.setEditable(false);
		pnLablePhone.add(lbPhone, BorderLayout.CENTER);
		pnTextFiledPhone.add(txtPhone, BorderLayout.CENTER);
		panelTextCustomer.add(pnLablePhone);
		panelTextCustomer.add(pnTextFiledPhone);

		JPanel pnLableAddress = new JPanel(new BorderLayout());
		JPanel pnTextFiledAddress = new JPanel(new BorderLayout());
		lbAddress = new JLabel("Địa Chỉ");
		txtAddress = new JTextField();
		pnLableAddress.add(lbAddress, BorderLayout.CENTER);
		pnTextFiledAddress.add(txtAddress, BorderLayout.CENTER);
		panelTextCustomer.add(pnLableAddress);
		panelTextCustomer.add(pnTextFiledAddress);

		panelTextBill = new JPanel(new GridLayout(3, 2, 0, 20));

		JPanel pnTextPrice = new JPanel(new BorderLayout());
		pnTextPrice.setBorder(new EmptyBorder(5, 10, 0, 0));
		JPanel pnPrice = new JPanel(new BorderLayout());
		pnPrice.setBorder(new EmptyBorder(5, 0, 0, 10));
		lbTextPrice = new JLabel("Tổng Tiền");
		lbPrice = new JLabel("0");
		lbPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		pnTextPrice.add(lbTextPrice, BorderLayout.CENTER);
		pnPrice.add(lbPrice, BorderLayout.CENTER);
		panelTextBill.add(pnTextPrice);
		panelTextBill.add(pnPrice);

		JPanel pnlTextDiscount = new JPanel(new BorderLayout());
		JPanel pnlDiscount = new JPanel(new BorderLayout());
		pnlTextDiscount.setBorder(new EmptyBorder(0, 10, 0, 0));
		pnlDiscount.setBorder(new EmptyBorder(0, 0, 0, 10));
		lbTextShip = new JLabel("Phí Giao Hàng");
		txtShip = new JTextField("0");
		txtShip.setEditable(false);
		txtShip.setHorizontalAlignment(SwingConstants.RIGHT);
		pnlTextDiscount.add(lbTextShip, BorderLayout.CENTER);
		pnlDiscount.add(txtShip, BorderLayout.CENTER);
		panelTextBill.add(pnlTextDiscount);
		panelTextBill.add(pnlDiscount);

		JPanel pnTextPay = new JPanel(new BorderLayout());
		JPanel pnPay = new JPanel(new BorderLayout());
		pnTextPay.setBorder(new EmptyBorder(0, 10, 5, 0));
		pnPay.setBorder(new EmptyBorder(0, 0, 5, 10));
		lbTextPay = new JLabel("Thanh Toán");
		lbPay = new JLabel("0");
		lbPay.setHorizontalAlignment(SwingConstants.RIGHT);
		pnTextPay.add(lbTextPay, BorderLayout.CENTER);
		pnPay.add(lbPay, BorderLayout.CENTER);
		panelTextBill.add(pnTextPay);
		panelTextBill.add(pnPay);

		pnCardLayout.add(panelTextBill, "BillInformation");
		pnCardLayout.add(panelTextCustomer, "CustomerInformation");

		CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
		cardLayout.show(pnCardLayout, "BillInformation");

		JPanel pnInfor = new JPanel(new GridLayout(2, 1, 20, 20));
		pnInfor.setBorder(new EmptyBorder(0, 0, 0, 20));
		//color
		pnInfor.setBackground(MyColor.BACK_GROUND);
		
		btnBillInformation = new JButton("TT Hóa Đơn");
		btnBillInformation.setBackground(MyColor.BUTTON_BACK_GROUND);
		btnBillInformation.setForeground(Color.BLACK);

		btnCustomerInformation = new JButton("TT Khách Hàng");
		btnCustomerInformation.setBackground(MyColor.BUTTON_BACK_GROUND);
		btnCustomerInformation.setForeground(Color.BLACK);

		pnInfor.add(btnBillInformation);
		pnInfor.add(btnCustomerInformation);

		panel.add(pnInfor, BorderLayout.WEST);
		// ---Button

		JPanel panelButton = new JPanel(new GridLayout(1, 2, 20, 0));
		panelButton.setBorder(new EmptyBorder(0, 200, 0, 0));
		panelButton.setBackground(MyColor.BACK_GROUND);
		
		btnSave = new JButton("Thanh Toán");
		btnSave.setBackground(MyColor.BUTTON_BACK_GROUND);
		btnSave.setForeground(Color.BLACK);

		btnCancle = new JButton("Xóa Giỏ Hàng");
		btnCancle.setBackground(MyColor.BUTTON_BACK_GROUND);
		btnCancle.setForeground(Color.BLACK);

		panelButton.add(btnSave);
		panelButton.add(btnCancle);

		panel.add(pnCardLayout, BorderLayout.CENTER);
		panel.add(panelButton, BorderLayout.SOUTH);

		return panel;
	}

	private void loadAllData() {

		ResultSet resultSet = connectDatabse.getData();
		listProduct = new Vector<Product>();
		try {
			if (resultSet != null) {
				int index = 0;
				GridBagConstraints bagConstraints = new GridBagConstraints();
				bagConstraints.ipadx = 55;
				bagConstraints.ipady = 50;
				while (resultSet.next()) {
					int id = resultSet.getInt(PRODUCT_ID);
					String name = resultSet.getString(PRODUCT_NAME);
					int stock = resultSet.getInt(PRODUCT_STOCK);
					int price = resultSet.getInt(PRODUCT_PRICE);
					String image = resultSet.getString(PRODUCT_IMAGE);
					int discount = resultSet.getInt(PRODUCT_DISCOUNT);

					bagConstraints.gridx = index % COLUM_SHOW_PRODUCT;
					bagConstraints.gridy = index / COLUM_SHOW_PRODUCT;

					final Product product = new Product(id, name, stock, price, image, discount);
					PanelPickItem pn = new PanelPickItem(name, price + "", image);
					pn.btnAddCart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {

							doAddProductIntoCart(product);
							
							caculatedCart();
						}
					});
					listProduct.add(product);
					pnProduct.add(pn, bagConstraints);
					index++;
				}
			}
			

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	private void doAddProductIntoCart(Product product) {
		modelCart = (DefaultTableModel) tableCart.getModel();
		int row = modelCart.getRowCount();
				for (int i = 0; i < row; i++) {

			int id = Integer.parseInt(String.valueOf(modelCart.getValueAt(i, TABLE_DELETE)));
		
			if (id == product.getId()) {
				JOptionPane.showMessageDialog(null, "Sản Phẩm đã có trong giỏ Hàng!!");
				return;
			}
		}
		Vector<String> arr = new Vector<String>();

		arr.add(product.getId() + "");
		arr.add(product.getName());
		arr.add("1");
		arr.add(product.getPrice() + "");
		arr.add(product.getDiscount() + "");
		arr.add(product.getPrice() + "");
		tableValue.add(arr);
		
		
		txtShip.setText("50000");
				
		tableCart.setModel(new DefaultTableModel(tableValue, tableTitle));
		tableCart.setRowSelectionInterval(tableValue.size() -1, tableValue.size() -1);
		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellRenderer(new MyTableRender.SpinnerColum());
		tableCart.getColumnModel().getColumn(TABLE_STOCK).setCellEditor(editSpiner);
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellRenderer(new MyTableRender.ButtonRender("Xóa"));
		tableCart.getColumnModel().getColumn(TABLE_DELETE).setCellEditor(btnEditorDelete);
		tableCart.getColumnModel().getColumn(TABLE_NAME).setMinWidth(200);
		tableCart.getColumnModel().getColumn(TABLE_PRICE).setMinWidth(100);
		tableCart.getColumnModel().getColumn(TABLE_AMOUNT).setMinWidth(100);

	}

}
