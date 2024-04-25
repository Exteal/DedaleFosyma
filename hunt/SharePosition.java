package hunt;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.OneShotBehaviour;
import mapSharing.MapAgent;

public class SharePosition extends OneShotBehaviour {

	
	public SharePosition(AbstractDedaleAgent agent) {
		super(agent);
	}
	
	
	@Override
	public void action() {
		((MapAgent) this.myAgent).getObservations();
		
	}

	
	
}
