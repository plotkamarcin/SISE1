
public class HeuristicFifteenPuzzle extends FifteenPuzzle 
                                    implements Comparable<HeuristicFifteenPuzzle> {
	private int myPathCost;
	private int myHeuristicValue;
	
	public HeuristicFifteenPuzzle() {
		super();
		myPathCost = 0;
		myHeuristicValue = 0;
	}
	
	public HeuristicFifteenPuzzle(FifteenPuzzle fp) {
		super();
		
		myBoard = fp.myBoard;
		myEmptyRow = fp.myEmptyRow;
		myEmptyCol = fp.myEmptyCol;
		
		// Setting path cast to zero means that you should _only_ 
		// use this when initializing your very first board.
		myPathCost = 0;
		computeHeuristic();
	}
	
	@Override
	public boolean move(int d) {
		if (super.move(d)) {
			myPathCost++;	
			computeHeuristic();
			return true;
		}
		return false;
	}
	
	private int[] findInt(int i) {
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				if (myBoard[r][c] == i) {
					int[] res = {r, c};
					return res;
				}
			}
		}
		return null;
	}
	
	private void computeHeuristic() {
		myHeuristicValue = 0;
		// heurystyka manhattan
		int count = 1;
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				if (count == 16) {
					continue;
				}
				int[] at = findInt(count);
				myHeuristicValue += Math.abs(r - at[0]) + Math.abs(c - at[1]);
				count++;
			}
		}
		int[] at = findInt(0);
		myHeuristicValue += Math.abs(3 - at[0]) + Math.abs(3 - at[1]);
	}
	
	public int compareTo(HeuristicFifteenPuzzle other) {
		int mine = myPathCost + myHeuristicValue;
		int yours = other.myPathCost + other.myHeuristicValue;
		if (mine < yours) {
			return -1;
		} else if (mine == yours) {
			return 0;
		} else {
			return 1;
		}
	}
	
	public HeuristicFifteenPuzzle clone() {
		HeuristicFifteenPuzzle result = new HeuristicFifteenPuzzle();
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				result.myBoard[r][c] = myBoard[r][c];
			}
		}
		result.myEmptyCol = myEmptyCol;
		result.myEmptyRow = myEmptyRow;
		result.myPathCost = myPathCost;
		result.myHeuristicValue = myHeuristicValue;
		return result;
	}
	
	public String toString() {
		String s = super.toString();
		return s + "Koszt: " + myPathCost + " Heurystyka: " + myHeuristicValue;
	}
}
