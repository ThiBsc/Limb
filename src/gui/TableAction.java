package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import engine.Action;
import engine.Bot;

public class TableAction extends JTable implements MouseListener {
	
	private Bot limb;

	public TableAction(Bot b, TableModelAction model) {
		super(model);
		limb = b;
		addMouseListener(this);
	}

	public TableAction(Bot b, TableModelAction model, TableColumnModel cmodel) {
		super(model, cmodel);
		limb = b;
		addMouseListener(this);
	}
	
	private void executeAction() {
		int[] rows = getSelectedRows();
		Action[] actions = new Action[rows.length];
		int idx = 0;
		for (int row : rows) {
			actions[idx++] = ((TableModelAction)getModel()).getAction(row);
		}
		limb.execute(actions);
	}

	private void deleteAction() {
		int row = getSelectedRow();
		if (row != -1) {
			((TableModelAction)getModel()).deleteAction(row);;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (e.getClickCount() == 2) {
				deleteAction();
			} else {
				executeAction();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
