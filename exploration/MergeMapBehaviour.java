package exploration;

import java.io.IOException;
import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import myagents.MapAgent;
import utils.MapSerializable;
import utils.MySerializableEdge;
import utils.MySerializableNode;
import utils.Protocol;

public class MergeMapBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 3000089878974807501L;
	

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

				for(MySerializableNode node : received.getNodes()) {
					((MapAgent)this.myAgent).getMap().addNode(node.getNode(),  MapRepresentation.MapAttribute.valueOf(node.getAttributes()));
				}
				
				
				for(MySerializableEdge edge : received.getEdges()) {
					((MapAgent)this.myAgent).getMap().addEdge(edge.getLeft(), edge.getRight());
				}
				
				((MapAgent) this.myAgent).getFriendsKnowledge().updateMine(received.getNodes(), received.getEdges());
				((MapAgent) this.myAgent).getFriendsKnowledge().updateFriend(message.getSender().getLocalName(), received.getNodes(), received.getEdges());
			
				

			
				List<MySerializableNode> theirMissingNodes = ((MapAgent)this.myAgent).getMap().getDifferenceNodes(((MapAgent)this.myAgent).getFriendsKnowledge().getKnownNodes(message.getSender().getLocalName()));
				List<MySerializableEdge> theirMissingEdges = ((MapAgent)this.myAgent).getMap().getDifferenceEdges( ((MapAgent)this.myAgent).getFriendsKnowledge().getKnownEdges(message.getSender().getLocalName()));

				if (! (theirMissingNodes.isEmpty() && theirMissingEdges.isEmpty())) {					
					ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
					msg.setProtocol(Protocol.RESEND_MAP.label);
					msg.setSender(this.myAgent.getAID());
					msg.addReceiver(message.getSender());
					
					MapSerializable content = new MapSerializable(theirMissingNodes, theirMissingEdges);
					
					
					try {					
						msg.setContentObject(content);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					
					
					((MapAgent)this.myAgent).sendMessage(msg);
					
					
					
					((MapAgent)this.myAgent).getFriendsKnowledge().updateMine(theirMissingNodes, theirMissingEdges);
					((MapAgent)this.myAgent).getFriendsKnowledge().updateFriend(message.getSender().getLocalName(), theirMissingNodes, theirMissingEdges);


					
				}
			}
		}
	}
}
