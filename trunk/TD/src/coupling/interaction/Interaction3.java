package coupling.interaction;

public class Interaction3 extends Interaction1 {
	
	private Interaction3 preInteraction;
	private Interaction3 postInteraction;

	private int weight = 0;

	public Interaction3(String label, int valence){
		super(label, valence);
	}
	
	public Interaction3 getPreInteraction() {
		return preInteraction;
	}

	public void setPreInteraction(Interaction3 preInteraction) {
		this.preInteraction = preInteraction;
	}

	public Interaction3 getPostInteraction() {
		return postInteraction;
	}
	
	public void setPostInteraction(Interaction3 postInteraction) {
		this.postInteraction = postInteraction;
	}

	public int getWeight() {
		return weight;
	}

	public void incrementWeight() {
		this.weight++;
	}
	
	public String toString(){
		if (this.getPreInteraction() != null)
			return this.getPreInteraction().getLabel() + "-" + this.getPostInteraction().getLabel() + "," + this.getValence() + "," + this.weight;
		else
			return this.getExperience().getLabel() + "," + this.getResult().getLabel() + "," + this.getValence();
	}

}