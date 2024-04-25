package mine;

import java.util.ArrayList;
import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.platformManagment.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.Behaviour;
import mapSharing.FriendsKnowledge;
import mapSharing.MapAgent;
import mapSharing.RespondPingBehaviour;

public abstract class PackAgent extends MapAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472712328448962481L;
	private ArrayList<String> pack = new ArrayList(List.of());
	
	
	
	public List<String> getPack() {
		return pack;
	}
	
	
	protected List<Behaviour> listCommonBehaviours() {
		return List.of(
					//new RandomMoveBehaviour(this, listAllies()),
					//new PingNearbyBehaviour(this, listAllies()),
					new RespondPingBehaviour(this)
				);
	}

	protected abstract List<Behaviour> listSpecificBehaviours();

	protected void setup() {
		super.setup();
					
		List<Behaviour> startingBehaviours = listStartingBehaviours();
		addBehaviour(new startMyBehaviours(this,startingBehaviours));
	}

	public void addToPack(String localName) {
		this.pack.add(localName);
	}
}
