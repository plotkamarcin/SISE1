import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class FifteenPuzzle {
	
	// tablica do gry.
	protected int[][] myBoard;
	// zapamietujemy gdzie jest puste.
	protected int myEmptyRow;
	protected int myEmptyCol;
	
	protected static StringBuilder move = new StringBuilder();
	protected static StringBuilder solution = new StringBuilder();
	private int seed = (int)System.currentTimeMillis();
	// tworzy rozwiazanie.
	FifteenPuzzle() {
			
		myBoard = new int[4][4];
		int count = 1;
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				myBoard[r][c] = count;
				count++;
			}
		}
		
		// ostatnia komorka to 0
		myBoard[3][3] = 0;
		myEmptyRow = 3;
		myEmptyCol = 3;
	}
	
	// wyswietla tablice 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				if (myBoard[r][c] == 0) {
					sb.append(" X"); // zamienia 0 na X zeby sie odroznialo
				} else if (myBoard[r][c] < 10) {
					sb.append(" ");
					sb.append(myBoard[r][c]);
				} else {
					sb.append(myBoard[r][c]);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	//przesuwa puste miejsce w tablicy 0 to "up", 
	// 1 to "right", 2 to "down", a 3 to "left."
	// 
	// sprawdza czy ruch byl dozwolony i jesli nie zwraca falsz
	public boolean move(int dir) {
		if (dir < 0 || dir > 3) {
			System.out.println("Niedozwolony ruch!");
			return false;
		}
		
		// w gore
		if (dir == 0 && myEmptyRow > 0) {
			int a = myBoard[myEmptyRow][myEmptyCol];
			myBoard[myEmptyRow][myEmptyCol] = myBoard[myEmptyRow-1][myEmptyCol];
			myBoard[myEmptyRow-1][myEmptyCol] = a;
			myEmptyRow--;
			move.append(" U");
			return true;
		}
		// w prawo
		if (dir == 1 && myEmptyCol < 3) {
			int a = myBoard[myEmptyRow][myEmptyCol];
			myBoard[myEmptyRow][myEmptyCol] = myBoard[myEmptyRow][myEmptyCol+1];
			myBoard[myEmptyRow][myEmptyCol+1] = a;
			myEmptyCol++;
			move.append(" R");
			return true;
		}
		// w dol
		if (dir == 2 && myEmptyRow < 3) {
			int a = myBoard[myEmptyRow][myEmptyCol];
			myBoard[myEmptyRow][myEmptyCol] = myBoard[myEmptyRow+1][myEmptyCol];
			myBoard[myEmptyRow+1][myEmptyCol] = a;
			myEmptyRow++;
			move.append(" D");
			return true;
		}
		// w lewo
		if (dir == 3 && myEmptyCol > 0) {
			int a = myBoard[myEmptyRow][myEmptyCol];
			myBoard[myEmptyRow][myEmptyCol] = myBoard[myEmptyRow][myEmptyCol-1];
			myBoard[myEmptyRow][myEmptyCol-1] = a;
			myEmptyCol--;
			move.append(" L");
			return true;
		}
		return false;
	}
	// miesza tablice
	
	void shuffle() {
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0 ; i < 100 ; ++i) {
			move(r.nextInt(4));
		}
		move.setLength(0); //zeruje ruchy po przemieszaniu
	}
	
	void setFixedTableForTest(){
        myBoard[0][0]=1;
        myBoard[0][1]=2;
        myBoard[0][2]=3;
        myBoard[0][3]=4;
        myBoard[1][0]=5;
        myBoard[1][1]=6;
        myBoard[1][2]=7;
        myBoard[1][3]=0;
        myBoard[2][0]=8;
        myBoard[2][1]=9;
        myBoard[2][2]=10;
        myBoard[2][3]=11;
        myBoard[3][0]=12;
        myBoard[3][1]=13;
        myBoard[3][2]=14;
        myBoard[3][3]=15;

		myEmptyCol=3;
		myEmptyRow=1;
	}
	//sprawdz gdzie jest zero
	public void checkWhereIsZeroElement(){
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				if(myBoard[r][c] ==0){
					myEmptyCol=c;
					myEmptyRow=r;
				}
				
			}
		}
		
	}
	
	
	//wczytaj z pliku
	public void loadFromFile(String filename){
		try {
			File file = new File(filename);
			Scanner fileScanner = new Scanner(file);
				
			System.out.println("Wczytano: "+file.getAbsolutePath());
			while(fileScanner.hasNext()!=false){
			for (int r = 0 ; r < 4 ; ++r) {
				for (int c = 0 ; c < 4 ; ++c) {
					myBoard[r][c] = fileScanner.nextInt();
				}
			}
			}
			fileScanner.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//kopiuje tablice
	public FifteenPuzzle clone() {
		FifteenPuzzle result = new FifteenPuzzle();
		for (int r = 0 ; r < 4 ; ++r) {
			for (int c = 0 ; c < 4 ; ++c) {
				result.myBoard[r][c] = myBoard[r][c];
			}
		}
		result.myEmptyCol = myEmptyCol;
		result.myEmptyRow = myEmptyRow;
		return result;
	}
	
	// funkcje do porownywania
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(myBoard);
		result = prime * result + myEmptyCol;
		result = prime * result + myEmptyRow;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FifteenPuzzle other = (FifteenPuzzle) obj;
		if (!Arrays.deepEquals(myBoard, other.myBoard))
			return false;
		if (myEmptyCol != other.myEmptyCol)
			return false;
		if (myEmptyRow != other.myEmptyRow)
			return false;
		return true;
	}
}
