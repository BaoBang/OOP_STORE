package other;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MyTableRender {

	private final static int MAX = Integer.MAX_VALUE;
	private final static int MIN = 1;

	public static class AlignRender extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setHorizontalAlignment(SwingConstants.CENTER);
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		}

	}

	public static class SpinnerColum extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JSpinner renderSpinner;
		private JSpinner editSpinner;
		private int selectedRow = -1;

		public void setSelectedRows(int row) {
			selectedRow = row;
		}

		public int getSelectedRows() {
			return selectedRow;
		}

		public JSpinner getEditSpinner() {
			return editSpinner;
		}

		public SpinnerColum() {
			editSpinner = new JSpinner();
			SpinnerModel model = new SpinnerNumberModel(1, MIN, MAX, 1);
			editSpinner.setModel(model);
			renderSpinner = new JSpinner();
			renderSpinner.setModel(model);

		}

		public Object getCellEditorValue() {
			try {
				editSpinner.commitEdit();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return editSpinner.getValue();
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (value == null) {
				renderSpinner.setValue(0);
			} else {
				renderSpinner.setValue(Integer.parseInt(value.toString()));
			}

			JPanel pn = new JPanel(new BorderLayout());
			pn.add(renderSpinner);
			pn.setBorder(new EmptyBorder(5, 5, 5, 5));
			if (isSelected) {
				pn.setBackground(table.getSelectionBackground());
				renderSpinner.setBackground(table.getSelectionBackground());
			} else {
				pn.setBackground(table.getBackground());
				renderSpinner.setBackground(table.getBackground());
			}

			return pn;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			selectedRow = row;

			if (value == null) {
				editSpinner.setValue(0);
			} else {

				editSpinner.setValue(Integer.parseInt(String.valueOf(value)));
			}
			JPanel pn = new JPanel(new BorderLayout());
			pn.add(editSpinner);
			pn.setBorder(new EmptyBorder(5, 5, 5, 5));

			if (isSelected) {
				pn.setBackground(table.getSelectionBackground());
				editSpinner.setBackground(table.getSelectionBackground());
			} else {
				pn.setBackground(table.getBackground());
				editSpinner.setBackground(table.getBackground());
			}
			return pn;
		}

	}

	public static class ButtonRender extends JButton implements TableCellEditor, TableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int selectedRow, selectedColumn;
		Vector<TableButtonListenner> listener;

		public ButtonRender(String tilte) {
			super(tilte);
			listener = new Vector<TableButtonListenner>();
			addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					for (TableButtonListenner buttonListenner : listener) {
						buttonListenner.tableButtonClicked(selectedRow, selectedColumn);
					}

				}
			});
		}

		public void addTableButtonListener(TableButtonListenner buttonListenner) {
			listener.add(buttonListenner);
		}

		public void removeTableButtomListener(TableButtonListenner buttonListenner) {
			listener.remove(buttonListenner);
		}

		public void addCellEditorListener(CellEditorListener l) {

		}

		public void cancelCellEditing() {

		}

		public Object getCellEditorValue() {

			return this;
		}

		public boolean isCellEditable(EventObject anEvent) {

			return true;
		}

		public void removeCellEditorListener(CellEditorListener l) {

		}

		public boolean shouldSelectCell(EventObject anEvent) {

			return true;
		}

		public boolean stopCellEditing() {

			return true;
		}

		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			JPanel pn = new JPanel(new BorderLayout());
			pn.setBorder(new EmptyBorder(5, 5, 5, 5));
			pn.add(this);
			if (arg2) {
				pn.setBackground(arg0.getSelectionBackground());
				this.setBackground(arg0.getSelectionBackground());
			} else {
				pn.setBackground(arg0.getBackground());
				this.setBackground(arg0.getBackground());
			}
			return pn;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			selectedRow = row;
			table.setRowSelectionInterval(row, row);
			selectedColumn = column;
			JPanel pn = new JPanel(new BorderLayout());
			pn.setBorder(new EmptyBorder(5, 5, 5, 5));
			if (isSelected) {
				pn.setBackground(table.getSelectionBackground());
				this.setBackground(table.getSelectionBackground());
			} else {
				pn.setBackground(table.getBackground());
				this.setBackground(table.getBackground());
			}

			pn.add(this);
			return pn;
		}

	}
	
	public static class ImageRender extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			JLabel lb = new JLabel();
			lb.setOpaque(true);
			ImageIcon imageIcon = null;
			Image image = null;
			Image newImage = null;
			try {
				imageIcon = new ImageIcon("./src/images/" + value.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				image = imageIcon.getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				newImage = image.getScaledInstance(100, 40, Image.SCALE_SMOOTH);
			} catch (Exception e) {
				e.printStackTrace();
			}

			lb.setHorizontalAlignment(SwingConstants.CENTER);
			if (column == 2) {
				try {
					lb.setIcon(new ImageIcon(newImage));
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				lb.setText(value.toString());
			}
			if (isSelected) {
				lb.setBackground(table.getSelectionBackground());

			} else {
				lb.setBackground(table.getBackground());
			}
			return lb;
		}

	}

}
