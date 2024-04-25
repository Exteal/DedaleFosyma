package mapSharing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import eu.su.mas.dedale.env.Location;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.platformManagment.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import hunt.Observation;
import jade.core.behaviours.Behaviour;

public abstract class MapAgent extends AbstractDedaleAgent {
	private static final long serialVersionUID = -6472712328448962481L;
	private MapRepresentation myMap;
	private FriendsKnowledge friendsKnowledge;
	private ArrayList<String> communicating;
	private List<Observation> observations;
	private String lastDestination = null;
	private int tries = 0;
	
	private ArrayList<String> hunters;
	private String huntingPos = null;
	
	private int blockCount = 0;
	//private MapRepresentation huntingMap;
	
	public FriendsKnowledge getFriendsKnowledge() {
		if (this.friendsKnowledge == null) {
			this.friendsKnowledge = new FriendsKnowledge();
		}
		return friendsKnowledge;
	}
	
	public Location selectNextDestination() {
		
//		if (targetDestination == null) {
//			selectDestination();
//		}
		
		if (getMap().getOpenNodes().isEmpty()) {
			return null;
		}
		
		List<String> path = getMap().getShortestPathToClosestOpenNode(this.getCurrentPosition().getLocationId());
		String destString = path.get(0);
		List<Location> finalLocation = observe().stream().filter(o -> o.getLeft().getLocationId().equals(destString)).map(o -> o.getLeft()).collect(Collectors.toList());
		if(finalLocation.isEmpty()) {
			return null;
		}
		return finalLocation.get(0);
	}

	public void resetTries() {
		tries = 0;
	}
	
	public void increaseTries() {
		tries++;
	}
	
	public int getTries() {
		return tries;
	}

	
	public void setLastDestination(String loc) {
		this.lastDestination = loc;
	}
	
	
	public String getLastDestination() {
		return lastDestination;
	}
	
	
	public List<Observation> getObservations() {
		return observations;
	}
	
	
	public List<String> getCommunicating() {
		return communicating;
	}
	
	public void emptyCommunicating() {
		this.communicating = new ArrayList<String>(List.of());
	}
	
	public void addToCommunicating(String agent) {
		this.communicating.add(agent);
	}
	
	public MapRepresentation getMap() {
		if (this.myMap == null) {
			this.initMap();
		}
		return this.myMap;
	}
	
	public void initMap() {
		this.myMap = new MapRepresentation();
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

	
	protected List<Behaviour> listStartingBehaviours() {
		List<Behaviour> commons = listCommonBehaviours();
		List<Behaviour> specific = listSpecificBehaviours();

		List<Behaviour> starting = new ArrayList<Behaviour>(commons);
		starting.addAll(specific);
		return starting;
	}
	
	protected abstract List<Behaviour> listSpecificBehaviours();
	
	protected abstract List<Behaviour> listCommonBehaviours();
	
	protected void setup() {
		super.setup();
					
		List<Behaviour> startingBehaviours = listStartingBehaviours();		
		addBehaviour(new startMyBehaviours(this,startingBehaviours));
	}

	
	public List<String> getHunters() {
		return hunters;
	}

	public void setHuntingPos(String locationId) {
		this.huntingPos  = locationId;
	}
	
	public String getHuntingPos() {
		return huntingPos;
	}
	
	public int getBlockCount() {
		return blockCount;
	}

	
	public void increaseBlockCount() {
		blockCount++;
	}

	public void resetBlockCount() {
		blockCount = 0;
	}
	
}
