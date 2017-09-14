package interfaces;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import ConnectDatabase.ConnectDatabse;
import login.Login;
import manager.Manager;
import manager.PanelOrder;
import sell.Sell;
import model.User;

public class HomePage extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTabbedPane tabbedPane;
	Sell sell;
	Manager depot;
	JMenuBar mnBar;
	JMenu mnFile, mnAccount;
	JMenuItem mniLogout, mniEditInfor, mniEditAcount, mniOut, mniHistory;
	public static ConnectDatabse connectDatabse = new  ConnectDatabse();
	private static User user = new User();
	
	public static User getUser() {
		return user;
	}

	public void setUser(User usr) {
		HomePage.user = usr;
	}

	public HomePage(User usr){
		user = usr;
		connectDatabse.connect();
		setLayout(new BorderLayout(10,10));
		setSize(1300, 700);
		setLocationRelativeTo(null);
		addControlls();
		addEvents();
	}

	private void addEvents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
					PanelOrder panel = depot.getPanelOrder();
					panel.loadDataIntoTable(panel.getResultSet());
					
				
				
			}
		});
		
		mniEditInfor.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new DialogEditInforUser("Đổi Thông Tin Cá Nhân", user); 
				
			}
		});
		mniEditAcount.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new DialogEditAcount("Đổi Mật Khẩu", user);
				
			}
		});
		mniLogout.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Login();
				
			}
		});
		mniOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		mniHistory.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new DialogHistory("Lịch Sử Mua Hàng", user);
				
			}
		});
	}

	private void addControlls() {
		tabbedPane = new JTabbedPane();
				
		sell = new Sell();
		ImageIcon iconSell = new ImageIcon(getClass().getResource("/images/sell.png"));
		iconSell.setImage(iconSell.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		tabbedPane.addTab("Mua Hàng",iconSell, sell, "Mua Hàng");
		
		if(user.getLevel() == 2 || user.getLevel() == 1 ){
			depot = new Manager();
			ImageIcon iconManager = new ImageIcon(getClass().getResource("/images/home.png"));
			iconManager.setImage(iconManager.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
			tabbedPane.addTab("Quản Lý",iconManager, depot, "Quản Lý");
		}
		
		add(tabbedPane, BorderLayout.CENTER);
		
		
		//
		mnBar = new JMenuBar();
		
		mnFile = new JMenu("Hệ Thống");
		mniLogout = new JMenuItem("Đăng Xuất");
		mnFile.add(mniLogout);
		mniOut = new JMenuItem("Thoát");
		mnFile.add(mniOut);
		
		mnAccount = new JMenu("Tài Khoản");
		mniEditAcount = new JMenuItem("Đổi mật khẩu");
		mniEditInfor = new JMenuItem("Thay đổi thông tin cá nhân");
		mniHistory = new JMenuItem("Lịch Sử Mua Hàng");
		mnAccount.add(mniHistory);
		mnAccount.add(mniEditAcount);
		mnAccount.add(mniEditInfor);
		
		
		
		
		JMenu mnHelp = new JMenu("Trợ Giúp");
		JMenuItem mniAbout = new JMenuItem("Thông Tin Cửa Hàng");
		mnHelp.add(mniAbout);
		
		mnBar.add(mnAccount);
		mnBar.add(mnFile);
		mnBar.add(mnHelp);
		setJMenuBar(mnBar);
		
		
	}
}
