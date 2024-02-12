package mine;

import java.util.ArrayList;
import java.util.List;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.platformManagment.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import jade.core.behaviours.Behaviour;

public abstract class PackAgent extends AbstractDedaleAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6472712328448962481L;
	private MapRepresentation myMap;
	private ArrayList<String> pack = new ArrayList(List.of());

	
	
	public MapRepresentation getMap() {
		return this.myMap;
	}
	
	public List<String> getPack() {
		return pack;
	}
	
	public List<String> listAllies() {
		Object[] args = getArguments();
		List<String> list_agentNames=new ArrayList<String>();

		if(args.length==0){
			System.err.println("Error while creating the agent, names of agent to contact expected");
			System.exit(-1);
		}else{
			int i=2;// WARNING YOU SHOULD ALWAYS START AT 2. This will be corrected in the next release.
			while (i<args.length) {
				list_agentNames.add((String)args[i]);
				i++;
			}
		}
		
		return list_agentNames;
	}

	
	private List<Behaviour> listStartingBehaviours() {
		List<Behaviour> commons = listCommonBehaviours();
		List<Behaviour> specific = listSpecificBehaviours();
		
		List<Behaviour> starting = new ArrayList<Behaviour>(commons);
		starting.addAll(specific);
		return starting;
	}
	
	private List<Behaviour> listCommonBehaviours() {
		return List.of(
					//new RandomMoveBehaviour(this, listAllies()),
					//new PingNearbyBehaviour(this, listAllies()),
					//new RespondPingBehaviour(this)
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
