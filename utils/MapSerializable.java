package utils;

import java.io.Serializable;
import java.util.List;


public class MapSerializable  implements Serializable {

	private static final long serialVersionUID = 7968059864201609257L;
	
	private List<MySerializableNode> nodes;
	private List<MySerializableEdge> edges;
	
	public MapSerializable(List<MySerializableNode> a, List<MySerializableEdge> b) {
		nodes = a;
		edges = b;
	}
	
	public List<MySerializableNode> getNodes() {
		return nodes;
	}
	
	public List<MySerializableEdge> getEdges() {
		return edges;
	}
}
