
public class Conway{
	static private Grid grid = new Grid(); 
	
	public static void main(String[] args){
		// find the provided input file path
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-i")){
				grid.create(args[i+1]);
				break;
			}
		}
		
		int nrOfGenerations = 0;
		
		// check iteration number validity
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-n")){
				try { nrOfGenerations = Integer.parseInt(args[i+1]); }  
				catch(NumberFormatException nfe)
				{ 
					System.out.println("A non-numeric value has been provided for nr of iterations!");
					System.exit(-1);
				}
			}
		}
		
		
		for(int i = 0; i < nrOfGenerations; i++){
			grid.process();
			if(grid.size() == 2) break;
		}
		
		// find the provided output file path
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-o")){
				grid.writeToFile(args[i+1]);
				break;
			}
		}
		
		System.out.println("Done!");
	}
}
