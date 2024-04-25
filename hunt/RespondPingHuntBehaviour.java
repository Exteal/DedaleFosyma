package hunt;

import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import mapSharing.MapAgent;
import utils.Protocol;

public class RespondPingHuntBehaviour extends SimpleBehaviour {

	public RespondPingHuntBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	
	@Override
	public void action() {
		MessageTemplate msgTemplate=MessageTemplate.and(
				MessageTemplate.MatchProtocol(Protocol.PING.label),
				MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF));
		ACLMessage msgReceived=this.myAgent.receive(msgTemplate);

		if (msgReceived != null) {
			
			Location myPos = ((MapAgent)myAgent).getCurrentPosition();
			
			String askedPos = msgReceived.getContent();
			
			if (askedPos.equals(myPos.getLocationId())) {
				ACLMessage response = new ACLMessage(ACLMessage.CONFIRM);
				response.addReceiver(msgReceived.getSender());
				response.setSender(this.myAgent.getAID());
				response.setProtocol(Protocol.PING.label);
		
				((AbstractDedaleAgent) this.myAgent).sendMessage(response);
			}
						
		}
	}

	@Override
	public boolean done() {
		return false;
	}

}
