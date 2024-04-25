package hunt;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import mapSharing.MapAgent;

public class StartHuntBehaviour extends SimpleBehaviour {
	private List<String> allies;

	public StartHuntBehaviour(AbstractDedaleAgent ag, List<String> allies) {
		super(ag);
		this.allies = allies;
	}
	
	@Override
	public void action() {
		System.out.println("debut chasse agent " + myAgent.getLocalName());
		this.myAgent.addBehaviour( new FollowerHuntBehaviour((MapAgent)this.myAgent, allies));
		this.myAgent.addBehaviour( new RespondPingHuntBehaviour((MapAgent)this.myAgent));
		
	}

	@Override
	public boolean done() {
		return true;
	}

}
