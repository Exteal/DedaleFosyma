package hunt;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.behaviours.OneShotBehaviour;
import myagents.FollowerAgent;
import myagents.MapAgent;
import utils.MovingStates;

public class FollowStenchBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173670085849547627L;

	public FollowStenchBehaviour(AbstractDedaleAgent ag) {
		super(ag);
	}
	
	
	private void waitABit() {
		long start = new Date().getTime();
		while(new Date().getTime() - start < 1000L){}
		return;
	}
	
	@Override
	public void action() {
				
		String pos = ((MapAgent) myAgent).getHuntingPos();
		List<String> uselessTiles = ((MapAgent) myAgent).getUselessTiles();
		
		if (pos != null && !uselessTiles.contains(pos)) {
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
						
						waitABit();
						//System.out.println(myAgent.getLocalName() + ((MapAgent)myAgent).getBlockCount()+ " block");
						
						if (((MapAgent)myAgent).getBlockCount() > 20) {
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
	
	@Override
	public int onEnd() {
		return ((FollowerAgent)myAgent).getMovingValue();
	}
	
}
