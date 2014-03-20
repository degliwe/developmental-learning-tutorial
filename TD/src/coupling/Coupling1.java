package coupling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coupling1 implements Coupling {
	
	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;

	public Coupling1(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, -1);
		createPrimitiveInteraction(e1, r2, 1);
		createPrimitiveInteraction(e2, r1, -1);
		createPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	public Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	@Override
	public Experience getOtherExperience(Experience experience) {
		Experience otherExperience = null;
		for (Experience e : EXPERIENCES.values()){
			if (e!=experience){
				otherExperience =  e;
				break;
			}
		}		
		return otherExperience;
	}

	@Override
	public Result createOrGetResult(String label) {
		if (!RESULTS.containsKey(label))
			RESULTS.put(label, new Result(label));			
		return RESULTS.get(label);
	}

	@Override
	public void createPrimitiveInteraction(Experience experience,
			Result result, int value) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), value); 
		interaction.setExperience(experience);
		interaction.setResult(result);
	}

	@Override
	public void createOrReinforceCompositeInteraction(
			Interaction preInteraction, Interaction postInteraction) {
		int value = preInteraction.getValue() + postInteraction.getValue();
		Interaction interaction = createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), value); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		interaction.incrementWeight();
	}

	@Override
	public Interaction getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	@Override
	public List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : INTERACTIONS.values())
			if (interaction==activatedInteraction.getPreIntearction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}

	private Interaction createOrGet(String label, int value) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction(label, value));			
		return INTERACTIONS.get(label);
	}

}
