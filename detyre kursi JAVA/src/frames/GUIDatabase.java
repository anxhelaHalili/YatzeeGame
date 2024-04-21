package frames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import data.Database;

public class GUIDatabase extends CustomFrame implements ActionListener {//implementon ActionListener per te detektuar shtypjen e butonave
	private static final long serialVersionUID = 1L;

	private static JTextField userName = new JTextField("root", 10);
	private static JTextField password = new JTextField("root", 10);
	private static JTextField serverName = new JTextField("localhost", 10);
	private static JTextField portNumber = new JTextField("3306", 10);
	private static JButton ok = new JButton("Next");
	private static JButton noData = new JButton("Luaj Pa Databaze");

	public GUIDatabase() {//konstruktor i frame-it qe merr te dhenat per tu lidhur me MySQL
		super("MySQL");

		addBackground("MySQL.png");
		setLayout(new GridLayout(5, 2, 0, 5));//vendos GridLayout() si menaxherin default te frame-it, 5 rreshta 2 kolona 0 hapesire horizontalisht 5 vertikalisht
		
		add(new JLabel("Username:", JLabel.CENTER));	add(userName);//shtojme komponentet ne frame
		add(new JLabel("Password:", JLabel.CENTER));	add(password);
		add(new JLabel("Server Name:", JLabel.CENTER));	add(serverName);
		add(new JLabel("Port Number:", JLabel.CENTER));	add(portNumber);
		add(noData);	noData.addActionListener(this);
		add(ok);		ok.addActionListener(this);

		pack();//ben qe frame-i te pershtatet ne gjatesi dhe gjeresi me komponentet e veta
		setLocationRelativeTo(null);//vendos GUI ne qender te ekranit
		setVisible(true);//ben frame-in te dukshem
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ok) {
			if (Database.getConnection(userName.getText(), password.getText(), serverName.getText(),
					Integer.parseInt(portNumber.getText())) == false) {//teston nese u krijua lidhje me MySQL
				JOptionPane.showMessageDialog(null, "Ju lutem kontrolloni te dhenat!",
						"Nuk u krijua dot nje lidhje me MySQL.", JOptionPane.ERROR_MESSAGE);//nese jo, shafq error
			} else {
				dispose();
				new GUIStart();//nese po, heq frame-in ekzistent dhe krijon nje framr GUIStart()
			}
		}
		if (e.getSource() == noData) {
			dispose();
			new GUIStart();//krijon GUIStart(), pa u munduar te krijoje lidhje me MySQL
		}
	}
}