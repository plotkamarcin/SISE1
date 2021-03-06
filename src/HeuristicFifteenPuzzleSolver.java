import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class HeuristicFifteenPuzzleSolver {

	//Rozwiazuje 15 DFSem i drukuje rozwiazanie po kazdym zdjeciu ze stosu

	public static FifteenPuzzle DFS(FifteenPuzzle fp) {
		FifteenPuzzle.move.append("   ");
		// licznik ile mamy tablic do gry
		int generated = 0;
		
		//rozwiazanie
		FifteenPuzzle solution = new FifteenPuzzle();
		
		//ile odwiedzilismy
		HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//stos do tablic
		Stack<FifteenPuzzle> stree = new Stack<FifteenPuzzle>();
		stree.push(fp);
		
		while (stree.size() > 0) 
		{
			FifteenPuzzle current = stree.pop();
			if (generated % 10000 == 0) {
				System.out.println(current);
				System.out.println("Wygenerowano: " + generated + 
						           " Rozmiar stosu: " + stree.size() + 
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
					stree.push(moved);
				}
			}
		}
		return null;
	}
	
	//Rozwiazuje 15 BFSem i drukuje rozwiazanie po kazdym zdjeciu ze stosu
	
	public static FifteenPuzzle BFS(FifteenPuzzle fp) {
		FifteenPuzzle.move.append("   ");
				int generated = 0;
				
		// rozwiazanie
				FifteenPuzzle solution = new FifteenPuzzle();
				
		//ile odwiedzilismy
				HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//zmieniamy na kolejke 
				Queue<FifteenPuzzle> qtree = new LinkedList<FifteenPuzzle>();
				qtree.add(fp);
				
				while (qtree.size() > 0) 
				{
					FifteenPuzzle current = qtree.poll();
					if (generated % 10000 == 0) {
						System.out.println(current);
						System.out.println("Wygenerowano: " + generated + 
						           " Rozmiar stosu: " + qtree.size() + 
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
							qtree.add(moved);
						}
					}
				}
				return null;
	}
	
	public static FifteenPuzzle AStar(FifteenPuzzle fp) {
		
		FifteenPuzzle.move.append("   ");
		// licznik ile mamy tablic do gry
		
		int generated = 0;
		
		// rozwiazanie
		
		HeuristicFifteenPuzzle solution = new HeuristicFifteenPuzzle();
		
		//ile odwiedzilismy
		HashSet<FifteenPuzzle> visited = new HashSet<FifteenPuzzle>();
		
		//wykorzystujemy kolejke priorytetowa i heurystyke
		PriorityQueue<HeuristicFifteenPuzzle> ptree = new PriorityQueue<HeuristicFifteenPuzzle>();
		ptree.add(new HeuristicFifteenPuzzle(fp));
		
		while (ptree.size() > 0) {
			HeuristicFifteenPuzzle current = ptree.poll();
			{
				System.out.println("\n"+current);
				System.out.println();
				System.out.println("Wygenerowano: " + generated + 
				           " Rozmiar stosu: " + ptree.size() + 
			               " Odwiedzono: " + visited.size());
				
			}
			
			if (current.equals(solution)) {
				System.out.println("Wykonane proby:\n"+FifteenPuzzle.move);
				System.out.println("Rozwiazanie:\n"+FifteenPuzzle.solution);
				return current;
				
			}
			for (int i = 0 ; i < 4 ; ++i) 
			{
				HeuristicFifteenPuzzle moved = current.clone();
				
				if (!moved.move(i)) {	
					FifteenPuzzle.solution.append(FifteenPuzzle.move.charAt(FifteenPuzzle.move.length()-1)+" ");
					FifteenPuzzle.move.deleteCharAt(FifteenPuzzle.move.length()-1);
					continue;
				}
				generated++;
				
				if (!visited.contains(moved)) {					
					visited.add(moved);
					ptree.add(moved);
				}
			}
			
		}
		return null;
	}
	
	public String moves = null;
	
	// FUNKCJA MAIN
	public static void main(String[] args) {
		FifteenPuzzle fp = new FifteenPuzzle();
		//fp.setFixedTableForTest();
	    
	    if(args.length==0){
	    	System.out.println("podaj argument uruchomienia! uzyj astar, bfs lub dfs");
	    }
	    else if(args[0].equals("astar")){
	    	fp.shuffle();
	    	fp.checkWhereIsZeroElement();
	    	Date now = new Date();
	    	System.out.println("\n"+AStar(fp));
	    	Date then = new Date();
	    	System.out.println("Czas wykonania : "+(then.getTime()-now.getTime()));
		}
	    else if(args[0].equals("dfs")){
	    	fp.shuffle();
	    	fp.checkWhereIsZeroElement();
	    	long now = System.currentTimeMillis();
	    	System.out.println("\n"+DFS(fp));
	    	long then = System.currentTimeMillis();
	    	System.out.println("Czas wykonania : "+(then-now));
		}
	    else if(args[0].equals("bfs")){
	    	fp.shuffle();
	    	fp.checkWhereIsZeroElement();
	    	long now = System.currentTimeMillis();
	    	System.out.println("\n"+BFS(fp));
	    	long then = System.currentTimeMillis();
	    	System.out.println("Czas wykonania : "+(then-now));
		}
	    else if(args[0].equals("file")&&args[1].length()!=0){
	    	fp.loadFromFile(args[1]);
	    	fp.checkWhereIsZeroElement();	    	
	    	long now = System.currentTimeMillis();
	    	System.out.println("\n"+AStar(fp));
	    	long then = System.currentTimeMillis();
			System.out.println("Czas wykonania : "+(then-now));
	    }
		else
		{
			System.out.println("zly argument! uzyj astar, bfs, dfs lub file nazwa_pliku");
		}
				
		
	}
}
