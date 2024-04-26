package test;

import java.util.List;

import exploration.MergeMapBehaviour;
import exploration.RespondPingBehaviour;
import jade.core.behaviours.Behaviour;
import myagents.MapAgent;

public class TestMergeAgent extends MapAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6781650893149055416L;
	
	@Override
	protected List<Behaviour> listCommonBehaviours() {
		return List.of(
					new RespondPingBehaviour(this),
					new MergeMapBehaviour(this)
				);
	}

	@Override
	protected List<Behaviour> listSpecificBehaviours() {
		return List.of();
	}

	

}
