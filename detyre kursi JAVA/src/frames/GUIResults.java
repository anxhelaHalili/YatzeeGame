package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import data.Database;
import data.PlayerGame;
import panels.PlayerTableRenderer;

public class GUIResults extends CustomFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	GUIResults() {//frame-i shfaq piket e lojetareve, fituesin dhe lojetarin me piket me te larte/uleta nga MySQL
		super("Zara");
		addBackground("Result.jpg");
		setLayout(new BorderLayout());
		
		JPanel players = new JPanel();
		players.setOpaque(false);
		players.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel top = new JPanel(new GridLayout(2,1));
		top.setOpaque(false);
		top.setBorder(new EmptyBorder(10, 10, 10, 10));

		ArrayList<JLabel> playerRows = new ArrayList<>();
		int i=0;
		for(; i<PlayerGame.currentPlayers.size();i++) {
			playerRows.add(new JLabel("", SwingConstants.CENTER));
			playerRows.get(i).setForeground(Color.WHITE);
			playerRows.get(i).setText(PlayerGame.currentPlayers.get(i).getFirstName()+" : "
									+PlayerTableRenderer.total.get(i)+" Pike");
			playerRows.get(i).setForeground(Color.WHITE); playerRows.get(i).setFont(new Font("Vollkorn", Font.PLAIN, 18));;
			players.add(playerRows.get(i));
		}//rradhit lojetaret dhe sa pike kane marre
		
		int max = -1;
		for(int j=0; j<PlayerGame.currentPlayers.size();j++) {
			if(max<PlayerTableRenderer.total.get(j))
				max=PlayerTableRenderer.total.get(j);
		}
		int indexWinner = PlayerTableRenderer.total.indexOf(max);//percakton indeksin e lojetarit me me shume pike totale
		
		playerRows.add(new JLabel("Urime! "+PlayerGame.currentPlayers.get(indexWinner).getFirstName()
				+" eshte fitues me "+PlayerTableRenderer.total.get(indexWinner)+ " pike!", SwingConstants.CENTER));
		playerRows.get(i).setForeground(Color.WHITE); playerRows.get(i).setFont(new Font("Vollkorn", Font.BOLD, 20));;
		players.add(playerRows.get(i));//shfaq nje rresht tjeter per fituesin
		i++;
		
		players.setLayout(new GridLayout(playerRows.size(),1));
		Database.writeDatabaseData();//kalon lojetaret e kesaj loje dhe piket qe kane marre ne MySQL
		
		Object record[];
		record = Database.calculateHighestLowest();//percakton lojetarin me piket me te larte/uleta nga databaza ne MySQL
		if(record != null) {
			playerRows.add(new JLabel("Rezultati me i larte: "+(String)record[0]+" "+(String)record[1]+" Loja nr. "+(Integer)record[2]+" "+(Integer)record[3]+" Pike"));
			playerRows.get(i).setForeground(Color.WHITE); playerRows.get(i).setFont(new Font("Vollkorn", Font.BOLD, 20));;
			top.add(playerRows.get(i));
			i++;
			playerRows.add(new JLabel("Rezultati me i ulet: "+(String)record[4]+" "+(String)record[5]+" Loja nr. "+(Integer)record[6]+" "+(Integer)record[7]+" Pike"));
			playerRows.get(i).setForeground(Color.WHITE); playerRows.get(i).setFont(new Font("Vollkorn", Font.BOLD, 20));;
			top.add(playerRows.get(i));
		}//shfaq rreshta per lojetarin me pike me te larta/uleta

		JButton exit = new JButton("Dil!");
		exit.addActionListener(this);//buton per te dale nga programi
		exit.setOpaque(false);
		exit.setFocusPainted(false);
		
		add(players,BorderLayout.NORTH);
		add(top,BorderLayout.CENTER);
		add(exit,BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
		System.exit(0);//del nga programi
	}
}