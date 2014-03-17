package Environments;

import main.Main;
import coupling.Experience;
import coupling.Result;

public class Environment4 implements Environment {

	private Experience experience_1;
	private Experience experience_2;

	public Result giveResult(Experience experience){
		
		Result result;

		if (experience_2!=experience &&
			experience_1==experience)
			result =  Main.r2;
		else
			result =  Main.r1;
		
		experience_2 = experience_1;
		experience_1 = experience;
		
		return result;
	}
}
