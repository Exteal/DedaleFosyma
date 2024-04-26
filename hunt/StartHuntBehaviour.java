package hunt;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.OneShotBehaviour;
import myagents.MapAgent;

public class StartHuntBehaviour extends OneShotBehaviour {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4304293555853940654L;
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

}
