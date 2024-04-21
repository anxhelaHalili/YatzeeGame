
import frames.CustomFrame;
import frames.GUIDatabase;

public class Game {
	public static void main(String[] args) {
		CustomFrame.setLookAndFeel(); //percakton LookAndFeel te GUI-t
		new GUIDatabase(); //krijon GUI-n
	}
}