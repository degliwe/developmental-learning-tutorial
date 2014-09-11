package reactive;

import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention2;
import coupling.Result;
import coupling.interaction.Interaction030;
import existence.Existence021;

/**
 * A Reality2 is a sort of Reality1
 * which results in R1 when the current experience equals the previous experience
 * and in R2 when the current experience differs from the previous experience.
 * @author Olivier
 */
public class Environment2 extends Environment1 {

	protected Experience previousExperience;
	
	public Environment2(Existence021 existence){
		super(existence);
	}
	
	@Override
	public Obtention2 provideObtention(Intention intention){
		
		Experience experience = ((Intention1)intention).getExperience();
		Result result = giveResult(experience);
		
		Interaction030 currentInteraction = (Interaction030)this.existence.createOrGetPrimitiveInteraction(experience, result, 0);
		
		return new Obtention2(currentInteraction);
	}
	
	@Override
	public Result giveResult(Experience experience){
		Result result = null;
		if (previousExperience == experience)
			result =  this.existence.createOrGetResult(this.existence.LABEL_R1);
		else
			result =  this.existence.createOrGetResult(this.existence.LABEL_R2);
		previousExperience = experience;

		return result;
	}
}