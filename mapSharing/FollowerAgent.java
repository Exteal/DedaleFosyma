package mapSharing;

import java.util.List;
import hunt.Observation;
import jade.core.behaviours.Behaviour;
import mine.PackAgent;
import utils.MovingStates;

public class FollowerAgent extends PackAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 903555356744520196L;
	
	private String leader;
	private int movingValue = MovingStates.Targeted.number;
	
	public int getMovingValue() {
		return movingValue;
	}
	
	public void setMovingValue(int movingValue) {
		this.movingValue = movingValue;
	}
	
	@Override
	protected List<Behaviour> listSpecificBehaviours() {
		return List.of(new FollowerExploringBehaviour(this));
				//new ShareMapBehaviour(this, this.listAllies()),
			//new RespondPingBehaviour(this));
				//new PrintKnowledgeBehaviour(this, 4000));
			//new MergeMapBehaviour(this));
		//new MustStartFollowingBehaviour(this));
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}
	
	public String getLeader() {
		return this.leader;
	}
}
