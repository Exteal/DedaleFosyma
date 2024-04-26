package hunt;

import java.util.Date;
import java.util.List;
import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import myagents.FollowerAgent;
import myagents.MapAgent;
import utils.MovingStates;
import utils.Protocol;

public class BlockingEnnemyBehaviour extends SimpleBehaviour {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1809675210863166056L;
	private List<String> allies;

	public BlockingEnnemyBehaviour(AbstractDedaleAgent ag, List<String> allies) {
		super(ag);
		this.allies = allies;
	}
	
	
	private void waitABit() {
		long start = new Date().getTime();
		while(new Date().getTime() - start < 1000L){}
		return;
	}
	
	@Override
	public void action() {
		try {
			//System.out.println("agent BLOCKING" + myAgent.getLocalName() + ((MapAgent)myAgent).getHuntingPos());
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
				
				
				waitABit();
				
				
				MessageTemplate msgTemplate=MessageTemplate.and(
						MessageTemplate.MatchProtocol(Protocol.PING.label),
						MessageTemplate.MatchPerformative(ACLMessage.CONFIRM));
				
				ACLMessage msgReceived=this.myAgent.receive(msgTemplate);
				if (msgReceived!=null) {
					
					((MapAgent)myAgent).addToUseless(msgReceived.getContent());
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

	@Override
	public int onEnd() {
		return ((FollowerAgent)myAgent).getMovingValue();
	}
	
	@Override
	public boolean done() {
		return true;
	}

}
