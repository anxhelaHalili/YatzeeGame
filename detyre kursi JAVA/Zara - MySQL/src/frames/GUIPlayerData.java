package frames;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Database;
import data.PlayerGame;

public class GUIPlayerData extends CustomFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<JPanel> playerRow = new ArrayList<>(); //rreshti qe kerkon te dhenat e lojtarit
	private ArrayList<JTextField> firstNamesInput = new ArrayList<>(); //mban JTextField ku shkruhet emri
	private ArrayList<JTextField> lastNamesInput = new ArrayList<>(); //mban JTextField ku shkruhet mbiemeri
	private ArrayList<JComboBox<Integer>> ageInput = new ArrayList<>(); //mban JComboBox per moshen
	
	GUIPlayerData(int n){//krijon nje frame qe merr te dhenat e lojtareve
		super("Lojtaret");
		setLayout(new BorderLayout());
		
		JPanel allRows = new JPanel(new GridLayout(n,1));//n rreshta 1 kolone
		for(int i=0;i<n;i++) {
			playerRow.add(new JPanel());
			playerRow.get(i).add(new JLabel("Emri i lojtarit nr. "+(i+1)+": ", JLabel.CENTER)); 	
			firstNamesInput.add(new JTextField(10));
			playerRow.get(i).add(firstNamesInput.get(i));
			playerRow.get(i).add(new JLabel("Mbiemri: ", JLabel.CENTER));							
			lastNamesInput.add(new JTextField(10));
			playerRow.get(i).add(lastNamesInput.get(i));	
			playerRow.get(i).add(new JLabel("Mosha: ", JLabel.CENTER));								
			ageInput.add(new JComboBox<Integer>());
			for(int l=0;l<100;l++)
				ageInput.get(i).addItem(l);
			playerRow.get(i).add(ageInput.get(i));
			allRows.add(playerRow.get(i));
		}
		add(allRows, BorderLayout.CENTER);
		
		JButton next = new JButton("Vazhdo!");
		next.addActionListener(this);
		add(next, BorderLayout.SOUTH);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		//shfaq nje mesazh nese fushat jane bosh.
		for(int i=0;i<playerRow.size();i++) {
			if(firstNamesInput.get(i).getText().isEmpty() || lastNamesInput.get(i).getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, 
                        "Ju lutem plotesoni te gjitha fushat.", 
                        "Emer/Mbiemer Bosh!", 
                        JOptionPane.DEFAULT_OPTION);
				return;//ndalon vazhdimin e metodes
			}
		}
		//shfaq nje mesazh nese emer dhe mbiemer eshte i njejte per te pakten 2 lojetare
		for(int i=0;i<playerRow.size();i++)
			for(int j=i+1;j<playerRow.size();j++)
				if(firstNamesInput.get(i).getText().equals(firstNamesInput.get(j).getText()) 
					&& lastNamesInput.get(i).getText().equals(lastNamesInput.get(j).getText())) {
					JOptionPane.showMessageDialog(null, 
	                        "Te gjithe lojetaret duhet te kene emer/mbiemer te ndryshem.", 
	                        "Emer/Mbiemer i Njejte!",
	                        JOptionPane.DEFAULT_OPTION);
					return;
				}
		//kalon lojetaret ne databaze dhe arraylist CurrentPlayers
		for(int i=0; i<playerRow.size(); i++) {
			String fName = firstNamesInput.get(i).getText();
			String lName = lastNamesInput.get(i).getText();
			Database.createPlayer(fName, lName, ageInput.get(i).getSelectedItem().toString());
			PlayerGame.currentPlayers.add(new PlayerGame(fName, lName));
		}
		dispose();
		new GUIGame();//krijon GUIGame()
	}
}