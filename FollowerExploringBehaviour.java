package mine;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.FSMBehaviour;

public class FollowerExploringBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7203027947007703479L;
	
	public FollowerExploringBehaviour(AbstractDedaleAgent agent) {
		super(agent);
		
		
		this.registerFirstState(new RandomMoveBehaviour(((AbstractDedaleAgent)this.myAgent), ((PackAgent)this.myAgent).listAllies()), MovingStates.Random.getStateName());
		this.registerLastState(new FollowLeaderBehaviour(((AbstractDedaleAgent)this.myAgent)), MovingStates.FollowingLeader.getStateName());
		this.registerLastState(new FollowEnnemyBehaviour((AbstractDedaleAgent) this.myAgent), MovingStates.FollowingEnnemy.getStateName());
		
		
		this.registerTransition(MovingStates.Random.getStateName(), MovingStates.FollowingLeader.getStateName(), MovingStates.FollowingLeader.getStateInt());
		this.registerTransition(MovingStates.Random.getStateName(), MovingStates.FollowingEnnemy.getStateName(), MovingStates.FollowingEnnemy.getStateInt());
	
	}	
}
