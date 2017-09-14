package other;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import model.Product;

public class ProductRender extends JPanel implements ListCellRenderer<Product>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbIcon = new JLabel();
	JLabel lbName = new JLabel();
	JLabel lbPrice = new JLabel();
	JPanel pnIcon = new JPanel();
	
	public ProductRender(){
		setLayout(new BorderLayout(5,5));
		JPanel pnText = new JPanel(new GridLayout(0,1));
		pnText.add(lbName);
		pnText.add(lbPrice);
		
		
		pnIcon.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnIcon.add(lbIcon);
		add(pnIcon, BorderLayout.WEST);
		add(pnText, BorderLayout.CENTER);
	}
	
	public Component getListCellRendererComponent(
			JList<? extends Product> list, Product product, int index,
			boolean isSelected, boolean cellHasFocus) {
		try{
			lbIcon.setIcon(new ImageIcon(getClass().getResource("/images/" + product.getImage())));
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
		lbName.setText(product.getName());
		lbPrice.setText(product.getPrice() +"");
		lbPrice.setForeground(Color.blue);
		// set Opaque to change background color of JLabel
		lbName.setOpaque(true);
		lbPrice.setOpaque(true);
		pnIcon.setOpaque(true);
		//when item selected
		if(isSelected){
			lbName.setBackground(list.getSelectionBackground());
			lbPrice.setBackground(list.getSelectionBackground());
			pnIcon.setBackground(list.getSelectionBackground());
			setBackground(list.getSelectionBackground());
		}else{
			lbName.setBackground(list.getBackground());
			lbPrice.setBackground(list.getBackground());
			pnIcon.setBackground(list.getBackground());
			setBackground(list.getBackground());
		}
		
		return this;
	}
	
	
}
