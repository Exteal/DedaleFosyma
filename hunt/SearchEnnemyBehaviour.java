package hunt;

import java.util.List;
import java.util.stream.Collectors;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import myagents.FollowerAgent;
import myagents.MapAgent;
import utils.MovingStates;

import java.util.Random;

public class SearchEnnemyBehaviour extends SimpleBehaviour {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1214089730462920189L;

	public SearchEnnemyBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	
	@Override
	public void action() {
		
		List<Couple<Location, List<Couple<Observation, Integer>>>> lobs = ((MapAgent)this.myAgent).observe();
		List<Couple<Location, List<Couple<Observation, Integer>>>> stenches = lobs.stream().filter(o -> o.getRight().stream().anyMatch(obs -> obs.getLeft().equals(Observation.STENCH))).collect(Collectors.toList());
		
		
		if (!stenches.isEmpty()) {
			((FollowerAgent)this.myAgent).setMovingValue(MovingStates.FollowingStench.number);
			
			
			Random r = new Random();
			int move = r.nextInt(stenches.size());
			
			
			((MapAgent)this.myAgent).moveTo(stenches.get(move).getLeft());
		}
		
		else {			
			((FollowerAgent)this.myAgent).setMovingValue(MovingStates.SearchEnnemy.number);
			Random r = new Random();
			int move = r.nextInt(lobs.size());
			
			((MapAgent)this.myAgent).moveTo(lobs.get(move).getLeft());
		}
		
	}

	@Override
	public boolean done() {
		return true;
	}
	
	public int onEnd() {
		//System.out.println(((FollowerAgent)myAgent).getMovingValue());
		return ((FollowerAgent)myAgent).getMovingValue();
	}

}
