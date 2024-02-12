package mine;

import java.io.Serializable;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class FollowLeaderBehaviour extends SimpleBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8755247300313175681L;
	
	
	private boolean finished = false;
	
	
	public FollowLeaderBehaviour(AbstractDedaleAgent agent) {
		super(agent);
		
	}
	@Override
	public void action() {
		
		/*
		 * 1 -- receive destination from leader
		 * 
		 * 2 -- moveTo destination
		 */
		
		String leader = ((FollowerAgent)this.myAgent).getLeader();
		//System.out.println("Leader of " + myAgent.getLocalName() + " is " + leader);
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.and( MessageTemplate.MatchProtocol(Protocol.FOLLOW_DESTINATION.toString())
				, MessageTemplate.MatchPerformative(ACLMessage.INFORM)), MessageTemplate.MatchSender(new AID(leader, AID.ISLOCALNAME)));
		
		ACLMessage message = myAgent.receive(template);
		if(message != null) {
			
			try {
				//getclosets
				//moveto
				
				System.out.println(myAgent.getLocalName() + " following " + message.getSender().getLocalName());
				Location target = (Location) message.getContentObject();
				Location currentPos = ((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
				
				Location destination = getClosestReachableLocation(currentPos, target);
				
				System.out.println("destination : " + destination.getLocationId());

				((AbstractDedaleAgent)this.myAgent).moveTo(destination);

			} 
			
			catch (UnreadableException e) {
				e.printStackTrace();
			}
			
			
		}
	}

	@Override
	public boolean done() {
		return finished;
	}
	
	private Location getClosestReachableLocation(Location current, Location target) {
		
		List<Couple<Location,List<Couple<Observation,Integer>>>> lobs=((AbstractDedaleAgent)this.myAgent).observe();//myPosition
		List<Location> reachables = lobs.stream().map(it  -> it.getLeft()).toList();
		
		return reachables.get(0);
		//return reachables.stream().min((l1, l2) -> Integer.compare(l1.getLocationId(), l2.getLocationId())).get();
		
	}

}
