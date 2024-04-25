package test;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.behaviours.SimpleBehaviour;
import mapSharing.FollowerAgent;
import mapSharing.MapAgent;


public class RandomMoveBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6533196628465572862L;
	private List<String> allies;
		
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
			((MapAgent)this.myAgent).getFriendsKnowledge().addNewNode(accessibleNode.getLocationId());
			
			//the node may exist, but not necessarily the edge
			if (myPosition.getLocationId()!=accessibleNode.getLocationId()) {
				
				((MapAgent)this.myAgent).getMap().addEdge(myPosition.getLocationId(), accessibleNode.getLocationId());
				
				((MapAgent)this.myAgent).getFriendsKnowledge().addNewEdge(myPosition.getLocationId(), accessibleNode.getLocationId());
				
				if (nextNodeId==null && isNewNode) nextNodeId=accessibleNode.getLocationId();
			}
		}
		
		
		Random r= new Random();
		int moveId=1+r.nextInt(lobs.size()-1);//removing the current position from the list of target, not necessary as to stay is an action but allow quicker random move

		//The move action (if any) should be the last action of your behaviour
		
		//System.out.println(this.myAgent.getAID().getLocalName() +" moved");
		Location destination = lobs.get(moveId).getLeft();

		/*if (this.getAgent() instanceof LeaderAgent) {
			((AbstractDedaleAgent)this.getAgent()).addBehaviour(
					new NotifyDestinationFollowersBehaviour((AbstractDedaleAgent) this.myAgent, destination, ((PackAgent)this.myAgent).getPack()));
		}*/
		
		
		((AbstractDedaleAgent)this.myAgent).moveTo(destination);
	}
	
	public int onEnd() {
		if (this.myAgent instanceof FollowerAgent) {
		//	System.out.println("onEnd random Move " + ((FollowerAgent) this.myAgent).getMovingValue());
			return ((FollowerAgent) this.myAgent).getMovingValue();
		}
		
		else  {
			return super.onEnd();
		}
	}
	
	public boolean done() {
		/*if (this.myAgent instanceof FollowerAgent) {
			return ((FollowerAgent) this.myAgent).getMovingValue() != MovingStates.Random.getStateInt();
		}
		else return finished;
			*/
		return true;
	}

}
