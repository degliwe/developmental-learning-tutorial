package Agents;

import java.util.List;

import main.Main;
import coupling.Experience;
import coupling.Interaction;
import coupling.Result;

public class Agent2 implements Agent{

	private Experience experience = Main.e1;
	private Interaction preInteraction;
	
	public Experience chooseExperience(Result result){

		Interaction enactedInteraction  = Interaction.get(this.experience.getLabel() + result.getLabel());
		
		if (preInteraction != null)
			Interaction.createOrGetCompositeInteraction(preInteraction, enactedInteraction);

		this.preInteraction = enactedInteraction;

		boolean proposed = false;
		
		List<Interaction> activatedInteractions = Interaction.getActivatedInteractions(enactedInteraction);
		for (Interaction activatedInteraction : activatedInteractions)
			if (activatedInteraction.getPostInteraction().getValue() > 0){
				this.experience = activatedInteraction.getPostInteraction().getExperience();
				proposed = true;
				System.out.println("propose " + this.experience.getLabel());
			}
			else{
				this.experience = Experience.getOther(activatedInteraction.getPostInteraction().getExperience());						
			}

//		if (!proposed &&  enactedInteraction.getValue() < 0)
//			this.experience = Experience.getNext();		
		
		return this.experience;
	}
}