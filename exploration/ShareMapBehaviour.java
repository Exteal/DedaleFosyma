package exploration;

import java.io.IOException;
import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import myagents.MapAgent;
import utils.MapSerializable;
import utils.MySerializableEdge;
import utils.MySerializableNode;
import utils.Protocol;

public class ShareMapBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221062297714829969L;
	
	public ShareMapBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}
	


	@Override
	public void action() {
				
		List<MySerializableNode> nodes = ((MapAgent)this.myAgent).getMap().getNodes();
		List<MySerializableEdge> edges = ((MapAgent)myAgent).getMap().getEdges();
		
		((MapAgent)this.myAgent).getFriendsKnowledge().updateMine(nodes, edges);
		
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setProtocol(Protocol.SHARE_MAP.label);
		msg.setSender(this.myAgent.getAID());
		
		for (String agentName : ((MapAgent) this.myAgent).getCommunicating()) {
		
			msg.addReceiver(new AID(agentName,AID.ISLOCALNAME));
			
			
			List<MySerializableNode> missing_nodes = ((MapAgent)this.myAgent).getMap().getDifferenceNodes(((MapAgent)this.myAgent).getFriendsKnowledge().getKnownNodes(agentName));
			List<MySerializableEdge> missing_edges = ((MapAgent)this.myAgent).getMap().getDifferenceEdges( ((MapAgent)this.myAgent).getFriendsKnowledge().getKnownEdges(agentName));

			MapSerializable content = new MapSerializable(missing_nodes, missing_edges);
			
			
			try {					
				msg.setContentObject(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			((MapAgent)this.myAgent).sendMessage(msg);
			
			((MapAgent)this.myAgent).getFriendsKnowledge().updateFriend(agentName, missing_nodes, missing_edges);
					
		}
		
		
		
	}
}
