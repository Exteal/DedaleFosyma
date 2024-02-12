package mine;

import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;


public class RandomMoveBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6533196628465572862L;
	private List<String> allies;
	
	private boolean finished = false;
	
	public RandomMoveBehaviour(AbstractDedaleAgent agent, List<String> allies) {
		super(agent);
		this.allies = allies;
	}
	
	@Override
	public void action() {
		
		try {
			this.myAgent.doWait(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Couple<Location,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
		Random r= new Random();
		int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move

		//The move action (if any) should be the last action of your behaviour
		
		//System.out.println(this.myAgent.getAID().getLocalName() +" moved");
		Location destination = lobs.get(moveId).getLeft();

		if (this.getAgent() instanceof LeaderAgent) {
			((AbstractDedaleAgent)this.getAgent()).addBehaviour(
					new NotifyDestinationFollowersBehaviour((AbstractDedaleAgent) this.myAgent, destination, ((PackAgent)this.myAgent).getPack()));
		}
		
		
		((AbstractDedaleAgent)this.myAgent).moveTo(destination);
	}
	
	public int onEnd() {
		if (this.myAgent instanceof FollowerAgent) {
			System.out.println("onEnd random Move " + ((FollowerAgent) this.myAgent).getMovingValue());
			return ((FollowerAgent) this.myAgent).getMovingValue();
		}
		
		else  {
			return super.onEnd();
		}
	}
	
	public boolean done() {
		if (this.myAgent instanceof FollowerAgent) {
			return ((FollowerAgent) this.myAgent).getMovingValue() != MovingStates.Random.getStateInt();
		}
		else return finished;
			
	}

}
