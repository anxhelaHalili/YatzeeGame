package frames;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class GUIStart extends CustomFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JLabel playerNrAsk = new JLabel("Numri i lojtareve: ");
	private JCheckBox nr1 = new JCheckBox("1 Lojtar", true);
	private JCheckBox nr2 = new JCheckBox("2 Lojtar", false);
	private JCheckBox nr3 = new JCheckBox("3 Lojtar", false);
	private JCheckBox nr4 = new JCheckBox("4 Lojtar", false);
	private JButton start = new JButton("Loje e re!");
	
	GUIStart(){//krijon nje frame qe merr sa lojetar do luajne
		super("Zara");
		addBackground("Start.png");
		setLayout(new FlowLayout());
		
		add(playerNrAsk);
		
		ButtonGroup playerNr = new ButtonGroup();
		playerNr.add(nr1);
		playerNr.add(nr2);
		playerNr.add(nr3);
		playerNr.add(nr4);
		add(nr1);
		add(nr2);
		add(nr3);
		add(nr4);
		
		start.addActionListener(this);
		add(start);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//krijon nje GUIPlayerData() frame me aq rreshta sa lojetar te perzgjedhur
		dispose();
		if(nr1.isSelected()) {
			new GUIPlayerData(1);
		}else if(nr2.isSelected()) {
			new GUIPlayerData(2);
		}else if(nr3.isSelected()) {
			new GUIPlayerData(3);
		}else {
			new GUIPlayerData(4);
		}
	}
}