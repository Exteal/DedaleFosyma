package utils;

public enum Protocol {
	PING("PING", 0),
	RESPOND_PING("RESPOND_PING", 1),
	START_FOLLOW("START_FOLLOW", 2), 
	FOLLOW_DESTINATION("FOLLOW_DESTINATION", 3),
	MERGE_MAP("MERGE_MAP", 4),
	SHARE_MAP("SHARE_MAP", 5), 
	WAIT_REPLY("WAIT_REPLY", 6),
	HUNTERS("HUNTERS", 7);
	
	public final String label;
	public final int number;
	
	
	private Protocol(String label, int number) {
		this.label = label;
		this.number = number;
	}
}
