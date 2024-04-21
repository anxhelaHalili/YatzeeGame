package data;

import java.util.ArrayList;
import java.util.List;

public class PlayerGame{
	
	public static List<PlayerGame> currentPlayers = new ArrayList<>();//mban te dhenat e lojetareve qe po luajne per momentin

	private String fName;//emri i lojetarit
	private String lName;//mbiemeri
	private int playCount;//numri i lojes
	private int rollsMade;//hedhjet qe kane bere kete cikel
	private boolean selectedThisTurn;//nese kane zgjedhur nje kategori kete cikel
	
	public PlayerGame(String fName, String lName) {//konstruktori
		this.fName = fName;
		this.lName = lName;
		playCount = Database.getCurrentPlayCount(fName, lName);
		rollsMade = 0;
		selectedThisTurn = false;
	}
	//aksesore
	public String getFirstName() {
		return this.fName;
	}
	
	public String getLastName() {
		return this.lName;
	}
	
	public int getPlayCount() {
		return this.playCount;
	}
	
	public int getRollsMade() {
		return this.rollsMade;
	}
	
	public boolean getSelectedThisTurn() {
		return this.selectedThisTurn;
	}
	//mutators
	public void setSelectedThisTurn(boolean selectedThisTurn) {
		this.selectedThisTurn=selectedThisTurn;
	}
	
	public void setRollsMade(int rollsMade) {
		this.rollsMade = rollsMade;
	}
	
	public void incRollsMade() {//rrit numrin e hedhjeve deri ne 3 dhe me pas ne 0
		if(rollsMade==3) {
			rollsMade=0;
			return;
		}
		this.rollsMade++;
	}
}