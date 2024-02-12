package mine;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class OrderFollowBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7827751616118741278L;
	private List<String> allies;
	public OrderFollowBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);
		this.allies = allies;
	}
	
	
	@Override
	public void action() {
		
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		
		message.setProtocol(Protocol.START_FOLLOW.toString());
		message.setSender(this.myAgent.getAID());
		
		for (String ally : allies) {
			message.addReceiver(new AID(ally, AID.ISLOCALNAME));
		}
		//System.out.println(myAgent.getLocalName() + " ordered");
		
		((AbstractDedaleAgent)this.myAgent).sendMessage(message);
		
		this.myAgent.addBehaviour(new waitReplyFollowBehaviour((AbstractDedaleAgent) this.myAgent));
	}

	@Override
	public boolean done() {
		return false;
	}

}
