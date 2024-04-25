package mapSharing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import utils.SerializableEdge;


public class FriendsKnowledge {
	    //tous noeuds partagés closed deviennent closed pour moi
	
	
		//for each node -> list friends who already know
		private HashMap<String, HashSet<String>> friendsKnowledgeNodes;

		//for each edge -> list friends who already know
		private HashMap<SerializableEdge, HashSet<String>> friendsKnowledgeEdges;
		
		//private MapRepresentation repr;
		
		
		public FriendsKnowledge() {
			this.friendsKnowledgeNodes = new HashMap<String, HashSet<String>>();
			
			this.friendsKnowledgeEdges = new HashMap<SerializableEdge, HashSet<String>>();
		}
		
		
		public HashMap<String, HashSet<String>> getFriendsKnowledgeNodes() {
			return this.friendsKnowledgeNodes;
		}
		
		public HashMap<SerializableEdge, HashSet<String>> getFriendsKnowledgeEdges() {
			return this.friendsKnowledgeEdges;
		}
		
		
		public List<String> myKnowledgeOfNodesOf(String friendId) {
			return getFriendsKnowledgeNodes().entrySet().stream()
					.filter(n -> n.getValue().contains(friendId))
					.map(n -> n.getKey())
					.collect(Collectors.toList());
		}
		
		
		
		public List<SerializableEdge> myKnowledgeOfEdgesOf(String friendId) {
			return getFriendsKnowledgeEdges().entrySet().stream()
					.filter(e -> e.getValue().contains(friendId))
					.map(e -> e.getKey())
					.collect(Collectors.toList());
		}
		
		
		public void addNodeToFriend(String nodeId, String friend) {
			if(this.friendsKnowledgeNodes.get(nodeId) == null) {
				System.out.println("myknown : " + this.friendsKnowledgeNodes);
				throw new IllegalArgumentException("add node " + nodeId + " to " + friend);
			}
			if (!this.friendsKnowledgeNodes.get(nodeId).contains(friend)) {
				this.friendsKnowledgeNodes.get(nodeId).add(friend);
			}
		}
		
		
		public void addEdgeToFriend(SerializableEdge edge, String friend) {
			if(this.friendsKnowledgeEdges.get(edge) == null ) {				
				throw new IllegalArgumentException("add edge " + edge + " to " + friend);
			}	
			if (!this.friendsKnowledgeEdges.get(edge).contains(friend)) {
				this.friendsKnowledgeEdges.get(edge).add(friend);
			}
		}
		
		
		public void addNewNode(String node) {
			HashSet<String> added = this.friendsKnowledgeNodes.putIfAbsent(node, new HashSet<>());
			
			/*if (added == null) {
			
				getRepr().addNewNode(node);
			}*/
		}
		
		
		public void addNewEdge(String source, String target) {
			//System.out.println("adding edge " + source + " " + target);
			
			//System.out.println("get " + friendsKnowledgeEdges.keySet()) ;
			//System.out.println(this.toString());

			HashSet<String> added = this.friendsKnowledgeEdges.putIfAbsent(new SerializableEdge(source, target), new HashSet<>());

			/*if (added == null) {
				getRepr().addEdge(source, target);
			}*/
			
			//System.out.println(this.toString());

		}
		
		/*private MapRepresentation getRepr() {
			if (this.repr == null) {
				repr = new MapRepresentation();
			}
			return repr;
		}*/


		public void updateMine(List<String> nodesToAdd, List<SerializableEdge> edgesToAdd) {
			for (String node : nodesToAdd) {
				addNewNode(node);
			}
			
			for (SerializableEdge edge : edgesToAdd) {
				addNewEdge(edge.getLeft(), edge.getRight());
			}
			
		
		}
		public void updateFriend(String friendId, List<String> nodesToAdd, List<SerializableEdge> edgesToAdd) {
			
			
			List<String> nodes = myKnowledgeOfNodesOf(friendId);
			List<SerializableEdge> edges = myKnowledgeOfEdgesOf(friendId);
			
			for (String node : nodesToAdd) {
				if(!nodes.contains(node)) {
					addNodeToFriend(node, friendId);
				}
			}
			
			
			for (SerializableEdge edge : edgesToAdd) {
				if(!edges.contains(edge)) {
					addEdgeToFriend(edge, friendId);
				}
			}
			
			
			
			
		}
		
		public List<String> getKnownNodes(String friendName) {
			return getFriendsKnowledgeNodes().entrySet()
					.stream()
					.filter(e -> e.getValue().contains(friendName))
					.map(e -> e.getKey())
					.toList();
		}
		
		public List<SerializableEdge> getKnownEdges(String friendName) {
			return  getFriendsKnowledgeEdges().entrySet()
					.stream()
					.filter(e -> e.getValue().contains(friendName))
					.map(e -> e.getKey())
					.toList();
		}
		
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append("nodes : \n");
			
			for (String node : friendsKnowledgeNodes.keySet())  {
				friendsKnowledgeNodes.get(node).stream().forEach(f -> sb.append(f +" knows " + node + "\n"));
			}
			
			return sb.toString();
		}
}
