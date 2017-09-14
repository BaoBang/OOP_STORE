package other;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Product;

public class JListCustomRenderer extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<Product> listProduct;
	
	public JListCustomRenderer(){
		setLayout(new BorderLayout());
		add(new  JScrollPane(setListProduct(createListProduct())), BorderLayout.CENTER);
		
	}
	
	private JList<Product> createListProduct(){
		DefaultListModel<Product> model = new DefaultListModel<Product>();
//		model.addElement(new Product("Camera Camera Camera Camera Camera", 12000,"camera.jpg"));
//		model.addElement(new Product("Computer", 12000,"computer.jpg"));
//		model.addElement(new Product("Camera", 12000,"camera.jpg"));
		
		
		JList<Product> list = new JList<Product>(model);
		list.setCellRenderer(new ProductRender());
		
		return list;
	}

	public JList<Product> getListProduct() {
		return listProduct;
	}

	public JList<Product> setListProduct(JList<Product> listProduct) {
		this.listProduct = listProduct;
		return listProduct;
	}
}
