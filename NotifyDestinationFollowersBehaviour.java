package mine;

import java.io.IOException;
import java.util.List;

import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class NotifyDestinationFollowersBehaviour extends OneShotBehaviour {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2383244975645117094L;
	
	private Location destination;
	private List<String> pack;
	
	public NotifyDestinationFollowersBehaviour(AbstractDedaleAgent agent, Location destination, List<String> pack) {
		super(agent);
		this.destination = destination;
		this.pack = pack;
	}
	
	@Override
	public void action() {
		
		ACLMessage message = new ACLMessage(ACLMessage.INFORM);
		message.setSender(new AID(myAgent.getLocalName(), AID.ISLOCALNAME));
		message.setProtocol(Protocol.FOLLOW_DESTINATION.toString());
		//message.setPerformative(ACLMessage.INFORM);
		
		
		for (String packMember : pack) {
			message.addReceiver(new AID(packMember, AID.ISLOCALNAME));
		}
		
		try {
			message.setContentObject(this.destination);
			((AbstractDedaleAgent)this.myAgent).sendMessage(message);
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
