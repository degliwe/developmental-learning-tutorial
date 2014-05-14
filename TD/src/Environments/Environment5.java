package Environments;

import coupling.Existence3;
import coupling.Experience;
import coupling.Result;

/**
 * The environment of Exercise 5.
 * The agent must alternate experiences e1 and e2 every third cycle to get one r2 result the third time:
 * e1->r1 e1->r1 e1->r2 e2->r1 e2->r1 e2->r2 etc. 
 * @author Olivier
 */
public class Environment5 implements Environment {

	private Existence3 coupling;
	private Experience experience_1;
	private Experience experience_2;
	private Experience experience_3;

	public Environment5(Existence3 coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		
		Result result = this.coupling.createOrGetResult(Existence3.LABEL_R1);

		if (experience_3 != experience &&
			experience_2 == experience &&
			experience_1 == experience)
			result =  this.coupling.createOrGetResult(Existence3.LABEL_R2);
		
		experience_3 = experience_2;
		experience_2 = experience_1;
		experience_1 = experience;
		
		return result;
	}
}
