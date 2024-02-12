package mine;

public enum Protocol {
	PING,
	RESPOND_PING,
	START_FOLLOW, 
	FOLLOW_DESTINATION;
	
	
	@Override
	public String toString() {
		switch (this) {
			case PING:
				return "PING";
			
			case RESPOND_PING:
				return "RESPOND_PING";
				
			case START_FOLLOW:
				return "START_FOLLOW";
				
			case FOLLOW_DESTINATION:
				return "FOLLOW_DESTINATION";
				
			default:
				return "UNKNOWN";
		}
		
	}
}
