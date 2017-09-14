package other;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;

public class MyTable extends JTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean inLayout;
	private boolean isChangeRowHeight = false;
	
	public boolean isChangeRowHeight() {
		return isChangeRowHeight;
	}

	public void setChangeRowHeight(boolean isChangeRowHeight) {
		this.isChangeRowHeight = isChangeRowHeight;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {

		return false;
	}

	@Override
	public void setRowHeight(int arg0) {
		if(!isChangeRowHeight){
			arg0 = 30;
		}
		super.setRowHeight(arg0);
	}

	public MyTable() {
		super.tableHeader.setFont(new Font("Tahoma", Font.PLAIN, 15));
		super.setFont(new Font("Tahoma", Font.PLAIN, 15));
	}

	@Override
	public void columnMarginChanged(ChangeEvent arg0) {

		if (isEditing()) {
			removeEditor();

		}
		TableColumn resizingColums = getTableHeader().getResizingColumn();
		if (resizingColums != null && autoResizeMode == AUTO_RESIZE_OFF) {
			resizingColums.setPreferredWidth(resizingColums.getWidth());

		}
		resizeAndRepaint();
	}

	@Override
	public void doLayout() {
		if (hasExcessWidth()) {
			autoResizeMode = AUTO_RESIZE_SUBSEQUENT_COLUMNS;
		}

		setInLayout(true);
		autoResizeMode = AUTO_RESIZE_OFF;
		super.doLayout();
		setInLayout(false);
		autoResizeMode = AUTO_RESIZE_OFF;
	}

	protected boolean hasExcessWidth() {
		return getPreferredSize().width < getParent().getWidth();
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return hasExcessWidth();
	}

	public boolean isInLayout() {
		return inLayout;
	}

	public void setInLayout(boolean inLayout) {
		this.inLayout = inLayout;
	}
	

}
