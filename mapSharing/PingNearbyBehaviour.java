package mapSharing;

import java.util.List;

import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.Protocol;

public class PingNearbyBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4433608064654261299L;
	private List<String> allies;
	
	public PingNearbyBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);
		this.allies = allies;
		
	}
	
	@Override
	public void action() {
		
		Location myPosition = ((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		if (myPosition!=null && myPosition.getLocationId()!=""){

			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setSender(this.myAgent.getAID());
			msg.setProtocol(Protocol.PING.label);
			
			for (String ally : allies) {
				msg.addReceiver(new AID(ally, AID.ISLOCALNAME));
			}
			

			//System.out.println(this.myAgent.getAID().getLocalName() +" pinged");
	
			((AbstractDedaleAgent)myAgent).sendMessage(msg);
		
		}
	}

}
