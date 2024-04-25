package mine;


import java.util.List;
import jade.core.behaviours.Behaviour;
import test.RandomMoveBehaviour;

public class LeaderAgent extends PackAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 119651788056430352L;
	
	@Override
	protected List<Behaviour> listSpecificBehaviours() {
		return List.of(
				new RandomMoveBehaviour(this, listAllies()),
				new OrderFollowBehaviour(this, listAllies())
		) ;
	}
}
