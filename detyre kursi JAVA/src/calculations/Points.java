package calculations;

import java.util.Arrays;
import java.util.List;

public class Points {
	
	public static int addValueDice(List<Integer> n, int valueToAdd) {//kthen vleren e zareve me valueToAdd pike
		int sum = 0;
		for(int i=0;i<n.size();i++)
			if(n.get(i)==valueToAdd)
				sum+=n.get(i);
		return sum;
	}

	public static int threeofakind(List<Integer> n) {//kthen vleren e zareve kur ka 3 te njejte
		int sum = 0;
		toploop:
		for(int i=0;i<n.size();i++)
			for(int j=i+1;j<n.size();j++)
				for(int k=j+1;k<n.size();k++)
					if(n.get(i)==n.get(j) && n.get(j)==n.get(k)) {
						for(int m=0;m<n.size();m++)
							sum+=n.get(m);
						break toploop;
					}
		return sum;
	}
	
	public static int fourofakind(List<Integer> n) {//kthen vleren e zareve kur ka 4 te njejte
		int m[] = {n.get(0), n.get(1), n.get(2), n.get(3), n.get(4)};
		Arrays.sort(m);
		int sum = 0;
		if((m[0]==m[1] && m[1]==m[2] && m[2]==m[3]) 
				|| (m[1]==m[2] && m[2]==m[3] && m[3]==m[4]))
			for(int k=0;k<n.size();k++)
				sum+=m[k];
		return sum;
	}
	
	public static int fiveofakind(List<Integer> n) {//kthen 50 pike kur ka 5 te njejte
		if(n.get(0)==n.get(1) && n.get(1)==n.get(2) && n.get(2)==n.get(3) && n.get(3)==n.get(4))
				return 50;
		return 0;
	}
	
	public static int threeandtwo(List<Integer> n) {//kthen 25 pike kur ka 3 te njejte dhe 2 te njejte
		if((n.get(0)==n.get(1) && n.get(1)==n.get(2) && n.get(3)==n.get(4)) 
				|| (n.get(0)==n.get(1) && n.get(2)==n.get(3) && n.get(3)==n.get(4)))
				return 25;
		return 0;
	}
	
	public static int fourSequence(List<Integer> n) {//kthen 30 pike kur nje sekuence prej 4 zaresh me vlera te njepasnjeshme
		int m[] = {n.get(0), n.get(1), n.get(2), n.get(3), n.get(4)};
		Arrays.sort(m);
		
		if((m[0]+1==m[1] && m[1]+1==m[2] && m[2]+1==m[3]) 
				|| (m[1]+1==m[2] && m[2]+1==m[3] && m[3]+1==m[4]))
				return 30;
		return 0;
	}
	
	public static int fiveSequence(List<Integer> n) {//kthen 40 pike kur nje sekuence prej 5 zaresh me vlera te njepasnjeshme
		int m[] = {n.get(0), n.get(1), n.get(2), n.get(3), n.get(4)};
		Arrays.sort(m);

		if(m[0]+1==m[1] && m[1]+1==m[2] && m[2]+1==m[3] && m[3]+1==m[4])
				return 40;
		return 0;
	}
	
	public static int addAllDice(List<Integer> n) {//kthen vleren e gjithe zareve
		int sum = 0;
		for(int i=0;i<n.size();i++)
			sum+=n.get(i);
		return sum;
	}
}