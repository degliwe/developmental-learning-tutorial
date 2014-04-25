package agent.decider;

import java.util.ArrayList;
import java.util.List;

import tracer.Trace;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode6 extends Episode4{

	private List<Interaction3> series = new ArrayList<Interaction3>();
	
	private int valence;
	
	private Interaction3 alternateInteraction;

	public Episode6(Coupling3 coupling, Experience experience){
		super(coupling, experience);
		if (!experience.isPrimitive())
			this.series = experience.getInteraction().getSeries();
	}

	@Override
	public void record(Result result){	
		
		this.alternateInteraction = null;
		
		Interaction3 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.getPrimitiveExperience().getLabel() + result.getLabel());

		Trace.addEventElement("enacted_interaction", enactedPrimitiveInteraction.getLabel());
		Trace.addEventElement("valence", enactedPrimitiveInteraction.getValence() + "");
		Trace.addEventElement("level", this.series.size() + "");

		valence += enactedPrimitiveInteraction.getValence();
		
		if (this.getExperience().isPrimitive()){
			this.setInteraction(enactedPrimitiveInteraction);
			this.setTerminated();
		}
		else{
			Interaction3 intendedPrimitiveInteraction = this.series.get(this.getStep());
			if (this.getStep() < this.series.size() -1){			
				// TODO Manage different results for longer interactions
				if (!enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction)){
					// The alternate primitive interaction don't have a primitive experience, it cannot be enacted !!
					this.alternateInteraction = this.getCoupling().createOrGetInteraction(this.getExperience(), result, this.valence);
					Trace.addEventElement("alternate_interaction", alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setInteraction(enactedPrimitiveInteraction);
					if (this.getStep() >= 1)
						this.setInteraction(this.getCoupling().getInteraction("(" + this.series.get(0).getLabel() + enactedPrimitiveInteraction.getLabel() + ")"));
					this.setTerminated();
				}
				this.incStep();
			}
			else{
				if (enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction))
					this.setInteraction(this.getExperience().getInteraction());
				else{
					this.getCoupling().createPrimitiveInteraction(this.getExperience(), result, this.valence);
					this.alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					Trace.addEventElement("alternate_interaction", alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setInteraction(enactedPrimitiveInteraction);
					if (this.getStep() >= 1)
						this.setInteraction(this.getCoupling().getInteraction("(" + this.series.get(0).getLabel() + enactedPrimitiveInteraction.getLabel() + ")"));
				}
				this.setTerminated();
			}			
		}
	}
	
	@Override
	public Experience nextPrimitiveExperience(){
		Experience nextExperience = this.getExperience();
		
		if (!this.getExperience().isPrimitive())
			nextExperience = this.series.get(this.getStep()).getExperience();
		
		this.setPrimitiveExperience(nextExperience);
		
		return nextExperience; 
	}
	
	public Interaction3 getAlternateInteraction(){
		return this.alternateInteraction;
	}
}