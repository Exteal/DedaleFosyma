package mapSharing;

import java.io.Serializable;
import java.util.List;

import utils.SerializableEdge;


public class MapSerializable  implements Serializable {

	private static final long serialVersionUID = 7968059864201609257L;
	
	private List<String> nodes;
	private List<SerializableEdge> edges;
	
	public MapSerializable(List<String> a, List<SerializableEdge> b) {
		nodes = a;
		edges = b;
	}
	
	public List<String> getNodes() {
		return nodes;
	}
	
	public List<SerializableEdge> getEdges() {
		return edges;
	}
}
