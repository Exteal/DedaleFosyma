package exploration;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.behaviours.SimpleBehaviour;
import myagents.FollowerAgent;
import myagents.MapAgent;
import utils.MovingStates;
import utils.MySerializableNode;


public class TargetedMoveBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6533196628465572862L;
		
	public TargetedMoveBehaviour(AbstractDedaleAgent agent) {
		super(agent);
	}
	
	@Override
	public void action() {
		
		try {
			this.myAgent.doWait(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		((MapAgent)this.myAgent).emptyCommunicating();
		
		
		List<Couple<Location,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition

		Location myPosition=((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
		((MapAgent)this.myAgent).getMap().addNode(myPosition.getLocationId(), MapAttribute.closed);


		
		
		//1) remove the current node from openlist and add it to closedNodes.
		((MapAgent)this.myAgent).getMap().addNode(myPosition.getLocationId(), MapAttribute.closed);

		//2) get the surrounding nodes and, if not in closedNodes, add them to open nodes.
		String nextNodeId=null;
		Iterator<Couple<Location, List<Couple<Observation, Integer>>>> iter=lobs.iterator();
		while(iter.hasNext()){
			Location accessibleNode=iter.next().getLeft();
			
			
			boolean isNewNode=((MapAgent)this.myAgent).getMap().addNewNode(accessibleNode.getLocationId());
			
			((MapAgent)this.myAgent).getFriendsKnowledge().addNewNode(new MySerializableNode(accessibleNode.getLocationId(), MapAttribute.open.toString()));
			
			//the node may exist, but not necessarily the edge
			if (myPosition.getLocationId()!=accessibleNode.getLocationId()) {
				
				((MapAgent)this.myAgent).getMap().addEdge(myPosition.getLocationId(), accessibleNode.getLocationId());
				
				((MapAgent)this.myAgent).getFriendsKnowledge().addNewEdge(myPosition.getLocationId(), accessibleNode.getLocationId());
				
				if (nextNodeId==null && isNewNode) nextNodeId=accessibleNode.getLocationId();
			}
		}
			
		
		Location loc = ((MapAgent)this.myAgent).selectNextDestination();
		
		//cas ou plus aucun noeud ouvert n'existe
		if (loc == null) {
			((FollowerAgent)this.myAgent).setMovingValue(MovingStates.StartHunt.number);
		}
		
		
		else {
			
			String last = ((MapAgent)this.myAgent).getLastDestination();
			
			if (last != null) {
				if (loc.getLocationId().equals(last)) {
					((MapAgent)this.myAgent).increaseTries();
					
					if (((MapAgent)this.myAgent).getTries() > 10) {
						
						String blockingLoopPos = ((MapAgent)this.myAgent).getBlockLoopPos();
						
						if (blockingLoopPos != null) {
							
							if (blockingLoopPos.equals(loc.getLocationId())) {
								((FollowerAgent)this.myAgent).increaseBlockLoopCount();
								if (((MapAgent)this.myAgent).getBlockLoopCount() > 10)  {
									((FollowerAgent)this.myAgent).setMovingValue(MovingStates.StartHunt.number);	
								}
								
							}
							else {
								((MapAgent)this.myAgent).setBlockLoopPos(loc.getLocationId());
							}
						}
							
						else {
							Random r = new Random();
							int rd=1+r.nextInt(lobs.size()-1);
							
							boolean free = ((MapAgent)this.myAgent).moveTo(lobs.get(rd).getLeft());
							if (free) {
								((MapAgent)this.myAgent).resetTries();
								((MapAgent)this.myAgent).setLastDestination(lobs.get(rd).getLeft().getLocationId());
								
							}
						}
						
						
					}
					
					else {
						((MapAgent)this.myAgent).setLastDestination(loc.getLocationId());
						((MapAgent)this.myAgent).moveTo(loc);
					}
					
				}
				
				else {
					((MapAgent)this.myAgent).resetTries();
					((MapAgent)this.myAgent).resetBlockLoopCount();
					((MapAgent)this.myAgent).setLastDestination(loc.getLocationId());
					((MapAgent)this.myAgent).moveTo(loc);
				}
			}
			
			else {
				((MapAgent)this.myAgent).resetTries();
				((MapAgent)this.myAgent).resetBlockLoopCount();
				((MapAgent)this.myAgent).setLastDestination(loc.getLocationId());
				((MapAgent)this.myAgent).moveTo(loc);
			}
			
		}
			
	}
	
	public int onEnd() {
		if (this.myAgent instanceof FollowerAgent) {
			return ((FollowerAgent) this.myAgent).getMovingValue();
		}
		
		else  {
			return super.onEnd();
		}
	}
	
	public boolean done() {
		return true;
	}

}