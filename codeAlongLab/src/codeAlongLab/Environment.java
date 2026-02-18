package codeAlongLab;

import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
	int n =100; //The number of agents
	double active = 1.0; //the probability an agent is active
	double p = 1.0; //probability of random movement
	boolean oneAgentperCell = true; //If true only one agent per location or cell
	double aggregation = 0.0;//probability of aggregating
	int searchRadius = 2; //The radius that an agent can see around itself


	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getActive() {
		return active;
	}

	public void setActive(double active) {
		this.active = active;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public boolean isOneAgentperCell() {
		return oneAgentperCell;
	}

	public void setOneAgentperCell(boolean oneAgentperCell) {
		this.oneAgentperCell = oneAgentperCell;
	}

	public double getAggregation() {
		return aggregation;
	}

	public void setAggregation(double aggregation) {
		this.aggregation = aggregation;
	}

	public int getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}

	public Environment(long seed, Class observer) {
		super(seed, observer);
	}

	public void makeAgents() {
		if(oneAgentperCell && n > gridWidth * gridHeight) {
			System.out.println("Too many agents for the space!");
			return;
		}
		else {
			for(int i=0;i<n;i++) {
				int x = random.nextInt(gridWidth);
				int y = random.nextInt(gridHeight);
				while(oneAgentperCell && sparseSpace.getObjectsAtLocation(x, y) != null) {
					x = random.nextInt(gridWidth);
					y = random.nextInt(gridHeight);
				}
				int dirx = random.nextInt(3)-1;
				int diry = random.nextInt(3)-1;
				Agent a = new Agent(x,y,dirx,diry);
				sparseSpace.setObjectLocation(a, x, y);
				schedule.scheduleRepeating(a);
			}
		}
	}

	public void start() {
		super.start();
		this.make2DSpace(Spaces.SPARSE, gridWidth, gridHeight);
		makeAgents();
	}
}
