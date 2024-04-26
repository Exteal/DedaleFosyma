package exploration;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import hunt.StartHuntBehaviour;
import jade.core.behaviours.FSMBehaviour;
import myagents.MapAgent;
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
		this.registerFirstState(new TargetedMoveBehaviour(((AbstractDedaleAgent)this.myAgent)), MovingStates.Targeted.label);		
		
		this.registerState(new PingNearbyBehaviour((AbstractDedaleAgent)this.myAgent, allies), Protocol.PING.label);
		this.registerState(new ShareMapBehaviour((AbstractDedaleAgent)this.myAgent), Protocol.SHARE_MAP.label);
		this.registerState(new WaitReplyBehaviour(((AbstractDedaleAgent)this.myAgent)), Protocol.WAIT_REPLY.label);
		this.registerState(new WaitResendMapBehaviour((AbstractDedaleAgent)this.myAgent, ((MapAgent)this.myAgent).listAllies()), Protocol.WAIT_RESEND_MAP.label);
	
		this.registerLastState(new StartHuntBehaviour((AbstractDedaleAgent)this.myAgent, allies), MovingStates.StartHunt.label);

	
		
		this.registerDefaultTransition(MovingStates.Targeted.label, Protocol.PING.label);
		
		this.registerTransition(MovingStates.Targeted.label, MovingStates.StartHunt.label, MovingStates.StartHunt.number);
		
		this.registerDefaultTransition(Protocol.PING.label, Protocol.WAIT_REPLY.label);
		
		this.registerDefaultTransition(Protocol.WAIT_REPLY.label, MovingStates.Targeted.label);

		this.registerTransition(Protocol.WAIT_REPLY.label, Protocol.SHARE_MAP.label, Protocol.SHARE_MAP.number);

		
		this.registerDefaultTransition(Protocol.SHARE_MAP.label, Protocol.WAIT_RESEND_MAP.label);
		
		this.registerDefaultTransition(Protocol.WAIT_RESEND_MAP.label, MovingStates.Targeted.label);
		
				
			
	}	
}
