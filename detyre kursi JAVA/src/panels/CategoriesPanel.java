package panels;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class CategoriesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public CategoriesPanel(){//JPanel i modifikuar qe shfaq nje JTable me kolone Kategorite dhe rreshta kategorite e ndryshme
		String[] text = {"Njesha", "Dysha", "Tresha", 
				"Katra", "Pesa", "Gjashta", "Piket e Siperme", 
				"Bonus (35)", "Tre me nje vlere", "Kater me nje vlere",
				"Tre dhe Dy (25)", "Kater te njepasnjeshme (30)", "Pese te njepasnjeshme (40)",
				"E njejta vlere (50)", "Çdo rast", "Piket e Poshtme", "TOTAL"};
		      
		DefaultTableModel model = new DefaultTableModel();
		JTable table = new JTable(model) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {                
                return false;
	        }//ndalon ndryshimin e vleres se qelizes nga perdoruesi
		};

		model.addColumn("Kategorite");
		for(int i=0;i<text.length;i++) {
			model.addRow(new Object[]{text[i]});
		}
		
		table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				table.getTableHeader().setBackground(Color.BLACK);//vendos ngjyren e background-it te headerit ("Kategorite") te zeze
				table.getTableHeader().setOpaque(false);//e vendos header transperant
				table.getTableHeader().setForeground(Color.WHITE);//vendos ngjyren e tekstit ne header te bardhe
				setBackground(new Color(245,245,245));//vendos ngjyren e qelizave te si nje gri te lehte
				setText((String)value);//vendos vleren cfaredo vlere ka pasur origjinalisht
				return this;//kthen qelizen qe po render ne moment
			}//ndryshon getTableCellRendererComponent te DefaultTableCellRenderer qe perdoret si CellRenderer per kolonen
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(225);//percakton gjeresine e kolones
		table.setRowHeight(25);//percakton gjatesine e rreshtit
		
		table.getTableHeader().setReorderingAllowed(false);//ndalon levizjen e tabeles
		table.setFocusable(false);//ndalon selektimin e tabeles nga perdoruesi
		table.setRowSelectionAllowed(false);//ndalon selektimin e rreshtave nga perdoruesi

		setLayout(new BorderLayout());
		add(table, BorderLayout.CENTER);
		add(table.getTableHeader(), BorderLayout.NORTH);
	}
}
