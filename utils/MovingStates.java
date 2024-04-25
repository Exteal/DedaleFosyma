package utils;

public enum MovingStates {

	//Random("Random", 0),
	//FollowingLeader("FollowingLeader", 1),
	//FollowingEnnemy("FollowingEnnemy", 2),
	Targeted("Targeted", 3),
	StartHunt("StartHunt", 4),
	SearchEnnemy("SearchEnnemy", 5),
	FollowingStench("FollowingStench", 6), 
	BlockingEnnemy("BlockingEnnemy", 7),
	Final("Final", 8);
	
	
	public final String label;
	public final int number;
	
	private MovingStates(String label, int number) {
		this.label = label;
		this.number = number;
	}
	
}


