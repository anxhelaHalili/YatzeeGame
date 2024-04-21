package calculations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceGenerator {
	public static List<Integer> diceNumbers = new ArrayList<>(List.of(0,0,0,0,0));
	private static Random rand = new Random();

	public static void generateDiceAll(){//gjeneron zare te rinj per te gjithe zaret
		for(int i=0;i<diceNumbers.size();i++) {
			DiceGenerator.diceNumbers.set(i, rand.nextInt(6)+1);
		}
	}

	public static void generateDiceSelect() {//gjeneron zare te rinj vetem per zaret qe jane zgjedhur per tu hequr (vlere -1)
		for(int i=0;i<diceNumbers.size();i++) {
			if(diceNumbers.get(i)==-1)
				DiceGenerator.diceNumbers.set(i, rand.nextInt(6)+1);
		}
	}
}