package hunt;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.Behaviour;
import mapSharing.FollowerAgent;
import mapSharing.MapAgent;
import utils.MovingStates;

public class FollowStenchBehaviour extends Behaviour {

	public FollowStenchBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	@Override
	public void action() {
		
		
		//System.out.println("follow stench");
	
		
		String pos = ((MapAgent) myAgent).getHuntingPos(); 
		if (pos != null) {
			List<Couple<Location, List<Couple<Observation, Integer>>>> lobs = ((MapAgent)this.myAgent).observe();
			List<Couple<Location, List<Couple<Observation, Integer>>>> stench = lobs.stream().filter(o -> o.getLeft().getLocationId().equals(pos)).collect(Collectors.toList());
			
			if (stench.isEmpty()) {
				followStench();
			}
			
			else {
				boolean ite = stench.get(0).getRight().stream().anyMatch(o -> o.getLeft()== Observation.STENCH);
				if (ite) {
					
					Location objective = stench.get(0).getLeft();
					
					boolean free = ((MapAgent) myAgent).moveTo(objective);
					if (free) {
						((MapAgent)this.myAgent).resetBlockCount();
						((MapAgent)myAgent).setHuntingPos(null);
					}
					
					else {

						((MapAgent)myAgent).setHuntingPos(objective.getLocationId());
					
						((MapAgent)myAgent).increaseBlockCount();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(myAgent.getLocalName() + ((MapAgent)myAgent).getBlockCount()+ " block");
						
						if (((MapAgent)myAgent).getBlockCount() > 10) {
							((FollowerAgent)myAgent).setMovingValue(MovingStates.BlockingEnnemy.number);
						}}
				}
				
				else {
					followStench();
				}
				
			}
		}
		
		else {
			followStench();

		}
	}
	
	
	private void followStench() {
		((MapAgent)this.myAgent).resetBlockCount();
		
		List<Couple<Location, List<Couple<Observation, Integer>>>> lobs = ((MapAgent)this.myAgent).observe();
		List<Couple<Location, List<Couple<Observation, Integer>>>> stenches = lobs.stream().filter(o -> o.getRight().stream().anyMatch(obs -> obs.getLeft().equals(Observation.STENCH))).collect(Collectors.toList());
		
		String currentPos = ((AbstractDedaleAgent)myAgent).getCurrentPosition().getLocationId();
		
		if (!stenches.isEmpty()) {
			Random r = new Random();
			int move = r.nextInt(stenches.size());
			
			Location objective = stenches.get(move).getLeft();
			boolean free = ((MapAgent)this.myAgent).moveTo(objective);
			if (!free) {
				((MapAgent)myAgent).setHuntingPos(objective.getLocationId());
			}
		}
		
		else {
			((MapAgent)myAgent).setHuntingPos(null);
			
			((FollowerAgent)this.myAgent).setMovingValue(MovingStates.SearchEnnemy.number);
		}


	}
	public int onEnd() {
		return ((FollowerAgent)myAgent).getMovingValue();
	}
	@Override
	public boolean done() {
		return true;
	}

}
