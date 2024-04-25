package mapSharing;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utils.Protocol;

public class RespondPingBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	
	private boolean finished = false;
	private static final long serialVersionUID = 2876902720573512513L;
	private List<String> allies;
	
	public RespondPingBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}

	@Override
	public void action() {
		
		MessageTemplate msgTemplate=MessageTemplate.and(
				MessageTemplate.MatchProtocol(Protocol.PING.label),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		ACLMessage msgReceived=this.myAgent.receive(msgTemplate);
		if (msgReceived!=null) {
			//System.out.println( this.myAgent.getAID().getLocalName() + " Ping received from " + msgReceived.getSender().getLocalName());
			ACLMessage response = new ACLMessage(ACLMessage.AGREE);
			response.addReceiver(msgReceived.getSender());
			response.setSender(this.myAgent.getAID());
			response.setProtocol(Protocol.SHARE_MAP.label);
	
			((AbstractDedaleAgent) this.myAgent).sendMessage(response);
			((AbstractDedaleAgent) this.myAgent).addBehaviour(new MergeMapBehaviour((AbstractDedaleAgent) this.myAgent));
		}
		
	}

	@Override
	public boolean done() {
		return finished;
	}

}
