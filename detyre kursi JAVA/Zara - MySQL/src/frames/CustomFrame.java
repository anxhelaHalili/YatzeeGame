package frames;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CustomFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	CustomFrame(String name) {
		super(name);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//krijon nje frame me emrin name, jo resizable dhe EXIT_ON_CLOSE si DefaultCloseOperation
	
	void addBackground(String name) {
		try {
			JLabel background;
			Image image;
			image = ImageIO.read(new File("src/resources/"+name));
			background = new JLabel(new ImageIcon(image));
			setContentPane(background);

		} catch (IOException e) {}
	}//shton nje JLabel me imazh si ContentPane te frame-it

	public static void setLookAndFeel() {//percakton paraqitjen e GUIt, LookAndFeel te pergjithshem dhe font-et per elementet
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/Vollkorn.ttf")));
			Font myFont = new Font("Vollkorn", Font.PLAIN, 16);
			
		    UIManager.put("CheckBoxMenuItem.acceleratorFont", myFont);
		    UIManager.put("Button.font", myFont);
		    UIManager.put("CheckBox.font", myFont);
		    UIManager.put("ComboBox.font", myFont);
		    UIManager.put("Label.font", myFont);
		    UIManager.put("OptionPane.buttonFont", myFont);
		    UIManager.put("OptionPane.messageFont", myFont);
		    UIManager.put("Menu.font", myFont);
		    UIManager.put("PopupMenu.font", myFont);
		    UIManager.put("OptionPane.font", myFont);
		    UIManager.put("Panel.font", myFont);
		    UIManager.put("ProgressBar.font", myFont);
		    UIManager.put("ScrollPane.font", myFont);
		    UIManager.put("Viewport.font", myFont);
		    UIManager.put("TabbedPane.font", myFont);
		    UIManager.put("Slider.font", myFont);
		    UIManager.put("Table.font", myFont);
		    UIManager.put("TableHeader.font", new Font("Vollkorn", Font.BOLD, 16));
		    UIManager.put("TextField.font", myFont);

			}
		catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException | FontFormatException | IOException e) {}
	}
}
