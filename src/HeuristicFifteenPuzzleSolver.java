import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class HeuristicFifteenPuzzleSolver {

	//Rozwiazuje 15 DFSem i drukuje rozwiazanie po kazdym zdjeciu ze stosu

	public static FifteenPuzzle DFS(FifteenPuzzle fp) {
		
		// licznik ile mamy tablic do gry
		int generated = 0;
		
		//rozwiazanie
		FifteenPuzzle solution = new FifteenPuzzle();
		
		//ile odwiedzilismy
		HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//stos do tablic
		Stack<FifteenPuzzle> s = new Stack<FifteenPuzzle>();
		s.push(fp);
		
		while (s.size() > 0) 
		{
			FifteenPuzzle current = s.pop();
			if (generated % 100000 == 0) {
				System.out.println(current);
				System.out.println("Wygenerowano: " + generated + 
						           " Rozmiar stosu: " + s.size() + 
					               " Odwiedzono: " + visited.size());
			}
			if (current.equals(solution)) {
				return current;
			}
			// utworz kolejne stany
			for (int i = 0 ; i < 4 ; ++i) {
				// kopiuj i wykonaj ruch
				FifteenPuzzle moved = current.clone();
				if (!moved.move(i))  // jezeli nie jest dozwolony to pomin
					continue;
				generated++;
				if (!visited.contains(moved)) {
					visited.add(moved);
					s.push(moved);
				}
			}
		}
		return null;
	}
	
	//Rozwiazuje 15 BFSem i drukuje rozwiazanie po kazdym zdjeciu ze stosu
	
	public static FifteenPuzzle BFS(FifteenPuzzle fp) {
		// Count how many boards we've created.
				int generated = 0;
				
		// rozwiazanie
				FifteenPuzzle solution = new FifteenPuzzle();
				
		//ile odwiedzilismy
				HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//zmieniamy na kolejke 
				Queue<FifteenPuzzle> s = new LinkedList<FifteenPuzzle>();
				s.add(fp);
				
				while (s.size() > 0) 
				{
					FifteenPuzzle current = s.poll();
					if (generated % 100000 == 0) {
						System.out.println(current);
						System.out.println("Wygenerowano: " + generated + 
						           " Rozmiar stosu: " + s.size() + 
					               " Odwiedzono: " + visited.size());
					}
					if (current.equals(solution)) {
						return current;
					}
					for (int i = 0 ; i < 4 ; ++i) {
						
						FifteenPuzzle moved = current.clone();
						if (!moved.move(i))  
							continue;
						generated++;
						if (!visited.contains(moved)) {
							visited.add(moved);
							s.add(moved);
						}
					}
				}
				return null;
	}
	
	public static FifteenPuzzle AStar(FifteenPuzzle fp) {
		
		// licznik ile mamy tablic do gry
		
		int generated = 0;
		
		// rozwiazanie
		
		HeuristicFifteenPuzzle solution = new HeuristicFifteenPuzzle();
		
		//ile odwiedzilismy
		HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//wykorzystujemy kolejke priorytetowa i heurystyke
		PriorityQueue<HeuristicFifteenPuzzle> p = new PriorityQueue<HeuristicFifteenPuzzle>();
		p.add(new HeuristicFifteenPuzzle(fp));
		
		while (p.size() > 0) {
			HeuristicFifteenPuzzle current = p.poll();
			if (generated % 10 == 0) {
				System.out.println("\n"+current);
				System.out.println();

				System.out.println("Wygenerowano: " + generated + 
				           " Rozmiar stosu: " + p.size() + 
			               " Odwiedzono: " + visited.size());
			}
			
			if (current.equals(solution)) {
				return current;
			}
			for (int i = 0 ; i < 4 ; ++i) {
				HeuristicFifteenPuzzle moved = current.clone();
				if (!moved.move(i))  
					continue;
				generated++;
				if (!visited.contains(moved)) {
					visited.add(moved);
					p.add(moved);
				}
			}
		}
		return null;
	}
	
	// FUNKCJA MAIN
	public static void main(String[] args) {
		FifteenPuzzle fp = new FifteenPuzzle();
		fp.shuffle();
		System.out.println("\n"+AStar(fp));
	}
}
