package hunt;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;

public class FinalBehaviour extends SimpleBehaviour {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9105439092657438561L;

	public FinalBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	
	
	@Override	
	public void action() {
		System.out.println("Final " + myAgent.getLocalName());
	}

	@Override
	public boolean done() {
		return true;
	}

}
