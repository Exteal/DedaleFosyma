package myagents;

import java.util.ArrayList;
import java.util.List;

import eu.su.mas.dedale.mas.agent.behaviours.platformManagment.startMyBehaviours;
import exploration.RespondPingBehaviour;
import jade.core.behaviours.Behaviour;

public abstract class PackAgent extends MapAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472712328448962481L;
	private ArrayList<String> pack = new ArrayList<String>();
	
	
	
	public List<String> getPack() {
		return pack;
	}
	
	
	protected List<Behaviour> listCommonBehaviours() {
		return List.of(
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
