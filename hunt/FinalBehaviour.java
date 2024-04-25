package hunt;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;

public class FinalBehaviour extends SimpleBehaviour {

	
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
