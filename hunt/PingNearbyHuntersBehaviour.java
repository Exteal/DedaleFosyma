package hunt;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import mapSharing.FollowerAgent;
import mapSharing.MapAgent;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.MovingStates;
import utils.Protocol;

public class PingNearbyHuntersBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4433608064654261299L;
	
	
	private List<String> allies;
	
	public PingNearbyHuntersBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);
		this.allies = allies;
		
	}
	
	@Override
	public void action() {
		
		
		
	}
}


