package mine;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MustStartFollowingBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8381704261517822307L;
	private boolean finished = false;
	public MustStartFollowingBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}
	
	@Override
	public void action() {
		
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(Protocol.START_FOLLOW.toString()),
				MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		ACLMessage message = this.myAgent.receive(pattern);
		
		if(message != null) {
			((FollowerAgent)this.myAgent).setMovingValue(1);
			((FollowerAgent)this.myAgent).setLeader(message.getSender().getLocalName());
			finished = true;
			
			ACLMessage reply = new ACLMessage(ACLMessage.AGREE);
			reply.setProtocol(Protocol.START_FOLLOW.toString());
			reply.addReceiver(message.getSender());
			reply.setSender(this.myAgent.getAID());
			((AbstractDedaleAgent) this.myAgent).sendMessage(reply);
	
		}
	}

	@Override
	public boolean done() {
		return finished;
	}
	
}
