package codeAlongLab;

import java.awt.Color;

import spaces.Spaces;
import sweep.GUIStateSweep;
import sweep.SimStateSweep;

public class AgentUI extends GUIStateSweep {

	public AgentUI(SimStateSweep state, int gridWidth, int gridHeight, Color backdrop, Color agentDefaultColor,
			boolean agentPortrayal) {
		super(state, gridWidth, gridHeight, backdrop, agentDefaultColor, agentPortrayal);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		AgentUI.initialize(Environment.class, null, AgentUI.class, 600, 600, Color.WHITE, Color.BLUE, true, Spaces.SPARSE);

	}

}
