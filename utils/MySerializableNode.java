package utils;

import java.io.Serializable;
import java.util.Objects;

public class MySerializableNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8353215012033139842L;
	private String node;
	private String attributes;
	
	
	public MySerializableNode(String node, String attributes) {
		this.node = node;
		this.attributes = attributes;
	}
	
	public String getNode() {
		return node;
	}

	public String getAttributes() {
		return attributes;
	}
	
	
	@Override
    public int hashCode() {
        return Objects.hash(node, attributes);
    }
	
	@Override
	public boolean equals(Object other) {	
		if (other instanceof MySerializableNode) {
			MySerializableNode po = (MySerializableNode)other;
			return po.getNode() == this.getNode() && po.getAttributes() == this.getAttributes() || po.getNode() == this.getAttributes() && (po.getAttributes() == this.getNode());		
		}	
		return false;
	}
	
	@Override
	public String toString() {
		return getNode() + " " + getAttributes();
	}
	
	
}
