package mapSharing;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class PrintKnowledgeBehaviour extends TickerBehaviour {

	public PrintKnowledgeBehaviour(Agent a, long period) {
		super(a, period);

	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 7259728451553126325L;

	
	@Override
	public void onTick() {
		System.out.println(((MapAgent)this.myAgent).getFriendsKnowledge());
	}

}
