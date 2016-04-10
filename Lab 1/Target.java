
public class Target{
	int listIndex;
	int cellIndex;
	private boolean kill;
	
	public Target(int listIndex, int cellIndex, boolean kill){
		this.listIndex = listIndex;
		this.cellIndex = cellIndex;
		this.kill = kill;
	}
	
	public boolean killCell(){
		return this.kill;
	}
}
