package gui;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import engine.Action;
import engine.Bot;

/**
 * @author thibdev
 */
public class TableAction extends JTable {
	
	private Bot limb;

	public TableAction(Bot b, TableModelAction model) {
		super(model);
		limb = b;
	}

	public TableAction(Bot b, TableModelAction model, TableColumnModel cmodel) {
		super(model, cmodel);
		limb = b;
	}
	
	public void executeAction(int niter) {
		int[] rows = getSelectedRows();
		Action[] actions = new Action[rows.length];
		int idx = 0;
		for (int row : rows) {
			actions[idx++] = ((TableModelAction)getModel()).getAction(row);
		}
		for(int i=0; i<niter; i++){
			limb.execute(actions);
			limb.delay(250);
		}
	}

	public void deleteAction() {
		int[] rows = getSelectedRows();
		for (int i=rows.length-1; i>=0; i--) {
			((TableModelAction)getModel()).deleteAction(rows[i]);
		}
	}

}
