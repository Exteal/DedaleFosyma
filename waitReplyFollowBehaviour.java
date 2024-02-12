package mine;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class waitReplyFollowBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3713673096759011267L;
	private boolean finished = false;
	
	
	public waitReplyFollowBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}
	
	
	@Override
	public void action() {
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.AGREE)
				, MessageTemplate.MatchProtocol(Protocol.START_FOLLOW.toString()));
		
		ACLMessage reply = this.myAgent.receive(pattern);
	
		if (reply != null) {
			((PackAgent) this.myAgent).addToPack(reply.getSender().getLocalName());
			System.out.println("Agents in pack : ");
			for (String agent : ((PackAgent) this.myAgent).getPack() ) {
				System.out.println(agent);
			}
		}
	}

	@Override
	public boolean done() {
		return finished;
	}

}
