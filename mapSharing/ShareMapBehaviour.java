package mapSharing;

import java.io.IOException;
import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import utils.SerializableEdge;
import utils.Protocol;

public class ShareMapBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4221062297714829969L;
	private boolean finished = true;
	private List<String> allies;
	
	public ShareMapBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);
		this.allies = allies;
	}
	


	@Override
	public void action() {
				
		List<String> nodes = ((MapAgent)this.myAgent).getMap().getNodes();
		List<SerializableEdge> edges = ((MapAgent)myAgent).getMap().getEdges();
		((MapAgent)this.myAgent).getFriendsKnowledge().updateMine(nodes, edges);
		
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setProtocol(Protocol.SHARE_MAP.label);
		msg.setSender(this.myAgent.getAID());
		
		for (String agentName : ((MapAgent) this.myAgent).getCommunicating()) {
		
			msg.addReceiver(new AID(agentName,AID.ISLOCALNAME));
			
			
			List<String> missing_nodes = ((MapAgent)this.myAgent).getMap().getDifferenceNodes(((MapAgent)this.myAgent).getFriendsKnowledge().getKnownNodes(agentName));
			List<SerializableEdge> missing_edges = ((MapAgent)this.myAgent).getMap().getDifferenceEdges( ((MapAgent)this.myAgent).getFriendsKnowledge().getKnownEdges(agentName));

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
	

	@Override
	public boolean done() {
		return finished;
	}

}
