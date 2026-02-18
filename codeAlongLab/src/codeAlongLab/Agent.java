package codeAlongLab;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Agent implements Steppable {
	int x; //x coordinate
	int y; //y coordinate
	int dirx; //direction of x movement
	int diry; //direction of y movement
	
	
	public Agent(int x, int y, int dirx, int diry) {
		super();
		this.x = x;
		this.y = y;
		this.dirx = dirx;
		this.diry = diry;
	}
	
	public void move(Environment state) {
		if(state.random.nextBoolean(state.active)) {
			if(state.random.nextBoolean(state.p)) {
				dirx = state.random.nextInt(3)-1;
				diry = state.random.nextInt(3)-1;
			}
			placeAgent(state);
		}
	}
	
	public void placeAgent (Environment state) {
		if(state.oneAgentperCell) {
			int tempx = state.sparseSpace.stx(x + dirx);
			int tempy = state.sparseSpace.sty(y + diry);
			if(state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
				x = tempx;
				y = tempy;
				state.sparseSpace.setObjectLocation(this, x, y);
			}
		}
		else {
			x = state.sparseSpace.stx(x + dirx);
			y = state.sparseSpace.sty(y + diry);
			state.sparseSpace.setObjectLocation(this, x, y);
		}
	}
	
	public void aggregate(Environment state) {
		Bag neighbors = state.sparseSpace.getMooreNeighbors(x, y, state.searchRadius, state.sparseSpace.TOROIDAL, false);
		dirx = decidex(state,neighbors);
		diry = decidey(state,neighbors);
		placeAgent(state);
	}
	
	public int decidex(Environment state, Bag neighbors) {
		int posx =0, negx = 0;
		for(int i = 0; i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.x > this.x) {
				posx++;
			}
			else if(a.x < this.x) {
				negx ++;
			}
		}
		if(posx > negx) {
			return 1;
		}
		if(negx > posx) {
			return -1;
		}
		return 0;
	}
	public int decidey(Environment state, Bag neighbors) {
		int posy =0, negy = 0;
		for(int i = 0; i<neighbors.numObjs;i++) {
			Agent a = (Agent)neighbors.objs[i];
			if(a.y > this.y) {
				posy++;
			}
			else if(a.y < this.y) {
				negy ++;
			}
		}
		if(posy > negy) {
			return 1;
		}
		if(negy > posy) {
			return -1;
		}
		return 0;
	}
	


	public void step(SimState state) {
		Environment eState = (Environment)state;
		double probability = state.random.nextDouble(); //probability in the range [0,1]
		if(probability <= eState.aggregation) {
			aggregate(eState);
		}
		else {
			move(eState);
		}

	}

}
