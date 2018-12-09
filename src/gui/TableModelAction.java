package gui;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import engine.Action;

public class TableModelAction extends AbstractTableModel {
	
	private ArrayList<Action> actionList;

	public TableModelAction() {
		actionList = new ArrayList<Action>();
	}
	
	public void addAction(Action a) {
		actionList.add(a);
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}
	
	public void deleteAction(int row) {
		actionList.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public Action getAction(int row) {
		return actionList.get(row);
	}

	@Override
	public int getColumnCount() {
		// Selection pos(x,y)|action
		return 2;
	}

	@Override
	public int getRowCount() {
		return actionList.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Object ret;
		Action a = actionList.get(row);
		switch (col) {
		case 0:
			ret = String.format("(%.0f, %.0f)", a.getPos().getX(), a.getPos().getY());
			break;
		case 1:
			ret = a.getAction();
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}
	
	@Override
	public String getColumnName(int column) {
		String ret;
		switch (column) {
		case 0:
			ret = "Position";
			break;
		case 1:
			ret = "Action";
			break;
		default:
			ret = "";
			break;
		}
		return ret;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String val = aValue.toString();
		actionList.get(rowIndex).setAction(val);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

}
