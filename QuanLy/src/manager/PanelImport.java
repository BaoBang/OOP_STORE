package manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import ConnectDatabase.ConnectDatabse;
import interfaces.HomePage;
import model.User;
import other.MyColor;

abstract class PanelImport extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnAdd, btnUpdate, btnDelete, btnDetail;
	JPanel pnButton, pnTitle, pn = new JPanel(new BorderLayout()), pnCardLayout;
	JTextField txtSearch;
	JLabel lbTitle;
	User user;
	ConnectDatabse connectDatabse = new ConnectDatabse();
	
	public PanelImport(String title) {
		setLayout(new BorderLayout());
		connectDatabse.connect();
		user = HomePage.getUser();
		pnButton = createPanelForm();
		pnTitle = createPanelTitle(title);
		pnTitle.setBackground(Color.white);
		pn.add(pnTitle, BorderLayout.NORTH);
		pn.add(pnButton, BorderLayout.SOUTH);
		add(pn, BorderLayout.NORTH);
		addEvents();

	}

	private JPanel createPanelForm() {
		JPanel panel = new JPanel(new BorderLayout());
		
		btnAdd = new JButton();
	
		btnUpdate = new JButton();
	
		btnDelete = new JButton();
		
		btnDetail = new JButton();
		
		btnDetail.setVisible(false);

		try {
			btnAdd.setIcon(new ImageIcon(getClass().getResource(
					"/images/add.png")));
		} catch (NullPointerException e) {
			btnAdd.setText("Thêm");
			e.printStackTrace();
		}
		try {

			btnUpdate.setIcon(new ImageIcon(getClass().getResource(
					"/images/update.png")));

		} catch (NullPointerException e) {
			btnUpdate.setText("Sửa");
			e.printStackTrace();
		}
		try {

			btnDelete.setIcon(new ImageIcon(getClass().getResource(
					"/images/delete.png")));

		} catch (NullPointerException e) {
			btnDelete.setText("Xóa");
			e.printStackTrace();
		}
		try {

			btnDetail.setIcon(new ImageIcon(getClass().getResource(
					"/images/detail.png")));
		} catch (NullPointerException e) {
			btnDetail.setText("Chi Tiết");
			e.printStackTrace();
		}

		JPanel pnControll = new JPanel(new GridLayout(1, 2));

		JPanel pnSearch = new JPanel(new BorderLayout(20,0));
		pnSearch.setBorder(new EmptyBorder(30, 0, 30, 100));
		pnSearch.setBackground(MyColor.BACK_GROUND);
		txtSearch = new JTextField();
		//txtSearch.setFont(new Font("Times New Roman",Font.BOLD,15));
		pnSearch.add(txtSearch);

		JLabel lbSearch = new JLabel("Tìm kiếm");
		try{
			lbSearch.setIcon(new ImageIcon("./src/images/search.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		pnSearch.add(lbSearch, BorderLayout.WEST);
		
		
		JPanel pnButton = new JPanel(new GridLayout(1, 3, 70, 0));
		pnButton.setBackground(MyColor.BACK_GROUND);
		pnButton.setBorder(new EmptyBorder(20, 0, 20, 0));
		
		pnCardLayout = new JPanel(new CardLayout());
		pnCardLayout.add(btnAdd, "ButtonAdd");
		pnCardLayout.add(btnDetail, "ButtonDetail");
		
		pnButton.add(pnCardLayout);
		pnButton.add(btnUpdate);
		pnButton.add(btnDelete);

		CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
		cardLayout.show(pnCardLayout, "ButtonAdd");
		
		pnControll.add(pnSearch);
		pnControll.add(pnButton);

		panel.add(pnControll, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPanelTitle(String title) {
		JPanel panel = new JPanel(new FlowLayout());

		panel.setBorder(BorderFactory.createBevelBorder(1));
		lbTitle = new JLabel(title);
		lbTitle.setForeground(Color.red);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		panel.add(lbTitle);

		return panel;
	}

	private void addEvents() {
		
		if(user.getLevel() <= 1){
			pnCardLayout.setVisible(false);
			btnDelete.setVisible(false);
			btnUpdate.setVisible(false);
			
		}
		
		btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doAdd();

			}
		});
		btnUpdate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doUpdate();

			}
		});
		btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doDelete();

			}
		});
		btnDetail.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				doDetail();

			}
		});
		txtSearch.addCaretListener(new CaretListener() {
			
			public void caretUpdate(CaretEvent arg0) {
				doSearch();
				
			}
		});

	}

	public abstract void doAdd();
	
	public abstract void doDelete();

	public abstract void doUpdate();
	
	public abstract void doDetail();
	
	public abstract void doSearch();

	public abstract void loadDataIntoTable(ResultSet resultSet);
}
