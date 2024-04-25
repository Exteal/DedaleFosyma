package mapSharing;

import java.util.List;

import jade.core.behaviours.Behaviour;

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
