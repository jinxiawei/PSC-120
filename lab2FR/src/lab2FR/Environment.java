package lab2FR;

import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
   //parametes
	
	public Environment(long seed) {
		super(seed);
		// TODO Auto-generated constructor stub
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
		// TODO Auto-generated constructor stub
	}

	public Environment(long seed, Class observer, String runTimeFileName) {
		super(seed, observer, runTimeFileName);
		// TODO Auto-generated constructor stub
	}
	
	public void makeAgents() {
		//this code to do
	}
	
	public void start() {
		super.start();
		//our own code here
		this.make2DSpace(Spaces.SPARSE, gridWidth, gridHeight);
		makeAgents();
	}
}
