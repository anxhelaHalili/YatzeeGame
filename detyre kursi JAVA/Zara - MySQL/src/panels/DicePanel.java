package panels;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import calculations.DiceGenerator;
import data.PlayerGame;
import frames.GUIGame;

public class DicePanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private ArrayList<JLabel> imgLabels = new ArrayList<>();

	public DicePanel() {//paneli i zarave
		setLayout(new GridLayout(5, 1, 5, 5));
		setOpaque(false);

		try {
			Image image;
			for (int i = 0; i < DiceGenerator.diceNumbers.size(); i++) {
				image = ImageIO.read(new File("src/resources/" + DiceGenerator.diceNumbers.get(i) + ".png")).getScaledInstance(84, 84, Image.SCALE_SMOOTH);
				imgLabels.add(i, new JLabel(new ImageIcon(image)));
				imgLabels.get(i).setName(String.valueOf(DiceGenerator.diceNumbers.get(i)));
				imgLabels.get(i).addMouseListener(this);

				add(imgLabels.get(i));
			}//shton ne panel imazhet e zareve
		} catch (IOException e) {
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {//ne rast se nje nga imazhet e zareve shtypet, krijohet nje border ngjyre te bardhe rreth imazhit dhe vlera vendoset -1
		if (GUIGame.playerIndex != -1 //ndalon shtypjen e imazheve nese nuk ka filluar loja
				&& PlayerGame.currentPlayers.get(GUIGame.playerIndex).getRollsMade() < 3 //ndalon shtypjen e imazheve nese hedhjet e zareve jane 3
				&& PlayerGame.currentPlayers.get(GUIGame.playerIndex).getSelectedThisTurn()==false) {//ndalon shtypjen e imazheve nese eshte zgjidhur nje kategori kete cikel
			int i = imgLabels.indexOf(e.getSource());

			if (DiceGenerator.diceNumbers.get(i) != -1) {
				imgLabels.get(i).setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				DiceGenerator.diceNumbers.set(i, -1);

			} else {//nese shtypet nje imazh qe eshte shtypur njehere, hiqen border-at dhe kthehet vlera origjinale
				imgLabels.get(i).setBorder(BorderFactory.createEmptyBorder());
				DiceGenerator.diceNumbers.set(i, Integer.parseInt(((JLabel) e.getSource()).getName()));
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}