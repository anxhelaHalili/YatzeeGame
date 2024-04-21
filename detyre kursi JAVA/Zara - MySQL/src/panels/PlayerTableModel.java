package panels;

import javax.swing.table.DefaultTableModel;
import calculations.DiceGenerator;
import calculations.Points;;

public class PlayerTableModel extends DefaultTableModel {//model tabele i modifikuar
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}//qelizat nuk jane te modifkueshme
	
	@Override
	public int getRowCount() {
		return 17;
	}//tabela duhet te kete 17 rreshta

	@Override
	public int getColumnCount() {
		return 1;
	}//tabela duhet te kete 1 kolone

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(rowIndex) {
			//1-6
			case 0: 
			case 1:
			case 2:
			case 3:
			case 4:
			case 5: return Points.addValueDice(DiceGenerator.diceNumbers, rowIndex+1);
			
			//case 6: above points
			//PlayerTableRenderer
			//case 7: bonus
			//PlayerTableRenderer
				
			//threeofakind
			case 8: return Points.threeofakind(DiceGenerator.diceNumbers);
			//fourofakind
			case 9: return Points.fourofakind(DiceGenerator.diceNumbers);
			//3and2
			case 10: return Points.threeandtwo(DiceGenerator.diceNumbers);
			//4 sequence
			case 11: return Points.fourSequence(DiceGenerator.diceNumbers);
			//5 sequence
			case 12: return Points.fiveSequence(DiceGenerator.diceNumbers);
			//5ofakind
			case 13: return Points.fiveofakind(DiceGenerator.diceNumbers);
			//cdo rast
			case 14: return Points.addAllDice(DiceGenerator.diceNumbers);
			
			//case 15: below points
			//PlayerTableRenderer
			//case 16: total
			//PlayerTableRenderer
		}//shfaq vlerat per rreshtat 0-5 dhe 8-14
		return 0;
	}
}