package utils;

import java.io.Serializable;
import java.util.Objects;

public class SerializableEdge implements Serializable {
	
	
	private static final long serialVersionUID = -5366032743460619624L;
	
	private String left;
	private String right;
	
	public SerializableEdge(String left, String right) {
		if (left.compareTo(right) < 0) {
			this.left = left;
			this.right = right;
		}
		else {
			this.left = right;
			this.right = left;
		}
		
	}
	
	public String getLeft() {
		return left;
	}
	
	public String getRight() {
		return right;
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
	
	@Override
	public boolean equals(Object other) {	
		if (other instanceof SerializableEdge) {
			SerializableEdge po = (SerializableEdge)other;
			return po.getLeft() == this.getLeft() && po.getRight() == this.getRight() || po.getLeft() == this.getRight() && (po.getRight() == this.getLeft());		
		}	
		return false;
	}
	
	@Override
	public String toString() {
		return getLeft() + " " + getRight();
	}
	
	
}
