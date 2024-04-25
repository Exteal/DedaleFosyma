package mapSharing;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.Protocol;

public class WaitReplyBehaviour extends SimpleBehaviour {

	
	private static final long serialVersionUID = 3912190804667995044L;
	private boolean finished = true;
	private List<String> allies;
	private int endValue = -1;
	
	public WaitReplyBehaviour(AbstractDedaleAgent abstractDedaleAgent, List<String> listAllies) {
		super(abstractDedaleAgent);
		this.allies = listAllies;
	}


	@Override
	public void action() {
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.AGREE)
				, MessageTemplate.MatchProtocol(Protocol.SHARE_MAP.label));
		
		ACLMessage reply = this.myAgent.receive(pattern);
		if (reply != null) {
			endValue = Protocol.SHARE_MAP.number;
			((MapAgent)this.myAgent).addToCommunicating(reply.getSender().getLocalName());
			//((MapAgent)this.myAgent).setSharingWith(reply.getSender().getLocalName());
		}
	}

	
	
	@Override
	public int onEnd() {
		//System.out.println("wait returned " + endValue);
		return endValue;
		
	}
	
	
	@Override
	public boolean done() {
		return finished;
	}

}
