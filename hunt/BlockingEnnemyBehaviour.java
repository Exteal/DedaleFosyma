package hunt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import mapSharing.FollowerAgent;
import mapSharing.MapAgent;
import utils.MovingStates;
import utils.Protocol;

public class BlockingEnnemyBehaviour extends Behaviour {


	private List<String> allies;

	public BlockingEnnemyBehaviour(AbstractDedaleAgent ag, List<String> allies) {
		super(ag);
		this.allies = allies;
	}
	
	@Override
	public void action() {
		try {
			System.out.println("agent BLOCKING" + myAgent.getLocalName() + ((MapAgent)myAgent).getHuntingPos());
			Location myPosition = ((AbstractDedaleAgent)this.myAgent).getCurrentPosition();
			if (myPosition!=null && myPosition.getLocationId()!="") {

				ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
				msg.setSender(this.myAgent.getAID());
				msg.setProtocol(Protocol.PING.label);
				
				
				String pos = ((MapAgent)myAgent).getHuntingPos();
				msg.setContent(pos);
				
				
				
				for (String ally : allies) {
					msg.addReceiver(new AID(ally, AID.ISLOCALNAME));
				}
				
				((AbstractDedaleAgent)myAgent).sendMessage(msg);
				
				
				long start = new Date().getTime();
				while(new Date().getTime() - start < 1000L){}
				
				
				MessageTemplate msgTemplate=MessageTemplate.and(
						MessageTemplate.MatchProtocol(Protocol.PING.label),
						MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
				
				ACLMessage msgReceived=this.myAgent.receive(msgTemplate);
				if (msgReceived!=null) {
					((FollowerAgent)myAgent).setMovingValue(MovingStates.SearchEnnemy.number);
				}
				
				else {
					System.out.println("Received nothing");
					((FollowerAgent)myAgent).setMovingValue(MovingStates.Final.number);
				}
			}
			
			else {
				((FollowerAgent)myAgent).setMovingValue(MovingStates.BlockingEnnemy.number);
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
			((FollowerAgent)myAgent).setMovingValue(MovingStates.BlockingEnnemy.number);
		}
		
		

	}

	public int onEnd() {
		//System.out.println("ENd of Block Value For Agent " + myAgent.getLocalName() + " is " + ((FollowerAgent)myAgent).getMovingValue() );
		return ((FollowerAgent)myAgent).getMovingValue();
	}
	@Override
	public boolean done() {
		return true;
	}

}
