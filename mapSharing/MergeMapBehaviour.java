package mapSharing;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import utils.SerializableEdge;
import utils.Protocol;

public class MergeMapBehaviour extends SimpleBehaviour {

	private static final long serialVersionUID = 3000089878974807501L;
	private boolean finished = false;
	

	public MergeMapBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}

	
	@Override
	public void action() {

		if (((MapAgent) this.myAgent).getMap() == null) {
			((MapAgent) this.myAgent).initMap();
		}
		MessageTemplate pattern = MessageTemplate.and(MessageTemplate.MatchProtocol(Protocol.SHARE_MAP.label),
				MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
		ACLMessage message = this.myAgent.receive(pattern);
		
		if(message != null) {
			
			MapSerializable received = null;
			try {
				 received = (MapSerializable)message.getContentObject();		
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			
			if (received != null) {				

				for(String node : received.getNodes()) {
					((MapAgent)this.myAgent).getMap().addNewNode(node);
				}
				
				
				for(SerializableEdge edge : received.getEdges()) {
					((MapAgent)this.myAgent).getMap().addEdge(edge.getLeft(), edge.getRight());
				}
				
				((MapAgent) this.myAgent).getFriendsKnowledge().updateMine(received.getNodes(), received.getEdges());
				((MapAgent) this.myAgent).getFriendsKnowledge().updateFriend(message.getSender().getLocalName(), received.getNodes(), received.getEdges());
			}
		}
	}

	@Override
	public boolean done() {
		return finished;
	}

}
