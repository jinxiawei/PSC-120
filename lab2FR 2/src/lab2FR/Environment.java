package lab2FR;

import spaces.Spaces;
import sweep.SimStateSweep;

public class Environment extends SimStateSweep {
    int n = 3000; //number of agent
    boolean oneAgentperCell = true; //only one agent per cell if true
    boolean boundedSpace = false; //bounded space if true else toroidal
    boolean broadRule = true; //broad rule if true else narrow rule
    int searchRadius = 1; //search radius for neighbors in broad rule
    double p = 1.0; //probability of random movement

   //Generate getters and setters using the Source menu

    public Environment(long seed, Class observer) {
       super(seed, observer);
    }


    public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public boolean isOneAgentperCell() {
		return oneAgentperCell;
	}

	public void setOneAgentperCell(boolean oneAgentperCell) {
		this.oneAgentperCell = oneAgentperCell;
	}

	public boolean isBoundedSpace() {
		return boundedSpace;
	}

	public void setBoundedSpace(boolean boundedSpace) {
		this.boundedSpace = boundedSpace;
	}

	public boolean isBroadRule() {
		return broadRule;
	}

	public void setBroadRule(boolean broadRule) {
		this.broadRule = broadRule;
	}

	public int getSearchRadius() {
		return searchRadius;
	}

	public void setSearchRadius(int searchRadius) {
		this.searchRadius = searchRadius;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public void makeAgents() {
       /* As in the code-along lab, we make the agents here. Remember
          that when oneAgentperCell = true, we need to check to make
          sure that there are not too many agents. Next create a single
          frozen agent at location gridWidth/2, gridHeight/2:
          Agent a = new Agent(x,y,dirx,diry,true);
          As in the code-along lab, you need to place it
          in space, but don't schedule it. It doesn't need to be stepped
          since it is already frozen: we will only schedule the other 
          n-1 agents. 
          As in the code-along lab, we use a for loop with int i = 0; 
          i < n-1; i++. 
          Create each agent:
          a = new Agent(x,y,dirx,diry,false);
          place each agent in space as in the code-along lab but this
          schedule it using:
          a.event = schedule.scheduleRepeating(a); */
		if (oneAgentperCell && n > gridWidth * gridHeight) {
            System.out.println("there are too many agents");
            return;
        }
		int cx = gridWidth / 2;
        int cy = gridHeight / 2;
        Agent frozen = new Agent(cx, cy, 0, 0, true);
        sparseSpace.setObjectLocation(frozen, cx, cy);
        
        for (int i = 0; i < n - 1; i++) {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            while (oneAgentperCell && sparseSpace.getObjectsAtLocation(x, y) != null) {
                x = random.nextInt(gridWidth);
                y = random.nextInt(gridHeight);
            }
            int dirx = random.nextInt(3) - 1;
            int diry = random.nextInt(3) - 1;
            Agent a = new Agent(x, y, dirx, diry, false);
            sparseSpace.setObjectLocation(a, x, y);
            a.event = schedule.scheduleRepeating(a); // 保存event用于之后stop
        }
		
    }

    public void start() {
       super.start();
       /* Make a sparseSpace as in the code-along lab and then 
          make the agents. */
       this.make2DSpace(Spaces.SPARSE, gridWidth, gridHeight);
       makeAgents();
    }
}
