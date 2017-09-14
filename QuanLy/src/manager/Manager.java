package manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import other.MyColor;
public class Manager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton btnProduct, btnCategory, btnBrand, btnOrder, btnUser;
	JPanel pnControll, pnButtonControll, pnCardLayout;

	JPanel pnProduct, pnCategory, pnBrand;
	public JPanel pnOrder, pnUser;

	public PanelOrder getPanelOrder(){
		return (PanelOrder) pnOrder;
	}
	public Manager() {
		setLayout(new BorderLayout(50, 20));
		setBorder(new EmptyBorder(50,50,50,50));
		setBackground(MyColor.BACK_GROUND);
		addControlls();
		addEvents();
	}

	private void addEvents() {

		Component[] pn = pnCardLayout.getComponents();
//		for(int i = 0; i < pn.length; i++){
//			System.out.println(pn[i].toString());
//		}
		
		btnProduct.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "panelProduct");
				
				PanelProduct pn = (PanelProduct)pnCardLayout.getComponent(2);
				pn.loadDataIntoTable(pn.connectDatabse.getData());
			}
		});

		btnCategory.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "panelCategory");
				
				PanelCategory pn = (PanelCategory)pnCardLayout.getComponent(1);
				pn.loadDataIntoTable(pn.connectDatabse.getData());

			}
		});
		btnOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "panelOrder");
				
				PanelOrder pn = (PanelOrder)pnCardLayout.getComponent(4);
				pn.loadDataIntoTable(pn.connectDatabse.getData());

			}
		});
		btnBrand.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "panelBrand");
				
				PanelBrand pn = (PanelBrand)pnCardLayout.getComponent(3);
				pn.loadDataIntoTable(pn.connectDatabse.getData());

			}
		});
		btnUser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
				cardLayout.show(pnCardLayout, "panelUser");
				
				PanelUser pn = (PanelUser)pnCardLayout.getComponent(5);
			
				pn.loadDataIntoTable(pn.connectDatabse.getData());

			}
		});

	}

	private void addControlls() {
		pnControll = new JPanel(new BorderLayout());
		
		pnButtonControll = createPanelButtonControlls();
		pnButtonControll.setBackground(MyColor.BACK_GROUND);
		pnControll.add(pnButtonControll, BorderLayout.CENTER);
		
		
		pnCardLayout = new JPanel(new CardLayout());

		pnProduct = new PanelProduct();
		pnCategory = new PanelCategory();
		pnBrand = new PanelBrand();
		pnUser = new PanelUser();
		pnOrder = new PanelOrder();
		

		pnCardLayout.add(new JPanel());
		pnCardLayout.add(pnCategory, "panelCategory");
		pnCardLayout.add(pnProduct, "panelProduct");
		pnCardLayout.add(pnBrand, "panelBrand");
		pnCardLayout.add(pnOrder, "panelOrder");
		pnCardLayout.add(pnUser, "panelUser");
		
		CardLayout cardLayout = (CardLayout) pnCardLayout.getLayout();
		cardLayout.show(pnCardLayout, "panelCategory");

		add(pnCardLayout, BorderLayout.CENTER);
		add(pnControll, BorderLayout.WEST);

	}

	private JPanel createPanelButtonControlls() {
		JPanel panel = new JPanel(new GridLayout(5, 1, 0, 30));
		panel.setBorder(new EmptyBorder(130, 0, 50, 0));
		
		btnCategory = new JButton("Loại Sản Phẩm");
		btnCategory.setBackground(MyColor.BUTTON_BACK_GROUND);
		
		btnCategory.setFont(new Font("Arial", Font.BOLD, 18));
		
		btnBrand = new JButton("Hãng Sản Xuất");
		btnBrand.setBackground(MyColor.BUTTON_BACK_GROUND);
	
		btnBrand.setFont(new Font("Arial", Font.BOLD, 18));
		
		btnProduct = new JButton("Sản Phẩm");
		btnProduct.setBackground(MyColor.BUTTON_BACK_GROUND);
		
		btnProduct.setFont(new Font("Arial", Font.BOLD, 18));
		
		btnOrder = new JButton("Đơn Hàng");
		btnOrder.setBackground(MyColor.BUTTON_BACK_GROUND);
		
		btnOrder.setFont(new Font("Arial", Font.BOLD, 18));
		
		btnUser = new JButton("Người Dùng");
		btnUser.setBackground(MyColor.BUTTON_BACK_GROUND);
	
		btnUser.setFont(new Font("Arial", Font.BOLD, 18));
		
		panel.add(btnCategory);
		panel.add(btnBrand);
		panel.add(btnProduct);
		panel.add(btnOrder);
		panel.add(btnUser);
		return panel;
	}

	
}
