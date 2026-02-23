package lab2FR;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;

public class Agent implements Steppable {
    int x;
    int y;
    int dirx;
    int diry;
    boolean frozen = false;
    Stoppable event; //use this to remove an agent from the 
                     //schedule by calling event.stop();

    //public Agent(int x, int y, int dirx, int diry, boolean frozen) {
      /*Constructor method. Use source menu --> 
                        Generate Contructor using Fields...*/
    //}

    public Agent(int x, int y, int dirx, int diry, boolean frozen) {
						super();
						this.x = x;
						this.y = y;
						this.dirx = dirx;
						this.diry = diry;
						this.frozen = frozen;
					}


	public void move (Environment state) {
     /*Movement is conditional on the probability of movement, state.p. 
      If true, then asign new random directions to dirx and diry as 
      we did in the code along lab. And, as in the code-along lab
      use the placeAgent method to place the agent in space. */
		if (state.random.nextBoolean(state.p)) {
            dirx = state.random.nextInt(3) - 1;
            diry = state.random.nextInt(3) - 1;
        }
        placeAgent(state);
    }

    public int bx(Environment state, int x) {
      /*This method makes sure that x is in the bounds of space, which
        implies that 0 <= x < state.gridWidth. So, if x < 0, it should
        return 0, if x >= state.gridWidth, it should return 
        state.gridWidth - 1, else return x. This is the bounded 
        version of state.sparseSpace.stx(x) */
    	if (x < 0) return 0;
        if (x >= state.gridWidth) return state.gridWidth - 1;
        return x;
    }

    public int by(Environment state, int y) {
       /* This follows the same logic as in bx, except for y and 
          state.gridHeight */
    	if (y < 0) return 0;
        if (y >= state.gridHeight) return state.gridHeight - 1;
        return y;
    }

    public void broadRule(Environment state) {
       /* Once an agent has moved it must get its neighbors and determine
          if at least one of its neighbors is frozen, then it freezes.  
          See the broad rule above for using 
          state.sparseSpace.getMooreNeighbors 
          to get the neighbors of an in toroidal and bounded spaces.
          Once you get a bag of neighbors, search through
          them with a for statement. If a frozen agent is 
          encountered, set this.frozen = true, 
          and remove this agent from the schedule with 
          this.event.stop(). Finally, stop
          the loop with a "break;" statement. (Hint: see
          the narrow rule, but with:
          Bag b = state.sparseSpace.getMooreNeighbors(.....)
          */
    	Bag neighbors;
        if (state.boundedSpace) {
            neighbors = state.sparseSpace.getMooreNeighbors(
                x, y, state.searchRadius, state.sparseSpace.BOUNDED, false);
        } else {
            neighbors = state.sparseSpace.getMooreNeighbors(
                x, y, state.searchRadius, state.sparseSpace.TOROIDAL, false);
        }
        for (int i = 0; i < neighbors.numObjs; i++) {
            Agent a = (Agent) neighbors.objs[i];
            if (a.frozen) {
                this.frozen = true;
                this.event.stop();
                break;
            }
        }
    }

    public void narrowRule(Environment state) {
       /* After an agent has moved, the agent determines if an agent
          in the direction it was moving is frozen. To do this, 
          the agent calculates nextx and nexty for either  
          bounded or toroidal spaces. For bounded spaces we get:
              nextx = bx(state, x+dirx);
              nexty = by(state, y+diry);
          For toroidal spaces we get
              nextx = state.sparseSpace.stx(x+dirx);
              nexty = state.sparseSpace.sty(y+diry);
          Once you have calculated nextx and nexty, you can use the narrow 
          rule algorithm above to determine if this agent freezes:

          Bag b = state.sparseSpace.getObjectsAtLocation(nextx, nexty);
          if(b != null) {
            for(int i = 0; i<b.numObjs;i++) {
                Agent a = (Agent)b.objs[i];
                if(a.frozen) {
                    this.frozen=true;
                    this.event.stop();
                    break;
                }
            }*/
    	int nextx, nexty;
        if (state.boundedSpace) {
            nextx = bx(state, x + dirx);
            nexty = by(state, y + diry);
        } else {
            nextx = state.sparseSpace.stx(x + dirx);
            nexty = state.sparseSpace.sty(y + diry);
        }
        Bag b = state.sparseSpace.getObjectsAtLocation(nextx, nexty);
        if (b != null) {
            for (int i = 0; i < b.numObjs; i++) {
                Agent a = (Agent) b.objs[i];
                if (a.frozen) {
                    this.frozen = true;
                    this.event.stop();
                    break;
                }
            }
        }
    }

    public void placeAgent(Environment state) {
     /* This method does most of the work. When state.oneAgentperCell
        = true, recall we calculate tempx and tempy and only place
        the agent in a new location if that location is empty (null,
        see the code-along lab). You will have to consider both
        bounded and toroidal for placing an agent at a location:

        if(state.oneAgentperCell) {
            if(state.boundedSpace) {
            }
            else {//Toroidal space
            }
        else { //more than one agent allowed
            if(state.boundedSpace) {
            }
           else { //Toroidal space
           }
        }

        After the agent is placed (or not), it is time to check for
        frozen agents. You should have an if-then statement using
        state.broadRule, which if true call the broadrule method
        else the narrow rule: */
    	if (state.oneAgentperCell) {
            int tempx, tempy;
            if (state.boundedSpace) {
                tempx = bx(state, x + dirx);
                tempy = by(state, y + diry);
            } else {
                tempx = state.sparseSpace.stx(x + dirx);
                tempy = state.sparseSpace.sty(y + diry);
            }
            if (state.sparseSpace.getObjectsAtLocation(tempx, tempy) == null) {
                x = tempx;
                y = tempy;
                state.sparseSpace.setObjectLocation(this, x, y);
            }
        } else {
            if (state.boundedSpace) {
                x = bx(state, x + dirx);
                y = by(state, y + diry);
            } else {
                x = state.sparseSpace.stx(x + dirx);
                y = state.sparseSpace.sty(y + diry);
            }
            state.sparseSpace.setObjectLocation(this, x, y);
        }

        if(state.broadRule) {
           broadRule(state);
        }
        else {
           narrowRule(state);
        }
    }

    public void step(SimState state) {
      /*Here is where the move method goes*/
    	Environment eState = (Environment) state;
        move(eState);
    }
}