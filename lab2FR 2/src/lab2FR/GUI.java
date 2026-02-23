package lab2FR;

import java.awt.Color;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class GUI extends GUIStateSweep {

	
	public GUI(SimStateSweep state, int gridWidth, int gridHeight,
            Color backdrop, Color agentDefaultColor, boolean agentPortrayal) {
super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
}

public static void main(String[] args) {
GUI.initialize(
 Environment.class, null, GUI.class,
 600, 600, Color.WHITE, Color.BLACK, true, Spaces.SPARSE
);
}
}
