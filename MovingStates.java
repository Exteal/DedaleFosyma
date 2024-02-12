package mine;

public enum MovingStates {

	Random,
	FollowingLeader,
	FollowingEnnemy;
	
	public int getStateInt() {
		switch(this) {
			case Random:
				return 0;
				
			case FollowingLeader:
				return 1;
			
			case FollowingEnnemy:
				return 2;
			
			default:
				return -1;
		}
	}
	
	
	public String getStateName() {
		switch(this) {
			case Random:
				return "Random";
				
			case FollowingLeader:
				return "FollowingLeader";
			
			case FollowingEnnemy:
				return "FollowingEnnemy";
			
			default:
				return "Unknown";
		}
	}
}


