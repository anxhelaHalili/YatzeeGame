package panels;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import frames.GUIGame;

public class PlayerTableRenderer extends DefaultTableCellRenderer {//renderer i modifikuar per kolonen e tabelave te lojetareve
	private static final long serialVersionUID = 1L;
	
	private static ArrayList<Map<Integer ,Boolean>> selectedRows = new ArrayList<>();//mban indeksin e rreshtave te zgjedhur indeksi i lojetarit(indeksi i rreshtit, true|false)
	private static ArrayList<Map<Integer ,Object>> selectedValues = new ArrayList<>();//mban vlerat e rreshtave te zgjedhur indeksi i lojetarit(indeksi i rreshtit, vlera)
	private static boolean initializedArrays = false; //percakton nese array-it jane inicializuar
	
	private static ArrayList<Integer> abovePoints = new ArrayList<>(); //mban vleren totale te rreshtave te siperm te zgjedhur (indeksi i lojetarit, piket e siperme) 
	private static ArrayList<Integer> bonus = new ArrayList<>(); //mban vleren bonus (indeksi i lojetarit, bonus)
	private static ArrayList<Integer> belowPoints = new ArrayList<>(); //mban vleren totale te rreshtave te poshtem te zgjedhur (indeksi i lojetarit, piket e poshtme)
	public static ArrayList<Integer> total = new ArrayList<>(); //mban piket totale te rreshtave te zgjedhur (indeksi i lojetarit, piket totale)
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {//renderi per CellRender
    	
    	if(initializedArrays==false) {
	    	for(int i=0;i<data.PlayerGame.currentPlayers.size();i++) {
		    	selectedRows.add(new HashMap<Integer, Boolean>());
		    	selectedValues.add(new HashMap<Integer, Object>());
		    	abovePoints.add(0);
		    	bonus.add(0);
		    	belowPoints.add(0);
		    	total.add(0);
	    	}
    	}initializedArrays=true;//inicializon arraylist-at

    	int workingTable = Integer.parseInt(table.getName());//emri i tabeles korrespondon me indeksin e lojetarit

		if(GUIGame.playerIndex==workingTable) {
			table.getTableHeader().setOpaque(false);
			table.getTableHeader().setBackground(new Color(90, 80, 195));
			table.getTableHeader().setForeground(Color.WHITE);
		}//vendos ngjyren e header-it te tabeles blu te erret nese eshte tabela e njejte me indeksin e lojetarit qe ka rradhen per momentin
    	
    	if (selectedRows.get(workingTable).containsKey(row)) {
            value = selectedValues.get(workingTable).get(row);
            setText(value.toString());//nese selectedRows permban kete rresht per kete tabele, shfaq vleren e zgjedhur me perpara
        } else if(workingTable == GUIGame.playerIndex){
            setText(value.toString());//nese jo, shfaq nje vlere te re
        } else {
        	setText("");
        }
    	
    	switch(row) {
    		case 6:
        		int aP=0;
    			for(int i=0;i<6;i++)
    				if(selectedValues.get(workingTable).containsKey(i))
    					aP+=(int)selectedValues.get(workingTable).get(i);
    			abovePoints.set(workingTable, aP);
    	        setText(""+aP);
    	        break;
    		case 7:
            	int b=0;
        		if(abovePoints.get(workingTable)>63)
        			b=35;
        		bonus.set(workingTable, b);
    	        setText(""+b);
    	        break;
    		case 15:
            	int bP=0;
    			for(int i=8;i<15;i++)
    				if(selectedValues.get(workingTable).containsKey(i))
    					bP+=(int)selectedValues.get(workingTable).get(i);
        		belowPoints.set(workingTable, bP);
    	        setText(""+bP);
    	        break;
    		case 16:
            	int t=abovePoints.get(workingTable)+bonus.get(workingTable)+belowPoints.get(workingTable);
        		total.set(workingTable, t);
    	        setText(""+t);
    	        break;
    	}//shfaq vlerat perkatese per piket e siperme, bonus, piket e poshtme dhe piket totale

		if ((int) value > 0 && !(row == 6 || row == 7 || row == 15 || row == 16) && workingTable == GUIGame.playerIndex) {
			setBackground(new Color(90,225,135));//nese jane qeliza ne rreshta jo te pikeve te siperme, bonus, te poshtme apo total dhe piket qe mund te merren jane me shume se 0 i shfaq me nje background ngjyre jeshil
		} else if (row == 6 || row == 7 || row == 15 || row == 16) {
			setBackground(Color.GRAY);//nese jane qeliza te atyre rreshtave, i shfaq me nje background ngjyre gri te erret
		} else {
			setBackground(new Color(245,245,245));//gjithe te tjerat qeliza shfaqen me nje background ngjyre gri shume te lehte
		}

		if (hasFocus && !(row == 6 || row == 7 || row == 15 || row == 16) && data.PlayerGame.currentPlayers.get(workingTable).getSelectedThisTurn()==false
				&& !(selectedRows.get(workingTable).containsKey(row)) 
				&& workingTable == GUIGame.playerIndex) {
			//vendos nje ngjyre portokall ne rreshtin e zgjedhur me kusht qe nuk ndodhet ne selectedRows per kete tabele, nuk eshte zgjedhur kete rradhe dhe eshte zgjedhje kategorie e mundshme 
			setBackground(Color.ORANGE);
			selectedRows.get(workingTable).put(row, true);
			selectedValues.get(workingTable).put(row, value);
			data.PlayerGame.currentPlayers.get(workingTable).setSelectedThisTurn(true);
			
			if (data.PlayerGame.currentPlayers.size() == 1)
				GUIGame.throwDiceButton.setText("Cikli Tjeter!");
			else
				GUIGame.throwDiceButton.setText("Lojetari Tjeter!");//u be zgjedhja, ne buton ndryshojme tekstin
			
			GUIGame.diceChances.setText("Zgjidhja u krye. Shtypni \"" + GUIGame.throwDiceButton.getText() + "\"");
			if (GUIGame.cycleCount == 12) {
				GUIGame.throwDiceButton.setText("Perfundo!");
				GUIGame.nameTurn.setText("");
				GUIGame.diceChances.setText("Shtyp \"Perfundo\".");
			}//u be zgjedhja, ne buton dhe labela shfaqim tekste te pershtatshme
		}

		if (selectedRows.get(workingTable).containsKey(row))
			setBackground(Color.ORANGE);//nese ky rresht eshte zgjehur me perpara e shfaq ngjyre portokalli

        return this;
    }
}