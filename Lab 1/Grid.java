import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
 * dies = 1 or >=4 neighbors alive
 * populate = if 3 populated neighbors 
 * 
 */

public class Grid extends ArrayList<ArrayList<Cell>>{
	private static final long serialVersionUID = 1L;
	private static final int UP = 0;
	private static final int RIGHT = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;

	public void create(String inputFilePath){
		try {
				BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
				while (true) {
					ArrayList<Cell> row = new ArrayList<Cell>();
				    String line = reader.readLine();
				    if (line == null) break;
				    char[] chars = line.toCharArray();
				    for(int i = 0; i < chars.length; i++){
				    	boolean alive = (chars[i] == '1') ? true : false;
				    	row.add(new Cell(alive));
				    }
				    this.add(row);
				}
				reader.close();
				
				// create dummy rings
				expand(UP, RIGHT, DOWN, LEFT);
				expand(UP, RIGHT, DOWN, LEFT);
				
				for(ArrayList<Cell> list : this){
					for(Cell c : list){
						if(c.isAlive()) System.out.print("1");
						else System.out.print("0");
					}
					System.out.println();
				}
		}catch(IOException e) { 
			System.out.println(e.getMessage()); 
			System.exit(-1);
		}
	}
	
	
	public void process(){
		ArrayList<Target> targets = new ArrayList<Target>();
		
		for(int i = 1; i < this.size()-1; i++)
			for(int j = 1; j < this.get(i).size() - 1; j++){
				int neighbors = countNeighbors(i,j);
				
				if(this.get(i).get(j).isAlive()){
					if(neighbors < 2 || neighbors > 3)
						targets.add(new Target(i, j, true));
				}
				else{
					if(neighbors == 3) targets.add(new Target(i, j, false));
				}
			}
		
		dealWithTargets(targets);
		normalize();
	}
	
	
	private int countNeighbors(int listIndex, int cellIndex){
		int count = 0;
		
		for(int up=0; up < 3; up++)
			if(this.get(listIndex - 1).get(cellIndex - 1 + up).isAlive()) count++;
		
		for(int down=0; down < 3; down++)
			if(this.get(listIndex + 1).get(cellIndex - 1 + down).isAlive()) count++;
		
		if(this.get(listIndex).get(cellIndex - 1).isAlive()) count++;
		if(this.get(listIndex).get(cellIndex + 1).isAlive()) count++;
		return count;
	}
	
	
	private void expand(int... direction){
		for(int d : direction)
			switch(d){
				case UP:
					// prepend to the grid a new ArrayList with size equal
					// to the next ArrayList with dead cells
					ArrayList<Cell> expandUP = new ArrayList<Cell>();
					for(int i = 0; i < this.get(0).size(); i++)
						expandUP.add(new Cell(false));
					this.add(0, expandUP);
					break;
					
				case RIGHT:
					for(int i = 0; i < this.size(); i++)
						this.get(i).add(new Cell(false));
					break;
					
				case DOWN:
					// append to the grid a new ArrayList with size equal
					// to the previous ArrayList with dead cells
					ArrayList<Cell> expandDOWN = new ArrayList<Cell>();
					for(int i = 0; i < this.get(this.size() - 1).size(); i++)
						expandDOWN.add(new Cell(false));
					this.add(expandDOWN);
					break;
					
				case LEFT:
					for(int i = 0; i < this.size(); i++)
						this.get(i).add(0, new Cell(false));
					break;
			}
	}
	
	
	private void contract(int... direction){
		for(int d : direction)
			switch(d){
			case UP: if(!this.isEmpty()) this.remove(0); break;
				
			case RIGHT:
				for(ArrayList<Cell> row : this)
					if(!row.isEmpty())row.remove(row.size() - 1);
				break;
				
			case DOWN: if(!this.isEmpty()) this.remove(this.size() - 1); break;
				
			case LEFT:
				for(ArrayList<Cell> row : this)
					if(!row.isEmpty()) row.remove(0);
				break;
			}
	}
	
	
	private void normalize(){
				if(containsAliveCells(this.get(1)))
					expand(UP);
				else if(this.size()>2 && !containsAliveCells(this.get(2))) 
					contract(UP);
				if(containsAliveCells(this.get(this.size()-2)))
					expand(DOWN);
				else if(this.size()>2 && !containsAliveCells(this.get(this.size()-3))) 
					contract(DOWN);
						
				boolean expandLEFT = false;
				boolean expandRIGHT = false;
				boolean contractLEFT = true;
				boolean contractRIGHT = true;
						
				for(ArrayList<Cell> list : this){
					if(list.size() > 1){
						if(list.get(1).isAlive()) {
							expandLEFT = true;
							contractLEFT = false;
							break;
						}
						else if(list.size() > 2)
								contractLEFT &= !list.get(2).isAlive();
					}
				}
				
				for(ArrayList<Cell> list : this){
					if(list.size() > 1){
						if(list.get(list.size()-2).isAlive()) {
							expandRIGHT = true;
							contractRIGHT = false;
							break;
						}
						else if(list.size() > 2)
							contractRIGHT &= !list.get(list.size()-3).isAlive();
					}
				}
						
				if(expandLEFT) expand(LEFT);
				else if(contractLEFT) { contract(LEFT); System.out.println("contract left"); }
				if(expandRIGHT) expand(RIGHT);
				else if(contractRIGHT) contract(RIGHT);
	}
	
	
	// checks if a row from the grid has at least 1 alive cell
	private boolean containsAliveCells(ArrayList<Cell> list){
		for(Cell c : list)
			if(c.isAlive()) return true;
		
		return false;
	}
	
	
	private void dealWithTargets(ArrayList<Target> targets){
		for(Target t : targets){
			if(t.killCell())  this.get(t.listIndex).get(t.cellIndex).kill();
			else this.get(t.listIndex).get(t.cellIndex).populate();
		}
	}
	
	
	public void writeToFile(String out){
			contract(UP, RIGHT, DOWN, LEFT);
			contract(UP, RIGHT, DOWN, LEFT);
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new FileOutputStream(out));
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
			 for (ArrayList<Cell> list : this){
				for(Cell c : list)
					if(c.isAlive()) pw.print("1");
					else pw.print("0");
			     pw.println("\n");
			 }
			 pw.close();
	}

}
