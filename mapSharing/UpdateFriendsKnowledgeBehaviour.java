package mapSharing;

import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.OneShotBehaviour;
import utils.SerializableEdge;

public class UpdateFriendsKnowledgeBehaviour extends OneShotBehaviour {

	
	private static final long serialVersionUID = -5093617975645702372L;
	private String friendId;
	private List<String> nodesToAdd;
	private List<SerializableEdge> edgesToAdd;
	
	
	public UpdateFriendsKnowledgeBehaviour(AbstractDedaleAgent agent, String friendId, List<String> nodesToAdd, List<SerializableEdge> edgesToAdd) {
		super(agent);
		this.friendId = friendId;
		this.nodesToAdd = nodesToAdd;
		this.edgesToAdd = edgesToAdd;
	}
	
	
	@Override
	public void action() {
		
		if(((MapAgent)this.myAgent).getFriendsKnowledge() == null) {
		
		}
		
		
		List<String> nodes = ((MapAgent)this.myAgent).getFriendsKnowledge().myKnowledgeOfNodesOf(friendId);
		List<SerializableEdge> edges = ((MapAgent)this.myAgent).getFriendsKnowledge().myKnowledgeOfEdgesOf(friendId);
		
		for (String node : nodesToAdd) {
			if(!nodes.contains(node)) {
				((MapAgent)this.myAgent).getFriendsKnowledge().addNodeToFriend(node, friendId);
			}
		}
		
		
		for (SerializableEdge edge : edgesToAdd) {
			if(!edges.contains(edge)) {
				((MapAgent)this.myAgent).getFriendsKnowledge().addEdgeToFriend(edge, friendId);
			}
		}
		
		//System.out.println(((MapAgent)this.myAgent).getFriendsKnowledge());
		
	}

}
