package panels;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;

public class PlayerPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public PlayerPanel(String name, int i){//panel per lojetaret, tabel me header emrin e tyre dhe rreshta vlerat e kategorive
		
		PlayerTableModel model = new PlayerTableModel();
		model.addColumn(name);
		
		JTable table = new JTable(model);
		table.setName(""+i);//vendos emrin e tabeles, indeksin e lojetarit (perdoret nga PlayerTableRenderer)
		PlayerTableRenderer tRenderer = new PlayerTableRenderer();
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setCellRenderer(tRenderer);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.setRowHeight(25);
		setLayout(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
	}
}
