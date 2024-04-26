package test;

import java.util.ArrayList;
import java.util.List;

import exploration.RespondPingBehaviour;
import jade.core.behaviours.Behaviour;
import myagents.MapAgent;


public class WaitingAgent extends MapAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6921245802936517021L;
	
	protected void setup() {
		super.setup();

		//get the parameters given into the object[]
		final Object[] args = getArguments();		
		
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
		
		//use them as parameters for your behaviours is you want
		
		
		
		/************************************************
		 * 
		 * ADD the inititial behaviours of the Dummy Moving Agent here
		 * 
		 ************************************************/
		
		
		
		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */
		
		//addBehaviour(new startMyBehaviours(this,lb));
	}


	@Override
	protected List<Behaviour> listSpecificBehaviours() {
		List<Behaviour> lb=new ArrayList<Behaviour>();
		lb.add(new RespondPingBehaviour(this));
		lb.add(new PrintKnowledgeBehaviour(this, 4000));

		//lb.add(new WaitFuseBehaviour(this, this.myMap, list_agentNames));
		return lb;
	}


	@Override
	protected List<Behaviour> listCommonBehaviours() {
		// TODO Auto-generated method stub
		return List.of();
	}
	
	
}
