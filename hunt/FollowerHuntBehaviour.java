package hunt;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.FSMBehaviour;
import myagents.MapAgent;
import utils.MovingStates;

public class FollowerHuntBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7203027947007703479L;
		
	public FollowerHuntBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);

		this.registerFirstState(new SearchEnnemyBehaviour(((MapAgent)this.myAgent)), MovingStates.SearchEnnemy.label);
				
		this.registerState(new FollowStenchBehaviour(((MapAgent)this.myAgent)), MovingStates.FollowingStench.label);
		this.registerState(new BlockingEnnemyBehaviour(((MapAgent)this.myAgent), allies), MovingStates.BlockingEnnemy.label);
		this.registerLastState(new FinalBehaviour(((MapAgent)this.myAgent)), MovingStates.Final.label);
		
		this.registerDefaultTransition(MovingStates.SearchEnnemy.label, MovingStates.SearchEnnemy.label);
		this.registerTransition(MovingStates.SearchEnnemy.label, MovingStates.FollowingStench.label, MovingStates.FollowingStench.number);
		
		this.registerTransition(MovingStates.FollowingStench.label, MovingStates.SearchEnnemy.label, MovingStates.SearchEnnemy.number);
		this.registerTransition(MovingStates.FollowingStench.label, MovingStates.FollowingStench.label, MovingStates.FollowingStench.number);
		this.registerTransition(MovingStates.FollowingStench.label, MovingStates.BlockingEnnemy.label, MovingStates.BlockingEnnemy.number);
	
		this.registerTransition(MovingStates.BlockingEnnemy.label, MovingStates.Final.label, MovingStates.Final.number);
		this.registerTransition(MovingStates.BlockingEnnemy.label, MovingStates.SearchEnnemy.label, MovingStates.SearchEnnemy.number);
		this.registerTransition(MovingStates.BlockingEnnemy.label, MovingStates.BlockingEnnemy.label, MovingStates.BlockingEnnemy.number);


	}
}