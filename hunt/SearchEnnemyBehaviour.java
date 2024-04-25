package hunt;

import java.util.List;
import java.util.stream.Collectors;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.SimpleBehaviour;
import mapSharing.FollowerAgent;
import mapSharing.MapAgent;
import utils.MovingStates;

import java.util.Random;

public class SearchEnnemyBehaviour extends SimpleBehaviour {

	
	public SearchEnnemyBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	
	@Override
	public void action() {
		
		//System.out.println("search");
		List<Couple<Location, List<Couple<Observation, Integer>>>> lobs = ((MapAgent)this.myAgent).observe();
		List<Couple<Location, List<Couple<Observation, Integer>>>> stenches = lobs.stream().filter(o -> o.getRight().stream().anyMatch(obs -> obs.getLeft().equals(Observation.STENCH))).collect(Collectors.toList());
		

		//((MapAgent)this.myAgent).getMap().
		
		if (!stenches.isEmpty()) {
			((FollowerAgent)this.myAgent).setMovingValue(MovingStates.FollowingStench.number);
			
			for (Couple<Location, List<Couple<Observation, Integer>>> stench : stenches) {
				String locId = stench.getLeft().getLocationId();
			}
			
			for (int i = 0; i < 3; i ++) {
				
			}
			
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
