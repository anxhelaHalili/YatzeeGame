package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import calculations.DiceGenerator;
import panels.CategoriesPanel;
import panels.DicePanel;
import panels.PlayerPanel;
import data.PlayerGame;

public class GUIGame extends CustomFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static boolean gameStarted = false;//boolean-i perdoret ne fillim te lojes per te percaktuar nese duhet nje button "Fillo Lojen"
	public static int playerIndex = -1;//mban indeksin e lojetaret qe ka rradhen
	public static int cycleCount = 0;//mban numrin e cikleve qe kane kaluar ne loje

	private static JButton startButton = new JButton("Fillo Lojen!");
	public static JButton throwDiceButton = new JButton();
	public static JLabel nameTurn = new JLabel("", SwingConstants.CENTER);
	public static JLabel diceChances = new JLabel("", SwingConstants.CENTER);

	GUIGame() {//frame-i kryesor i lojes
		super("Zara");
		
		addBackground("Background.jpg");
		setLayout(new BorderLayout());//BorderLayout, pozicionet CENTER, SOUTH, NORTH, EAST, WEST
 
		addToFrame();//shton panelet kryesore dhe panelin me butonin startButton ose panelin me throwDiceButton
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		throwDiceButton.addActionListener(this);
	}

	private void addToFrame() {
		addMainPanels();
		if(gameStarted == false) addStartPanel(); 
		else addInteractPanel();
	}

	private void addMainPanels() {// panels = zarat+kategorite+player panel
		JPanel panels = new JPanel(new FlowLayout());
		panels.setOpaque(false);//vendos pixelat e panelit transperante
		panels.add(new DicePanel());// paneli i zarave
		panels.add(new CategoriesPanel());// kategorite
		for (int i = 0; i < PlayerGame.currentPlayers.size(); i++)
			panels.add(new PlayerPanel(PlayerGame.currentPlayers.get(i).getFirstName(), i));// player panel
		
		add(panels, BorderLayout.CENTER);// panels ne main frame
	}
	
	private void addStartPanel() {//shton ne frame nje panel me startButton
		JPanel startPanel = new JPanel(new FlowLayout());
		startPanel.setOpaque(false);
		startButton.setOpaque(false);
		startButton.addActionListener(this);
		startPanel.add(startButton);
		add(startPanel, BorderLayout.SOUTH);
	}
	
	private void addInteractPanel() {//shton ne frame panel me throwDiceButton dhe JLabel qe shfaq lojetarin qe ka rradhen dhe sa mundesi per te hedhur zare
		JPanel interact = new JPanel();
		JPanel throwDice = new JPanel(new FlowLayout());
		throwDice.setOpaque(false);
		interact.setLayout(new BorderLayout());

		interact.setOpaque(false);
		interact.setBorder(new EmptyBorder(5,5,5,5));

		throwDiceButton.setFocusPainted(false);
		throwDiceButton.setOpaque(false);
		throwDiceButton.setFont(new Font("Vollkorn", Font.BOLD, 15));//percakton fontin
		throwDiceButton.setText("Hidh Zaret!");
		if (PlayerGame.currentPlayers.get(playerIndex).getRollsMade() == 3) {
			if (PlayerGame.currentPlayers.size() == 1)
				throwDiceButton.setText("Cikli Tjeter!");//nese ka vetem nje lojetar dhe jane bere 3 hedhje, butoni shfaq "Cikli Tjeter!"
			else
				throwDiceButton.setText("Lojetari Tjeter!");//nese jane bere 3 hedhje nga lojetari, butoni shfaq "Lojetari Tjeter!"
		}

		nameTurn.setFont(new Font("Vollkorn", Font.BOLD, 18));
		nameTurn.setForeground(Color.WHITE);//vendos ngjyren e fontit
		nameTurn.setText("Rradha - " + PlayerGame.currentPlayers.get(playerIndex).getFirstName());
		
		diceChances.setFont(new Font("Vollkorn", Font.BOLD, 16));
		diceChances.setForeground(Color.WHITE);
		diceChances.setText("Zgjidhni zarat qe doni te hidhni perseri dhe shtypni \"Hidh Zarat\" ose zgjidhni nje kategori. Mundesi: "
				+ PlayerGame.currentPlayers.get(playerIndex).getRollsMade() + "/3");
		if (PlayerGame.currentPlayers.get(playerIndex).getRollsMade() == 3) {//nese jane bere 3 hedhje nga lojetari
			if (PlayerGame.currentPlayers.size() == 1)
				diceChances.setText("Nuk keni mundesi te tjera. Zgjidhni nje kategori dhe shtypni \"Cikli Tjeter\"!");
			else
				diceChances.setText("Nuk keni mundesi te tjera. Zgjidhni nje kategori dhe shtypni \"Lojetari Tjeter\"!");
		}
		
		throwDice.add(throwDiceButton);
		interact.add(throwDice, BorderLayout.NORTH);
		interact.add(nameTurn, BorderLayout.CENTER);
		interact.add(diceChances, BorderLayout.SOUTH);
		add(interact, BorderLayout.SOUTH);
	}

	private void resetGUI() {//vizaton serish komponentet ne frame
		getContentPane().removeAll();//heq gjithcka nga frame-i
		addToFrame();//shton perseri panelet e perditesuar ne frame
		revalidate();//kalkulon edhe njehere layout-in
		repaint();//kerkon frame-in te vizatoj serish (repaint) vetveten
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == startButton) {//shtypet butoni i fillimit te lojes
			DiceGenerator.generateDiceAll();//gjenerohen zare te rinj
			gameStarted = true;//loja ka filluar
			playerIndex = 0;//indeksi vendoset tek lojetari i pare (indeksi 0)
			resetGUI();
		}

		if (e.getSource() == throwDiceButton) {//shtypet butoni i hedhjes se zareve
			DiceGenerator.generateDiceSelect();//gjeneron zare te rinj vetem per ato zare qe jane zgjedhur per tu hequr
			if(PlayerGame.currentPlayers.get(playerIndex).getRollsMade()<3 && PlayerGame.currentPlayers.get(playerIndex).getSelectedThisTurn()==false)
				PlayerGame.currentPlayers.get(playerIndex).incRollsMade();//nese numri i hedhjeve eshte me i vogel se 3 dhe nuk eshte zgjedhur nje kategori
			if(PlayerGame.currentPlayers.get(playerIndex).getSelectedThisTurn()==true) {
				if (playerIndex == PlayerGame.currentPlayers.size() - 1)	playerIndex = 0;	else 	playerIndex++;//nese eshte zgjedhur nje kategori, rrit indeksin e lojetarit
				PlayerGame.currentPlayers.get(playerIndex).setRollsMade(0);//per indeksin e ri, vendos qe lojetari nuk ka bere asnje hedhje kete cikel
				PlayerGame.currentPlayers.get(playerIndex).setSelectedThisTurn(false);//per indeksin e ri, vendos qe lojetari nuk ka zgjedhur kategori kete cikel
				DiceGenerator.generateDiceAll();//gjeneron zare te rinj per indeksin e ri
				if (playerIndex == 0)	cycleCount++;//nese indeksi eshte kthyer ne 0, ka kaluar nje cikel
			}

			try {
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/resources/diceRoll.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(audioInputStream);
				clip.start();
			} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e1) {}//luan nje efekt zanor kur hidhen zare
			resetGUI();

			if (cycleCount == 13) {
				dispose();
				new GUIResults();
			}//nese kane kaluar 13 cikle, jane zgjedhur te gjithe kategorite dhe loja do perfundoje
		}
	}
}