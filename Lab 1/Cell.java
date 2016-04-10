public class Cell {
	private boolean alive;
	
	public Cell(boolean alive){
		this.alive = alive;
	}
	
	public boolean isAlive(){
		return this.alive;
	}
	
	public void populate(){
		this.alive = true;
	}
	
	public void kill(){
		this.alive = false;
	}
}
