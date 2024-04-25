package mapSharing;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import hunt.StartHuntBehaviour;
import jade.core.behaviours.FSMBehaviour;
import test.TargetedMoveBehaviour;
import utils.MovingStates;
import utils.Protocol;

public class FollowerExploringBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7203027947007703479L;
	
	public FollowerExploringBehaviour(AbstractDedaleAgent agent) {
		super(agent);
		
		List<String> allies = ((MapAgent)this.myAgent).listAllies();
		
		//this.registerFirstState(new RandomMoveBehaviour(((AbstractDedaleAgent)this.myAgent), allies), MovingStates.Random.label);
		this.registerFirstState(new TargetedMoveBehaviour(((AbstractDedaleAgent)this.myAgent), allies), MovingStates.Targeted.label);

		
		
		this.registerState(new PingNearbyBehaviour(agent, allies), Protocol.PING.label);
		this.registerState(new ShareMapBehaviour((AbstractDedaleAgent)this.myAgent, ((MapAgent)this.myAgent).listAllies()), Protocol.SHARE_MAP.label);
		this.registerState(new WaitReplyBehaviour(((AbstractDedaleAgent)this.myAgent), ((MapAgent)this.myAgent).listAllies()), Protocol.WAIT_REPLY.label);
		
		this.registerLastState(new StartHuntBehaviour((AbstractDedaleAgent)this.myAgent, allies), MovingStates.StartHunt.label);

		this.registerDefaultTransition(MovingStates.Targeted.label, Protocol.PING.label);
		
		this.registerTransition(MovingStates.Targeted.label, MovingStates.StartHunt.label, MovingStates.StartHunt.number);
		
		this.registerDefaultTransition(Protocol.PING.label, Protocol.WAIT_REPLY.label);
		
		this.registerDefaultTransition(Protocol.WAIT_REPLY.label, MovingStates.Targeted.label);

		this.registerTransition(Protocol.WAIT_REPLY.label, Protocol.SHARE_MAP.label, Protocol.SHARE_MAP.number);

		
		this.registerDefaultTransition(Protocol.SHARE_MAP.label, MovingStates.Targeted.label);
		
				
			
	}	
}
