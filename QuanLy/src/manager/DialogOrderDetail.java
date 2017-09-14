package manager;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import other.MyTable;

public class DialogOrderDetail extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel lbName, lbPhone, lbAddress, lbCreatedAt, lbTotal, lbStatus, lbOrderID;
	JTextField txtName, txtPhone, txtAddress, txtCreatedAt, txtStatus, txtTotal, txtOrderID;
	JTable tbOrderDetail = new MyTable();

	JPanel pnCenter, pnText, pnTable;

	String[] colum_detail = { "STT", "Tên Sản Phẩm ", "Số Lượng", "Đơn Giá", "Giảm Giá" };

	public DialogOrderDetail() {
		setTitle("Chi Tiết Hóa Đơn");
		createdTable();
		addControlls();
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	void createdTable() {
		DefaultTableModel model_detail = new DefaultTableModel();
		model_detail.setColumnIdentifiers(colum_detail);
		//

		//
		tbOrderDetail.setModel(model_detail);

	}

	private void addControlls() {
		pnCenter = new JPanel(new BorderLayout(20, 20));
		pnCenter.setBorder(new EmptyBorder(20, 20, 20, 20));

		lbName = new JLabel("Khách Hàng");
		lbAddress = new JLabel("Địa Chỉ");
		lbPhone = new JLabel("Số Điện Thoại");
		lbCreatedAt = new JLabel("Thời Gian");
		lbTotal = new JLabel("Tổng Tiền");
		lbStatus = new JLabel("Tình Trạng Đơn Hàng");
		lbOrderID = new JLabel("Số Hóa Đơn");

		txtName = new JTextField();
		txtAddress = new JTextField();
		txtPhone = new JTextField();
		txtCreatedAt = new JTextField();
		txtTotal = new JTextField();
		txtStatus = new JTextField();
		txtOrderID = new JTextField();

		pnText = new JPanel(new GridLayout(4, 4, 10, 15));
		pnText.add(lbName);
		pnText.add(txtName);
		pnText.add(lbPhone);
		pnText.add(txtPhone);
		pnText.add(lbAddress);
		pnText.add(txtAddress);
		pnText.add(lbCreatedAt);
		pnText.add(txtCreatedAt);
		pnText.add(lbTotal);
		pnText.add(txtTotal);
		pnText.add(lbStatus);
		pnText.add(txtStatus);
		pnText.add(lbOrderID);
		pnText.add(txtOrderID);

		txtName.setEditable(false);
		txtAddress.setEditable(false);
		txtPhone.setEditable(false);
		txtCreatedAt.setEditable(false);
		txtTotal.setEditable(false);
		txtStatus.setEditable(false);
		txtOrderID.setEditable(false);

		pnTable = new JPanel();
		pnTable.setLayout(new BorderLayout());
		pnTable.add(new JScrollPane(tbOrderDetail));

		pnCenter.add(pnText, BorderLayout.NORTH);
		pnCenter.add(pnTable, BorderLayout.CENTER);

		add(pnCenter);

	}

	public static void main(String[] args) {
		new DialogOrderDetail();
	}

}
