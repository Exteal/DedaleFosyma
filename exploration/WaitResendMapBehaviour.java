package exploration;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import myagents.MapAgent;
import utils.MapSerializable;
import utils.MySerializableEdge;
import utils.MySerializableNode;
import utils.Protocol;

public class WaitResendMapBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -241850640977740874L;

	public WaitResendMapBehaviour(AbstractDedaleAgent myAgent, List<String> listAllies) {
		super(myAgent);
	}

	
	@Override
	public void action() {
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(Protocol.RESEND_MAP.label),
				MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
		
		ACLMessage message = this.myAgent.receive(pattern);
		
		if(message != null) {
			MapSerializable received = null;
			try {
				 received = (MapSerializable)message.getContentObject();		
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			
			if (received != null) {		
				for(MySerializableNode node : received.getNodes()) {
					((MapAgent)this.myAgent).getMap().addNode(node.getNode(),  MapRepresentation.MapAttribute.valueOf(node.getAttributes()));
				}
				
				
				for(MySerializableEdge edge : received.getEdges()) {
					((MapAgent)this.myAgent).getMap().addEdge(edge.getLeft(), edge.getRight());
				}
				
				((MapAgent) this.myAgent).getFriendsKnowledge().updateMine(received.getNodes(), received.getEdges());
				((MapAgent) this.myAgent).getFriendsKnowledge().updateFriend(message.getSender().getLocalName(), received.getNodes(), received.getEdges());
			
			}
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}
