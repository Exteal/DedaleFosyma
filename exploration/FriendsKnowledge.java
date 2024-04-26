package exploration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import utils.MySerializableEdge;
import utils.MySerializableNode;


public class FriendsKnowledge {
	
		//for each node -> list friends who already know
		private HashMap<MySerializableNode, HashSet<String>> friendsKnowledgeNodes;

		//for each edge -> list friends who already know
		private HashMap<MySerializableEdge, HashSet<String>> friendsKnowledgeEdges;
				
		public FriendsKnowledge() {
			this.friendsKnowledgeNodes = new HashMap<MySerializableNode, HashSet<String>>();
			
			this.friendsKnowledgeEdges = new HashMap<MySerializableEdge, HashSet<String>>();
		}
		
		
		public HashMap<MySerializableNode, HashSet<String>> getFriendsKnowledgeNodes() {
			return this.friendsKnowledgeNodes;
		}
		
		public HashMap<MySerializableEdge, HashSet<String>> getFriendsKnowledgeEdges() {
			return this.friendsKnowledgeEdges;
		}
		
		
		public List<MySerializableNode> myKnowledgeOfNodesOf(String friendId) {
			return getFriendsKnowledgeNodes().entrySet().stream()
					.filter(n -> n.getValue().contains(friendId))
					.map(n -> n.getKey())
					.collect(Collectors.toList());
		}
		
		
		
		public List<MySerializableEdge> myKnowledgeOfEdgesOf(String friendId) {
			return getFriendsKnowledgeEdges().entrySet().stream()
					.filter(e -> e.getValue().contains(friendId))
					.map(e -> e.getKey())
					.collect(Collectors.toList());
		}
		
		
		public void addNodeToFriend(MySerializableNode nodeId, String friend) {
			if(this.friendsKnowledgeNodes.get(nodeId) == null) {
				throw new IllegalArgumentException("add node " + nodeId + " to " + friend);
			}
			if (!this.friendsKnowledgeNodes.get(nodeId).contains(friend)) {
				this.friendsKnowledgeNodes.get(nodeId).add(friend);
			}
		}
		
		
		public void addEdgeToFriend(MySerializableEdge edge, String friend) {
			if(this.friendsKnowledgeEdges.get(edge) == null ) {				
				throw new IllegalArgumentException("add edge " + edge + " to " + friend);
			}	
			if (!this.friendsKnowledgeEdges.get(edge).contains(friend)) {
				this.friendsKnowledgeEdges.get(edge).add(friend);
			}
		}
		
		
		public void addNewNode(MySerializableNode node) {
			this.friendsKnowledgeNodes.putIfAbsent(node, new HashSet<>());
		}
		
		
		public void addNewEdge(String source, String target) {
			this.friendsKnowledgeEdges.putIfAbsent(new MySerializableEdge(source, target), new HashSet<>());
		}
	
		public void updateMine(List<MySerializableNode> nodesToAdd, List<MySerializableEdge> edgesToAdd) {
			for (MySerializableNode node : nodesToAdd) {
				addNewNode(node);
			}
			
			for (MySerializableEdge edge : edgesToAdd) {
				addNewEdge(edge.getLeft(), edge.getRight());
			}
			
		
		}
		public void updateFriend(String friendId, List<MySerializableNode> nodesToAdd, List<MySerializableEdge> edgesToAdd) {
			List<MySerializableNode> nodes = myKnowledgeOfNodesOf(friendId);
			List<MySerializableEdge> edges = myKnowledgeOfEdgesOf(friendId);
			
			for (MySerializableNode node : nodesToAdd) {
				if(!nodes.contains(node)) {
					addNodeToFriend(node, friendId);
				}
			}
			
			
			for (MySerializableEdge edge : edgesToAdd) {
				if(!edges.contains(edge)) {
					addEdgeToFriend(edge, friendId);
				}
			}
		}
		
		public List<MySerializableNode> getKnownNodes(String friendName) {
			return getFriendsKnowledgeNodes().entrySet()
					.stream()
					.filter(e -> e.getValue().contains(friendName))
					.map(e -> e.getKey())
					.toList();
		}
		
		public List<MySerializableEdge> getKnownEdges(String friendName) {
			return  getFriendsKnowledgeEdges().entrySet()
					.stream()
					.filter(e -> e.getValue().contains(friendName))
					.map(e -> e.getKey())
					.toList();
		}
		
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			sb.append("nodes : \n");
			
			for (MySerializableNode node : friendsKnowledgeNodes.keySet())  {
				friendsKnowledgeNodes.get(node).stream().forEach(f -> sb.append(f +" knows " + node.getNode() + "\n"));
			}
			
			return sb.toString();
		}
}
