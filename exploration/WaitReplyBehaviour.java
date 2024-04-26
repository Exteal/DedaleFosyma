package exploration;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import myagents.MapAgent;
import utils.Protocol;

public class WaitReplyBehaviour extends SimpleBehaviour {

	
	private static final long serialVersionUID = 3912190804667995044L;
	private boolean finished = true;
	private int endValue = -1;
	
	public WaitReplyBehaviour(AbstractDedaleAgent abstractDedaleAgent) {
		super(abstractDedaleAgent);
	}


	@Override
	public void action() {
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.AGREE)
				, MessageTemplate.MatchProtocol(Protocol.SHARE_MAP.label));
		
		ACLMessage reply = this.myAgent.receive(pattern);
		if (reply != null) {
			endValue = Protocol.SHARE_MAP.number;
			((MapAgent)this.myAgent).addToCommunicating(reply.getSender().getLocalName());
		}
	}

	
	
	@Override
	public int onEnd() {
		return endValue;
		
	}
	
	
	@Override
	public boolean done() {
		return finished;
	}

}
