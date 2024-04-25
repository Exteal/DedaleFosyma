package toDo;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;

public class FollowEnnemyBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5891506654334660955L;
	private boolean finished = false;
	
	public FollowEnnemyBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}
	
	@Override
	public void action() {
		//this.myAgent.removeBehaviour();
	}

	@Override
	public boolean done() {
		return finished;
	}

}
