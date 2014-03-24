package coupling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import coupling.interaction.Interaction3;

public class Coupling3 implements Coupling {

	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction3> INTERACTIONS = new HashMap<String , Interaction3>() ;

	public Coupling3(){
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
			Result result, int valence) {
		Interaction3 interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
	}

	@Override
	public Interaction3 getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	public Collection<Interaction3> getInteractions(){
		return INTERACTIONS.values();
	}
	
	private Interaction3 createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction3(label, valence));			
		return INTERACTIONS.get(label);
	}
	public void createOrReinforceCompositeInteraction(
		Interaction3 preInteraction, Interaction3 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction3 interaction = createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		interaction.incrementWeight();
		System.out.println("learn " + interaction.toString());
	}

	public List<Interaction3> getActivatedInteractions(Interaction3 interaction) {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
		for (Interaction3 activatedInteraction : getInteractions())
			if (interaction==activatedInteraction.getPreInteraction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
